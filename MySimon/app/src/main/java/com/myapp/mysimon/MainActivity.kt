package com.myapp.mysimon

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val clicks = stringResource(R.string.clicks)
    val sequence = stringResource(R.string.sequence)

    // String with the sequence of actual game
    var t by remember { mutableStateOf(sequence) }

    ConstraintLayout(modifier = modifier) {
        val (space, redBut, greenBut, blueBut, magentaBut, yellowBut, cyanBut, text, deleteBut, endBut) = createRefs()

        // Space on the top
        Spacer(modifier = Modifier.height(16.dp))

        Button(

        )
    }

    // Space on the bottom
    /*Spacer(modifier = Modifier.height(16.dp))

    // Six coloured buttons
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {}*/
    /*Button()
    Button()
    Button()
    Button()
    Button()*/

    // Text view with the string of the current game
    //Text()

    //
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen( buttonAction = {} )
}