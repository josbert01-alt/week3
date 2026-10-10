package com.example.week3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.week3.tasks.CatClickerScreen
import com.example.week3.tasks.CoffeeOrderScreen
import com.example.week3.tasks.ColorWordMatchingScreen
import com.example.week3.tasks.ReactionTestScreen
import com.example.week3.tasks.RockPaperScissorsScreen
import com.example.week3.tasks.TravelJournalScreen
import com.example.week3.ui.theme.Week3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week3Theme(darkTheme = false, dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        MainApp()
                    }
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    var currentTask by remember { mutableStateOf("MENU") }

    BackHandler(enabled = currentTask != "MENU") {
        currentTask = "MENU"
    }

    if (currentTask == "MENU") {
        MenuScreen(onTaskClick = { task -> currentTask = task })
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Button(onClick = { currentTask = "MENU" }) {
                Text(text = "Back")
            }
            if (currentTask == "REACTION") {
                ReactionTestScreen()
            } else if (currentTask == "CLICKER") {
                CatClickerScreen()
            } else if (currentTask == "COLOR_WORD") {
                ColorWordMatchingScreen()
            } else if (currentTask == "RPS") {
                RockPaperScissorsScreen()
            } else if (currentTask == "COFFEE") {
                CoffeeOrderScreen()
            } else if (currentTask == "TRAVEL") {
                TravelJournalScreen()
            }
        }
    }
}

@Composable
fun MenuScreen(onTaskClick: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onTaskClick("REACTION") }) {
            Text(text = "Reaction Test")
        }
        Button(onClick = { onTaskClick("CLICKER") }) {
            Text(text = "Cat Clicker")
        }
        Button(onClick = { onTaskClick("COLOR_WORD") }) {
            Text(text = "Color Word Matching")
        }
        Button(onClick = { onTaskClick("RPS") }) {
            Text(text = "Rock Paper Scissors")
        }
        Button(onClick = { onTaskClick("COFFEE") }) {
            Text(text = "Coffee Order")
        }
        Button(onClick = { onTaskClick("TRAVEL") }) {
            Text(text = "Travel Journal")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MenuScreenPreview() {
    Week3Theme(darkTheme = false, dynamicColor = false) {
        MenuScreen(onTaskClick = {})
    }
}
