package com.example.week3.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.week3.R
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class ReactionState {
    START, WAITING, READY, TRIAL_DONE, FAIL, FINAL
}

@Composable
fun ReactionTestScreen() {
    var state by remember { mutableStateOf(ReactionState.START) }
    val trials = remember { mutableStateListOf<Long>() }
    var startTime by remember { mutableStateOf(0L) }

    LaunchedEffect(state) {
        if (state == ReactionState.WAITING) {
            val waitTime = Random.nextLong(500, 4501)
            delay(waitTime)
            startTime = System.currentTimeMillis()
            state = ReactionState.READY
        }
    }

    var total = 0L
    var validCount = 0
    for (time in trials) {
        if (time != -1L) {
            total = total + time
            validCount = validCount + 1
        }
    }
    var average = 0L
    if (validCount > 0) {
        average = total / validCount
    }

    var message = "YOU LIKE A SNAIL BRO"
    var picture = R.drawable.person_thumbs_down
    var finalColor = Color(0xFFFF5722)
    if (validCount == 0) {
        message = "NO VALID TRIAL BRO"
    } else if (average < 180) {
        message = "DANG YOU ARE SO FAST BRO!"
        picture = R.drawable.person_salute
        finalColor = Color(0xFF00E676)
    } else if (average < 280) {
        message = "YOUR REFLEX IS GOOD"
        picture = R.drawable.person_thumbs_up
        finalColor = Color(0xFF2196F3)
    } else if (average < 450) {
        message = "MEH LIKE OTHER PERSON"
        picture = R.drawable.person_standard
        finalColor = Color(0xFFFF9800)
    }

    val bgColor = when (state) {
        ReactionState.START -> Color(0xFF6DCFDF)
        ReactionState.WAITING -> Color(0xFFD6D6D6)
        ReactionState.READY -> Color(0xFF4CAF50)
        ReactionState.TRIAL_DONE -> Color(0xFF4CAF50)
        ReactionState.FAIL -> Color(0xFFF44141)
        ReactionState.FINAL -> finalColor
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .clickable {
                if (state == ReactionState.START) {
                    state = ReactionState.WAITING
                } else if (state == ReactionState.WAITING) {
                    trials.add(-1L)
                    state = ReactionState.FAIL
                } else if (state == ReactionState.READY) {
                    val reaction = System.currentTimeMillis() - startTime
                    trials.add(reaction)
                    if (trials.size == 3) {
                        state = ReactionState.FINAL
                    } else {
                        state = ReactionState.TRIAL_DONE
                    }
                } else if (state == ReactionState.TRIAL_DONE) {
                    state = ReactionState.WAITING
                } else if (state == ReactionState.FAIL) {
                    if (trials.size == 3) {
                        state = ReactionState.FINAL
                    } else {
                        state = ReactionState.START
                    }
                } else if (state == ReactionState.FINAL) {
                    trials.clear()
                    state = ReactionState.START
                }
            }
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state == ReactionState.START) {
            ReactionContent("Reaction", "⚡", "Test", "Click to Start")
        } else if (state == ReactionState.WAITING) {
            ReactionContent("Get Ready", "⚠️", "Wait for green light...", "DON'T CLICK YET!")
        } else if (state == ReactionState.READY) {
            ReactionContent("GO!", "🏃", "CLICK NOW!", "TAP AS FAST AS YOU CAN!")
        } else if (state == ReactionState.TRIAL_DONE) {
            ReactionContent(
                "Trial ${trials.size} Complete!",
                "✅",
                "Time: ${trials[trials.size - 1]}ms",
                "Continue to Trial ${trials.size + 1}"
            )
        } else if (state == ReactionState.FAIL) {
            var hint = "TRY AGAIN"
            if (trials.size == 3) {
                hint = "SEE RESULT"
            }
            ReactionContent("FAIL!", "👎", "You clicked too early, TRY TO READ THE RULE BRO", hint)
        } else if (state == ReactionState.FINAL) {
            Text(text = message, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = picture),
                contentDescription = message,
                modifier = Modifier.size(140.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Average: ${average}ms", color = Color.White, fontSize = 18.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Click to Start New Test", color = Color.White, fontSize = 14.sp, textAlign = TextAlign.Center)
        }

        if (trials.size > 0) {
            Spacer(modifier = Modifier.height(24.dp))
            TrialResultsCard(trials, average, state == ReactionState.FINAL)
        }
    }
}

@Composable
fun ReactionContent(title: String, icon: String, text: String, hint: String) {
    Text(text = title, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.height(24.dp))
    Text(text = icon, fontSize = 72.sp)
    Spacer(modifier = Modifier.height(24.dp))
    Text(text = text, color = Color.White, fontSize = 18.sp, textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.height(12.dp))
    Text(text = hint, color = Color.White, fontSize = 14.sp, textAlign = TextAlign.Center)
}

@Composable
fun TrialResultsCard(trials: List<Long>, average: Long, showAverage: Boolean) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Trial Results", color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                for (i in 0..2) {
                    var result = "-"
                    var numberColor = Color.Gray
                    if (i < trials.size && trials[i] != -1L) {
                        result = "${trials[i]}ms"
                        numberColor = Color(0xFF4CAF50)
                    }
                    Column(
                        modifier = Modifier.width(64.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "${i + 1}", color = numberColor, fontWeight = FontWeight.Bold)
                        Text(text = result, color = Color.Black, fontSize = 13.sp)
                    }
                }
            }
            if (showAverage) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Average Score", color = Color(0xFF1976D2), fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(text = "${average}ms", color = Color(0xFFE65100), fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReactionTestScreenPreview() {
    ReactionTestScreen()
}
