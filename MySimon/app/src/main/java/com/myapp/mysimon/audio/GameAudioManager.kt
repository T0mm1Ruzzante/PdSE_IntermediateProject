package com.myapp.mysimon.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.myapp.mysimon.R

// Class used to manage the audio of the game (feedback to the user for buttons pressed and errors)
class GameAudioManager(context: Context) {
    // Instance of the SoundPool
    private val soundPool: SoundPool

    // Map used to associate the SoundPool ID with the sound resource ID
    private val soundMap = HashMap<Int, Int>()
    private var isLoaded = false

    init {
        // Define the audio attributes optimized for game sounds
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        // Build the instance of SoundPool
        soundPool = SoundPool.Builder()
            .setMaxStreams(6)
            .setAudioAttributes(audioAttributes)
            .build()

        // Insert every sound (taken from the resources of the app) in the map
        soundMap[0] = soundPool.load(context, R.raw.color_0, 1)
        soundMap[1] = soundPool.load(context, R.raw.color_1, 1)
        soundMap[2] = soundPool.load(context, R.raw.color_2, 1)
        soundMap[3] = soundPool.load(context, R.raw.color_3, 1)
        soundMap[4] = soundPool.load(context, R.raw.color_4, 1)
        soundMap[5] = soundPool.load(context, R.raw.color_5, 1)
        soundMap[99] = soundPool.load(context, R.raw.error, 1)

        // Listener used to know when the sound is loaded
        soundPool.setOnLoadCompleteListener { _, _, status ->
            // If the status is 0, the sound is loaded correctly
            if (status == 0) {
                isLoaded = true
            }
        }
    }

    // Function used to play a sound
    fun playSound(buttonIndex: Int) {
        val soundId = soundMap[buttonIndex]
        if (isLoaded && soundId != null) {
            // Play the sound with the given ID
            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    // Function used to release the resources
    fun release() {
        soundPool.release()
    }
}