package com.example.week3.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.week3.R
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun CatClickerScreen() {
    var coins by remember { mutableStateOf(0) }
    var coinsPerTap by remember { mutableStateOf(1) }
    var upgradeCost by remember { mutableStateOf(10) }
    var mouthOpen by remember { mutableStateOf(false) }
    var tapCount by remember { mutableStateOf(0) }

    LaunchedEffect(tapCount) {
        if (tapCount > 0) {
            delay(300)
            mouthOpen = false
        }
    }

    val nextValue = (coinsPerTap * 1.5).roundToInt()
    val canUpgrade = coins >= upgradeCost

    var catImage = R.drawable.popcat_closed
    var catText = "Purr~"
    if (mouthOpen) {
        catImage = R.drawable.popcat_open
        catText = "Meow!"
    }

    var buttonText = "Find ${upgradeCost - coins} more coins"
    if (canUpgrade) {
        buttonText = "Pay for $upgradeCost coins"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.cat_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x55FFFFFF))
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Your Coins", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "$coins", color = Color(0xFF00E676), fontSize = 40.sp, fontWeight = FontWeight.Bold)
                    Text(text = "$coinsPerTap coins per tap", color = Color.White, fontSize = 13.sp)
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Tap the Cat!", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = catImage),
                    contentDescription = "Pop Cat",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(190.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            coins = coins + coinsPerTap
                            mouthOpen = true
                            tapCount = tapCount + 1
                        }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = catText, color = Color.White, fontSize = 16.sp)
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xEEFFFFFF))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Give Me Your Coin", color = Color(0xFF424242), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Next upgrade: +$nextValue coins per tap", color = Color.Gray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            coins = coins - upgradeCost
                            coinsPerTap = nextValue
                            upgradeCost = upgradeCost * 2
                        },
                        enabled = canUpgrade,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            disabledContainerColor = Color(0xFFBDBDBD),
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text(text = buttonText, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CatClickerScreenPreview() {
    CatClickerScreen()
}
