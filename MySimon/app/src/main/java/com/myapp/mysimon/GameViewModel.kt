package com.myapp.mysimon

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val game: SimonGame = SimonGame()
}