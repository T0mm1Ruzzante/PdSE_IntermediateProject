package com.myapp.mysimon

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.myapp.mysimon.ui.theme.MySimonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display on API level < 35
        enableEdgeToEdge()

        // Set and display the UI content
        setContent {
            MySimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        buttonAction = {
                            val myIntent = Intent(this, EndgameActivity::class.java)
                            // aggiungere passaggio stringa
                            // Button to pass to the endgame activity
                            startActivity(myIntent)

                            // Note: if you need more control over the starting process,
                            // consider the alternative method startActivity(Intent!,Bundle?).
                            // If you need to pass data to the starting activity,
                            // consider adding them as intent extras.
                            // If you need to receive a result from the starting activity,
                            // look up https://developer.android.com/training/basics/intents/result
                        }
                    )
                }
            }
        }
    }
}

// Function of the first screen of the app
// Contains colored buttons, current sequence, delete button and end-game button
@Composable
fun MainScreen(modifier: Modifier = Modifier, buttonAction : () -> Unit) {
    // Orientation of the device
    val orientation = LocalConfiguration.current.orientation

    // Strings used on this activity
    val delete = stringResource(R.string.delete)
    val endgame = stringResource(R.string.endgame)
    val newSequence = stringResource(R.string.new_sequence)

    // String with the sequence of the actual game
    var t by rememberSaveable { mutableStateOf(newSequence) }

    // Boolean value that identifies if a new game is started
    var gameStarted = false

    ConstraintLayout(modifier = modifier) {
        val (spacer, redBut, greenBut, blueBut, magentaBut, yellowBut, cyanBut, sequence, deleteBut, endBut) = createRefs()

        // Space on the top if orientation = portrait
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Spacer(
                modifier = Modifier
                    .height(32.dp)
                    .constrainAs(spacer) {
                        top.linkTo(parent.top)
                    }
            )
        }

        // Six coloured buttons
        Button(
            modifier = Modifier
                .padding()
                .size(140.dp)
                .constrainAs(redBut) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(spacer.bottom)
                    end.linkTo(magentaBut.start)
                },
            onClick = {
                if (!gameStarted) {
                    t = ""
                    gameStarted = true
                }
                t += "R"
            },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {}
        Button(
            modifier = Modifier
                .padding()
                .size(140.dp)
                .constrainAs(greenBut) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(redBut.bottom, margin = 2.dp)
                    end.linkTo(yellowBut.start)
                },
            onClick = {
                if (!gameStarted) {
                    t = ""
                    gameStarted = true
                }
                t += "G"
            },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {}
        Button(
            modifier = Modifier
                .padding()
                .size(140.dp)
                .constrainAs(blueBut) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(greenBut.bottom, margin = 2.dp)
                    end.linkTo(cyanBut.start)
                },
            onClick = {
                if (!gameStarted) {
                    t = ""
                    gameStarted = true
                }
                t += "B"
            },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {}
        Button(
            modifier = Modifier
                .padding()
                .size(140.dp)
                .constrainAs(magentaBut) {
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(spacer.bottom)
                    start.linkTo(redBut.end)
                    baseline.linkTo(redBut.baseline)
                },
            onClick = {
                if (!gameStarted) {
                    t = ""
                    gameStarted = true
                }
                t += "M"
            },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
        ) {}
        Button(
            modifier = Modifier
                .padding()
                .size(140.dp)
                .constrainAs(yellowBut) {
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(magentaBut.bottom, margin = 2.dp)
                    start.linkTo(greenBut.end)
                    baseline.linkTo(greenBut.baseline)
                },
            onClick = {
                if (!gameStarted) {
                    t = ""
                    gameStarted = true
                }
                t += "Y"
            },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
        ) {}
        Button(
            modifier = Modifier
                .padding()
                .size(140.dp)
                .constrainAs(cyanBut) {
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(yellowBut.bottom, margin = 2.dp)
                    start.linkTo(blueBut.end)
                    baseline.linkTo(blueBut.baseline)
                },
            onClick = {
                if (!gameStarted) {
                    t = ""
                    gameStarted = true
                }
                t += "C"
            },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
        ) {}

        // Border color in the text box
        val gradientBrush = Brush.horizontalGradient(
            colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Magenta, Color.Yellow, Color.Cyan),
            startX = 0.0f,
            endX = 500.0f,
            tileMode = TileMode.Repeated
        )
        // Text view with the string of the current game
        Text(
            text = t,
            modifier = Modifier
                .border(width = 2.dp, brush = gradientBrush, shape = RectangleShape)
                .padding(10.dp)
                .constrainAs(sequence) {
                    start.linkTo(parent.start, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(blueBut.bottom, margin = 2.dp)
                    bottom.linkTo(deleteBut.top)
                    bottom.linkTo(endBut.top)
                },
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        // Button to delete the current game
        Button(
            modifier = Modifier.constrainAs(deleteBut) {
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(endBut.start, margin = 2.dp)
                bottom.linkTo(parent.bottom, margin = 24.dp)
            },
            onClick = {
                gameStarted = false
                t = newSequence
            }
        ) {
            Text(text = delete)
        }

        // Button to end the current game
        Button(
            modifier = Modifier.constrainAs(endBut) {
                start.linkTo(deleteBut.end, margin = 8.dp)
                end.linkTo(parent.end, margin = 2.dp)
                bottom.linkTo(parent.bottom, margin = 24.dp)
            },
            onClick = {
                gameStarted = false
                t = newSequence
                buttonAction()
            }
        ) {
            Text(text = endgame)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen( buttonAction = {} )
}