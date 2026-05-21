package com.myapp.mysimon

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.mysimon.data.AppDatabase
import com.myapp.mysimon.data.Game
import com.myapp.mysimon.data.GameRepository
import com.myapp.mysimon.model.SimonGame
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class GameState {
    STARTING, // Waiting for the user to start a new game
    CPU_TURN, // The CPU is generating the sequence
    USER_TURN, // The user can click on the buttons
    GAME_OVER, // The game ended due to an error or by the button End Game
    PAUSE, // The game is paused by the user
    WAITING // Waiting for the game to change state during the check
}

class GameViewModel(application: Application) : AndroidViewModel(application) {
    // Instance of the repository
    private val repository: GameRepository
    init {
        val db = AppDatabase.getDatabase(application)
        repository = GameRepository(db.gameDao())
    }

    // Instance of the current SimonGame
    private val simonGame = SimonGame()

    // Last button pressed by the user
    private var userIndex = 0

    // Observable state of the game
    private val _gameState = MutableStateFlow(GameState.STARTING)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    // Sequence of the game initialized as a state
    private val _sequenceString = MutableStateFlow("")
    val sequenceString: StateFlow<String> = _sequenceString.asStateFlow()

    // Tracks which button should be highlighted (-1 means none)
    private val _activeButtonIndex = MutableStateFlow(-1)
    val activeButtonIndex: StateFlow<Int> = _activeButtonIndex.asStateFlow()

    // Function to start a new game
    fun startNewGame() {
        simonGame.reset() // Make sure the game is cleared
        userIndex = 0
        _gameState.value = GameState.CPU_TURN
        addNewColor()
    }

    // Function that generate a new color and add it to the sequence
    fun addNewColor() {
        viewModelScope.launch {
            // Set the state to CPU_TURN
            _gameState.value = GameState.CPU_TURN

            // Clear the string every time the user has to repeat the sequence
            _sequenceString.value = ""

            // Generate an int between 0 and 5 and add it to the sequence
            val nextColor = Random.nextInt(0, 6)
            simonGame.increment(nextColor)

            // Show the sequence to the user
            for (colorIndex in simonGame.sequence) {
                // Stop playback if game is paused
                while (_gameState.value == GameState.PAUSE) {
                    // Wait 100 ms and then check if the game is resumed, if not repeat the loop
                    delay(100)
                }

                // Every button is illuminated for 800ms and there's a gap of 200ms between buttons
                _activeButtonIndex.value = colorIndex
                delay(800)
                _activeButtonIndex.value = -1
                delay(200)
            }

            // Pass the game state to the user
            _gameState.value = GameState.USER_TURN
        }
    }

    // Function that handle the user click, the parameter is the index of the button pressed
    fun userClick(btn: Int) {
        // Change the game state during the check
        _gameState.value = GameState.WAITING

        viewModelScope.launch {
            // Visual feedback for user click
            _activeButtonIndex.value = btn
            delay(200)
            _activeButtonIndex.value = -1

            // Button the user should have clicked
            val rightColor = simonGame.sequence[userIndex]

            if (rightColor == btn) {
                // The user has guess the right color so we increment his counter
                userIndex++

                // Update the sequence showed with the last button pressed
                _sequenceString.value = simonGame.getSequenceString(userIndex)

                // If the button is right and there are one or more other button the user can click again
                _gameState.value = GameState.USER_TURN

                // Check if the user has completed this round's sequence
                if (userIndex == simonGame.count) {
                    userIndex = 0
                    delay(500)
                    addNewColor()
                }
            } else {
                // The user has guess the wrong button, so the game end
                gameOver()
            }
        }
    }

    // Function that handle the end of the game
    fun gameOver() {
        _gameState.value = GameState.GAME_OVER

        // If the user hasn't pressed any button do not save the game
        if (simonGame.count <= 1) return

        // Save the values that has to be inserted in the database
        val game = Game(counter = simonGame.count, sequence = simonGame.getSequenceString(), error = userIndex+1)

        // Launch a coroutine to insert the game in the database safely
        viewModelScope.launch {
            repository.insert(game)
        }
    }

    // Function called when the pause button is clicked by the user
    fun pauseGame() {
        _gameState.value = GameState.PAUSE
    }

    // Function used to return to the cpu turn
    fun resumeGame() {
        _gameState.value = GameState.CPU_TURN
    }

    // Function called when the endgame button is clicked by the user
    fun endGame() {
        gameOver()
    }
}
