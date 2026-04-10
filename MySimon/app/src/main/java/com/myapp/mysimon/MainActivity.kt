package com.myapp.mysimon

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.myapp.mysimon.ui.theme.*
import kotlin.collections.plusAssign
import kotlin.inc
import kotlin.text.get

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
fun MainScreen(modifier: Modifier = Modifier, buttonAction : (/*List<String>*/) -> Unit) {
    // Orientation of the device
    val orientation = LocalConfiguration.current.orientation

    // Value used to proportion items to the screen
    val configuration = LocalConfiguration.current

    //  DA IMPORTARE NELLE APPOSITE FUNZIONI

    // String with the sequence of the actual game
    val newSequence = stringResource(R.string.new_sequence)
    var t by rememberSaveable { mutableStateOf(newSequence) }

    // Boolean value that identifies if a new game is started
    var gameStarted by rememberSaveable { mutableStateOf(false) }

    // Value used to count the actual clicks on buttons
    var count by rememberSaveable { mutableIntStateOf(0) }

    // List of the previous sequences in the current game
    // var list by rememberSaveable {mutableStateOf(listOf<String>()}

    // Function used to add the letter of the clicked button to the sequence
    val onColoredButtonCLick: (String) -> Unit = { color->
        if (!gameStarted) {
            t = ""
            gameStarted = true
        } else {
            t += ", "
        }
        t += color
        count++
    }

    // Function used to delete the current game and restart a new one
    val onDeleteButtonClick: () -> Unit = {
        gameStarted = false
        t = newSequence
        count = 0
    }

    // Function used to end the current game and go to the second activity
    val onEndgameButtonClick: () -> Unit = {
        gameStarted = false
        t = newSequence
        count = 0
        buttonAction(/*list*/)
    }

    // Layout of the game activity
    ConstraintLayout(modifier = modifier) {
        val (matrix, sequence, deleteBut, endBut) = createRefs()

        // Column that contain the 3x2 matrix with the 6 buttons
        // It always cover half of the screen
        /*Column(modifier = Modifier
            .padding(8.dp)
            .constrainAs(matrix) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    // end.linkTo(parent.end)
                    // bottom.linkTo(sequence.top)
                    width = Dimension.value(configuration.screenWidthDp.dp)
                    height= Dimension.value((configuration.screenHeightDp / 2).dp)
                } else {
                    // end.linkTo(sequence.start)
                    // bottom.linkTo(parent.bottom)
                    width = Dimension.value((configuration.screenWidthDp / 2).dp)
                    height= Dimension.value((configuration.screenHeightDp).dp)
                }
            },
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // All button colors and their respective initial letters
            val colors = listOf(Color.Red, Color.Magenta, Color.Green, Color.Yellow, Color.Blue, Color.Cyan)
            val colorsLetters = listOf("R", "M", "G", "Y", "B", "C")

            // Loop used to create the 6 buttons inside the column
            // All the rows have the same weight, as do the buttons which are therefore the same size
            var index = 0
            repeat(3) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(2) {
                        val i = index
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            onClick = {
                                if (!gameStarted) {
                                    t = ""
                                    gameStarted = true
                                } else {
                                    t += ", "
                                }
                                t += colorsLetters[i]
                                count++
                            },
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colors[index])
                        ) {}
                        index++
                    }
                }
            }
        }*/

        // Call to the composable function that create the 3x2 matrix of 6 colored buttons
        ButtonGrid(modifier = Modifier
            .constrainAs(matrix) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    // end.linkTo(parent.end)
                    // bottom.linkTo(sequence.top)
                    width = Dimension.value(configuration.screenWidthDp.dp)
                    height= Dimension.value((configuration.screenHeightDp / 2).dp)
                } else {
                    // end.linkTo(sequence.start)
                    // bottom.linkTo(parent.bottom)
                    width = Dimension.value((configuration.screenWidthDp / 2).dp)
                    height= Dimension.value((configuration.screenHeightDp).dp)
                }
            },
            onButtonClick = onColoredButtonCLick)

        // Text view with the string of the current game
        /*Text(
            text = t,
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(scrollState)
                .background(color = LightBlueGrey50, shape = RoundedCornerShape(4.dp))
                .border(width = 2.dp, brush = gradientBrush, shape = RoundedCornerShape(4.dp))
                .constrainAs(sequence) {
                    end.linkTo(parent.end, margin = 4.dp)
                    // bottom.linkTo(deleteBut.top)
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        top.linkTo(matrix.bottom)
                        start.linkTo(parent.start, margin = 4.dp)
                        // bottom.linkTo(endBut.top)
                    } else {
                        top.linkTo(parent.top, margin = 4.dp)
                        start.linkTo(matrix.end)
                    }
                },
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )*/

        // Call to the composable function that create the text view of the current game sequence
        SequenceText(sequence = t,modifier = Modifier.constrainAs(sequence) {
            end.linkTo(parent.end, margin = 4.dp)
            // bottom.linkTo(deleteBut.top)
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                top.linkTo(matrix.bottom)
                start.linkTo(parent.start, margin = 4.dp)
                // bottom.linkTo(endBut.top)
            } else {
                top.linkTo(parent.top, margin = 4.dp)
                start.linkTo(matrix.end)
            }
        })

        // Button to delete the current game
        /*Button(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(deleteBut) {
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        start.linkTo(parent.start)
                        end.linkTo(endBut.start)
                        bottom.linkTo(parent.bottom)
                    } else {
                        top.linkTo(sequence.bottom)
                        start.linkTo(matrix.end)
                        end.linkTo(parent.end)
                        bottom.linkTo(endBut.top)
                    }
            },
            onClick = onDeleteButtonClick,
            colors = ButtonDefaults.buttonColors(containerColor = OrangeA400)
        ) {
            Text(text = delete)
        }*/

        //
        /*val endgame = stringResource(R.string.endgame)

        // Button to end the current game
        Button(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(endBut) {
                    bottom.linkTo(parent.bottom)
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        start.linkTo(deleteBut.end)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    } else {
                        start.linkTo(matrix.end)
                        end.linkTo(parent.end)
                        // top.linkTo(deleteBut.bottom)
                    }
                },
            onClick = onEndgameButtonClick,
            colors = ButtonDefaults.buttonColors(containerColor = OrangeA400)
        ) {
            Text(text = endgame)
        }*/
    }
}

@Composable
fun ButtonGrid(modifier: Modifier = Modifier, onButtonClick: (String) -> Unit) {
    // Column that contain the 3x2 matrix with the 6 buttons
    // It always cover half of the screen
    Column(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // All button colors and their respective initial letters
        val colors = listOf(Color.Red, Color.Magenta, Color.Green, Color.Yellow, Color.Blue, Color.Cyan)
        val colorsLetters = listOf("R", "M", "G", "Y", "B", "C")

        // Loop used to create the 6 buttons inside the column
        // All the rows have the same weight, as do the buttons which are therefore the same size
        var index = 0
        repeat(3) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(2) {
                    val i = index
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = { onButtonClick(colorsLetters[i]) },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colors[index])
                    ) {}
                    index++
                }
            }
        }
    }
}

@Composable
fun SequenceText(modifier: Modifier = Modifier, sequence: String) {
    // Value used to make the sequence scrollable and not expandable
    val scrollState = rememberScrollState()

    // Border color in the text box containing the sequence
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(Color.Red, Color.Magenta, Color.Green, Color.Yellow, Color.Blue, Color.Cyan),
        startX = 0.0f,
        endX = 500.0f,
        tileMode = TileMode.Repeated
    )

    // Text view with the string of the current game
    Text(
        text = sequence,
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
            .background(color = LightBlueGrey50, shape = RoundedCornerShape(4.dp))
            .border(width = 1.dp, brush = gradientBrush, shape = RoundedCornerShape(4.dp)),
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun DeleteButton(modifier: Modifier = Modifier, onButtonClick: () -> Unit) {
    // String of the button
    val delete = stringResource(R.string.delete)

    // Button to delete the current game
    Button(
        modifier = modifier
            .padding(8.dp),
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(containerColor = OrangeA400)
    ) {
        Text(text = delete)
    }
}

@Composable
fun EndgameButton(modifier: Modifier = Modifier, onButtonClick: (/*List<String>*/) -> Unit) {
    // String of the button
    val endgame = stringResource(R.string.endgame)

    // Button to end the current game
    Button(
        modifier = modifier
            .padding(8.dp),
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(containerColor = OrangeA400)
    ) {
        Text(text = endgame)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen( buttonAction = {/*listOf<String>()*/} )
}