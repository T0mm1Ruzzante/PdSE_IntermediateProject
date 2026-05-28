package com.myapp.mysimon.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity class that represent a game in the database
@Entity
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Longest sequence of button pressed by the user
    @ColumnInfo(name = "counter")
    val counter: Int,

    // String of the sequence of the game
    @ColumnInfo(name = "sequence")
    val sequence: String,

    // The index of the wrong button pressed
    @ColumnInfo(name = "error_index")
    val error: Int
)
