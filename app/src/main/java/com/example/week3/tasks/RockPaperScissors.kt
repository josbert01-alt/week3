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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class Rps(val emoji: String, val label: String) {
    ROCK("✊", "Rock"),
    PAPER("✋", "Paper"),
    SCISSOR("✌️", "Scissor")
}

enum class State {
    INITIAL, PICK, REVEAL, FINISHED
}

enum class Result {
    WIN, LOSE, DRAW
}

@Composable
fun RockPaperScissorsScreen() {
    val bestOf = 5
    val target = bestOf / 2 + 1

    var state by rememberSaveable { mutableStateOf(State.INITIAL) }
    var yourScore by rememberSaveable { mutableStateOf(0) }
    var cpuScore by rememberSaveable { mutableStateOf(0) }
    var bestScore by rememberSaveable { mutableStateOf(0) }

    var playerPick by remember { mutableStateOf(Rps.ROCK) }
    var cpuPick by remember { mutableStateOf(Rps.ROCK) }
    var result by remember { mutableStateOf(Result.DRAW) }
    var buttons by remember { mutableStateOf(Rps.entries.shuffled()) }

    LaunchedEffect(state) {
        if (state == State.REVEAL) {
            delay(700)
            if (yourScore >= target || cpuScore >= target) {
                val diff = yourScore - cpuScore
                if (diff > bestScore) {
                    bestScore = diff
                }
                state = State.FINISHED
            } else {
                buttons = Rps.entries.shuffled()
                state = State.PICK
            }
        }
    }

    var resultText = "Draw"
    if (result == Result.WIN) {
        resultText = "You Win!"
    } else if (result == Result.LOSE) {
        resultText = "You Lose"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "🧑 $yourScore — $cpuScore 🤖",
                fontSize = 18.sp,
                modifier = Modifier.semantics { contentDescription = "You $yourScore, CPU $cpuScore" }
            )
            Text(text = "Best of $bestOf", fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(48.dp))

        if (state == State.INITIAL) {
            Text(text = "Rock • Paper • Scissors", fontSize = 24.sp, fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.height(16.dp))
            GrayButton("Start", Modifier.fillMaxWidth(0.6f)) {
                yourScore = 0
                cpuScore = 0
                buttons = Rps.entries.shuffled()
                state = State.PICK
            }
        }

        if (state == State.PICK) {
            Text(text = "Pick your move!", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "❔ VS ❔", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (move in buttons) {
                    GrayButton(
                        "${move.emoji} ${move.label}",
                        Modifier.semantics { contentDescription = "Pick ${move.label}" }
                    ) {
                        val cpu = Rps.entries.random()
                        if (move == cpu) {
                            result = Result.DRAW
                        } else if (
                            (move == Rps.ROCK && cpu == Rps.SCISSOR) ||
                            (move == Rps.PAPER && cpu == Rps.ROCK) ||
                            (move == Rps.SCISSOR && cpu == Rps.PAPER)
                        ) {
                            result = Result.WIN
                            yourScore = yourScore + 1
                        } else {
                            result = Result.LOSE
                            cpuScore = cpuScore + 1
                        }
                        playerPick = move
                        cpuPick = cpu
                        state = State.REVEAL
                    }
                }
            }
        }

        if (state == State.REVEAL) {
            Text(
                text = "${playerPick.emoji} VS ${cpuPick.emoji}",
                fontSize = 40.sp,
                modifier = Modifier.semantics {
                    contentDescription = "You ${playerPick.label} VS CPU ${cpuPick.label}"
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = resultText,
                fontSize = 18.sp,
                modifier = Modifier.semantics { contentDescription = "Result: $resultText" }
            )
        }

        if (state == State.FINISHED) {
            var matchText = "You Lose the Match"
            if (yourScore > cpuScore) {
                matchText = "You Win the Match!"
            }
            Text(
                text = matchText,
                fontSize = 24.sp,
                modifier = Modifier.semantics { contentDescription = matchText }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "🧑 $yourScore - $cpuScore 🤖", fontSize = 16.sp)
            Text(text = "Best Score: $bestScore", fontSize = 13.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GrayButton("Restart") {
                    yourScore = 0
                    cpuScore = 0
                    buttons = Rps.entries.shuffled()
                    state = State.PICK
                }
                GrayButton("Exit") {
                    yourScore = 0
                    cpuScore = 0
                    state = State.INITIAL
                }
            }
        }
        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RockPaperScissorsScreenPreview() {
    RockPaperScissorsScreen()
}
