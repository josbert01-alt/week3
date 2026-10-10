package com.example.week3.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class GameState {
    INITIAL, COUNTDOWN, RUNNING, GAME_OVER
}

enum class Mode {
    COLOR, TEXT
}

enum class Choice {
    LEFT, RIGHT
}

enum class ColorName(val label: String, val color: Color) {
    RED("RED", Color(0xFFE53935)),
    BLUE("BLUE", Color(0xFF1E88E5)),
    GREEN("GREEN", Color(0xFF43A047)),
    ORANGE("ORANGE", Color(0xFFFB8C00)),
    PURPLE("PURPLE", Color(0xFF8E24AA))
}

@Composable
fun ColorWordMatchingScreen() {
    var gameState by remember { mutableStateOf(GameState.INITIAL) }
    var countdownText by remember { mutableStateOf("3") }
    var score by remember { mutableStateOf(0) }
    var strikes by remember { mutableStateOf(0) }
    var bestScore by remember { mutableStateOf(0) }

    var mode by remember { mutableStateOf(Mode.COLOR) }
    var word by remember { mutableStateOf(ColorName.RED) }
    var ink by remember { mutableStateOf(ColorName.BLUE) }
    var inkOnLeft by remember { mutableStateOf(true) }
    var questionNumber by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(5000L) }

    fun newQuestion() {
        mode = Mode.entries.random()
        word = ColorName.entries.random()
        ink = ColorName.entries.random()
        while (ink == word) {
            ink = ColorName.entries.random()
        }
        inkOnLeft = Random.nextBoolean()
        timeLeft = 5000L
        questionNumber = questionNumber + 1
    }

    fun wrongAnswer() {
        strikes = strikes + 1
        if (strikes >= 3) {
            if (score > bestScore) {
                bestScore = score
            }
            gameState = GameState.GAME_OVER
        } else {
            newQuestion()
        }
    }

    fun onChoice(choice: Choice) {
        var picked = word
        if (choice == Choice.LEFT && inkOnLeft) {
            picked = ink
        }
        if (choice == Choice.RIGHT && !inkOnLeft) {
            picked = ink
        }

        var correctAnswer = word
        if (mode == Mode.COLOR) {
            correctAnswer = ink
        }

        if (picked == correctAnswer) {
            score = score + 1
            newQuestion()
        } else {
            wrongAnswer()
        }
    }

    LaunchedEffect(gameState) {
        if (gameState == GameState.COUNTDOWN) {
            countdownText = "3"
            delay(1000)
            countdownText = "2"
            delay(1000)
            countdownText = "1"
            delay(1000)
            countdownText = "Start!"
            delay(700)
            newQuestion()
            gameState = GameState.RUNNING
        }
    }

    LaunchedEffect(questionNumber, gameState) {
        if (gameState == GameState.RUNNING) {
            while (timeLeft > 0) {
                delay(100)
                timeLeft = timeLeft - 100
            }
            wrongAnswer()
        }
    }

    var leftColor = word
    var rightColor = ink
    if (inkOnLeft) {
        leftColor = ink
        rightColor = word
    }

    var clue = "Pilih NAMA KATA"
    if (mode == Mode.COLOR) {
        clue = "Pilih WARNA TINTA"
    }

    val seconds = (timeLeft + 999) / 1000

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (gameState == GameState.INITIAL) {
            Text(
                text = "Welcome\nto\nColor Word Matching",
                fontSize = 26.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            GrayButton("Start Game") {
                score = 0
                strikes = 0
                gameState = GameState.COUNTDOWN
            }
        }

        if (gameState == GameState.COUNTDOWN) {
            Text(text = countdownText, fontSize = 40.sp, fontWeight = FontWeight.Light)
        }

        if (gameState == GameState.RUNNING) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Mode: ${mode.name}", fontSize = 16.sp)
                Text(text = "✅ $score   ❌ $strikes/3", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = clue,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF5C6BC0))
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "$seconds s", fontSize = 22.sp, fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = word.label, color = ink.color, fontSize = 56.sp, fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.height(64.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GrayButton(leftColor.label, Modifier.width(120.dp)) {
                    onChoice(Choice.LEFT)
                }
                GrayButton(rightColor.label, Modifier.width(120.dp)) {
                    onChoice(Choice.RIGHT)
                }
            }
        }

        if (gameState == GameState.GAME_OVER) {
            Text(text = "Game Over!", fontSize = 32.sp, fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Your Score", fontSize = 20.sp, fontWeight = FontWeight.Light)
            Text(text = "$score", fontSize = 28.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Best Score", fontSize = 13.sp)
            Text(text = "$bestScore", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(24.dp))
            GrayButton("Restart Game") {
                score = 0
                strikes = 0
                gameState = GameState.COUNTDOWN
            }
            Spacer(modifier = Modifier.height(8.dp))
            GrayButton("Exit") {
                gameState = GameState.INITIAL
            }
        }
    }
}

@Composable
fun GrayButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFB0BEC5),
            contentColor = Color(0xFF263238)
        )
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ColorWordMatchingScreenPreview() {
    ColorWordMatchingScreen()
}
