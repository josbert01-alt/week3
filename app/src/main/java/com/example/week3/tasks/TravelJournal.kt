package com.example.week3.tasks

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TravelJournalScreen() {
    val context = LocalContext.current
    var spot by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var satisfaction by remember { mutableStateOf("Luar Biasa ★") }

    val darkBg = Color(0xFF111827)
    val blue = Color(0xFF60B5F0)
    val gray = Color(0xFF9CA3AF)

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        focusedContainerColor = Color(0xFF1F2937),
        unfocusedContainerColor = Color(0xFF1F2937),
        focusedBorderColor = blue,
        unfocusedBorderColor = Color(0xFF374151),
        focusedLabelColor = blue,
        unfocusedLabelColor = gray,
        cursorColor = blue
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBg)
            .padding(16.dp)
    ) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2B5DC8), RoundedCornerShape(14.dp))
                    .padding(16.dp)
            ) {
                Text(text = "LOG PERJALANAN", color = Color(0xFFBFDBFE), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text(text = "Tromsø, Norway ❄️", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "Ekspedisi Aurora Borealis", color = Color(0xFFDBEAFE), fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = spot,
                onValueChange = { spot = it },
                label = { Text(text = "SPOT WISATA FAVORIT") },
                placeholder = { Text(text = "Fjellheisen Cable Car") },
                singleLine = true,
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = experience,
                onValueChange = { experience = it },
                label = { Text(text = "APA YANG PALING KAMU NIKMATI?") },
                placeholder = { Text(text = "Pemandangan langit malam hijau aurora...") },
                minLines = 3,
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text(text = "CATATAN TAMBAHAN / PERLENGKAPAN") },
                placeholder = { Text(text = "(Ketik perlengkapan ekstra di sini...)") },
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "TINGKAT KEPUASAN:", color = Color(0xFFD1D5DB), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val options = listOf("Biasa", "Seru", "Luar Biasa ★")
                for (option in options) {
                    var bgColor = Color(0xFF1F2937)
                    var textColor = gray
                    var borderColor = Color(0xFF4B5563)
                    if (satisfaction == option) {
                        bgColor = blue
                        textColor = darkBg
                        borderColor = blue
                    }
                    Button(
                        onClick = { satisfaction = option },
                        border = BorderStroke(1.dp, borderColor),
                        colors = ButtonDefaults.buttonColors(containerColor = bgColor, contentColor = textColor)
                    ) {
                        Text(text = option, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(text = "Status: ", color = gray, fontSize = 13.sp)
                Text(text = "Draft Tersimpan", color = blue, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            FloatingActionButton(
                onClick = {
                    Toast.makeText(context, "Jurnal $spot berhasil disimpan!", Toast.LENGTH_SHORT).show()
                },
                containerColor = blue,
                contentColor = darkBg
            ) {
                Text(text = "+", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TravelJournalScreenPreview() {
    TravelJournalScreen()
}
