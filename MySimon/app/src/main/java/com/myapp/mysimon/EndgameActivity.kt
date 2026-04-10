package com.myapp.mysimon

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.myapp.mysimon.ui.theme.MySimonTheme

class EndgameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display on API level < 35
        enableEdgeToEdge()

        // Set and display the UI content
        setContent {
            MySimonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EndScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                        // la lista di stringhe viene presa dallo stack e passata alla funzione EndScreen
                    )
                }
            }
        }
    }
}

// Function of the second screen of the app
// Contain the sequences of the previous games and how many times buttons were clicked in each sequence
@Composable
fun EndScreen(modifier: Modifier = Modifier) {
    // String used on this activity
    val prevSequence = stringResource(R.string.prev_sequence)

    LazyColumn(modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        item  {
            Text(
                text = prevSequence,
                modifier = modifier.padding(8.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndScreenPreview() {
    EndScreen()
}