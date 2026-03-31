package com.myapp.mysimon

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val delete = stringResource(R.string.delete)
    val endgame = stringResource(R.string.endgame)
    val sequence = stringResource(R.string.new_sequence)

    // String with the sequence of actual game
    var t by remember { mutableStateOf(sequence) }

    ConstraintLayout(modifier = modifier) {
        val (spacer, redBut, greenBut, blueBut, magentaBut, yellowBut, cyanBut, sequence, deleteBut, endBut) = createRefs()

        // Space on the top
        Spacer(
            modifier = Modifier.height(32.dp).constrainAs(spacer) {
                top.linkTo(parent.top)
            }
        )

        // Six coloured buttons
        Button(
            modifier = Modifier.constrainAs(redBut) {
                start.linkTo(parent.start, margin = 8.dp)
                top.linkTo(spacer.bottom)
                end.linkTo(magentaBut.start, margin = 2.dp)
            },
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {}
        Button(
            modifier = Modifier.constrainAs(greenBut) {
                start.linkTo(parent.start, margin = 8.dp)
                top.linkTo(redBut.bottom, margin = 2.dp)
                end.linkTo(yellowBut.start, margin = 2.dp)
            },
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {}
        Button(
            modifier = Modifier.constrainAs(blueBut) {
                start.linkTo(parent.start, margin = 8.dp)
                top.linkTo(greenBut.bottom, margin = 2.dp)
                end.linkTo(cyanBut.start, margin = 2.dp)
            },
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {}
        Button(
            modifier = Modifier.constrainAs(magentaBut) {
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(spacer.bottom)
                start.linkTo(redBut.end, margin = 2.dp)
                baseline.linkTo(redBut.baseline)
            },
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
        ) {}
        Button(
            modifier = Modifier.constrainAs(yellowBut) {
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(magentaBut.bottom, margin = 2.dp)
                start.linkTo(greenBut.end, margin = 2.dp)
                baseline.linkTo(greenBut.baseline)
            },
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
        ) {}
        Button(
            modifier = Modifier.constrainAs(cyanBut) {
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(yellowBut.bottom, margin = 2.dp)
                start.linkTo(blueBut.end, margin = 2.dp)
                baseline.linkTo(blueBut.baseline)
            },
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
        ) {}

        // Text view with the string of the current game
        Text(
            text = t,
            modifier = Modifier.constrainAs(sequence) {
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(blueBut.bottom, margin = 2.dp)
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
            onClick = {}
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
            onClick = buttonAction
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