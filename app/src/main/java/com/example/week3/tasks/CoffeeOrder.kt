package com.example.week3.tasks

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.week3.R

fun formatRupiah(amount: Int): String {
    return "Rp " + String.format("%,d", amount).replace(",", ".")
}

@Composable
fun CoffeeOrderScreen() {
    val context = LocalContext.current
    var quantity by remember { mutableStateOf(1) }
    var isLarge by remember { mutableStateOf(false) }

    val brown = Color(0xFF4A2511)
    val orange = Color(0xFFD9622B)
    val lightBorder = Color(0xFFF6D9B8)

    var price = 25000
    if (isLarge) {
        price = 25000 + 6000
    }
    val subtotal = price * quantity
    val tax = subtotal * 10 / 100
    val total = subtotal + tax

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F4F1))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Kopi Kenangan Senja", color = brown, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "☕", fontSize = 22.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, lightBorder)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(
                            Brush.linearGradient(listOf(Color(0xFF70391A), Color(0xFF8D3B1F))),
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_coffee_cup),
                        contentDescription = "Coffee",
                        modifier = Modifier.size(72.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Caramel Latte", color = brown, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Text(text = "Espresso shot, steamed milk & caramel syrup", color = Color(0xFF8D5A3B), fontSize = 13.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = formatRupiah(price) + " / cup", color = orange, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "PILIHAN UKURAN:", color = brown, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var regularBorder = orange
            var regularBg = Color.White
            var regularText = orange
            var largeBorder = lightBorder
            var largeBg = Color(0xFFFEF3E2)
            var largeText = Color(0xFF5D4037)
            if (isLarge) {
                regularBorder = lightBorder
                regularBg = Color(0xFFFEF3E2)
                regularText = Color(0xFF5D4037)
                largeBorder = orange
                largeBg = Color.White
                largeText = orange
            }

            OutlinedButton(
                onClick = { isLarge = false },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, regularBorder),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = regularBg)
            ) {
                Text(text = "Regular (+0)", color = regularText, fontWeight = FontWeight.Bold)
            }
            OutlinedButton(
                onClick = { isLarge = true },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, largeBorder),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = largeBg)
            ) {
                Text(text = "Large (+6k)", color = largeText, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, lightBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Jumlah Pesanan:", color = brown, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(
                        onClick = {
                            if (quantity > 1) {
                                quantity = quantity - 1
                            }
                        },
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFFCDD5E0)),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = "-", color = orange, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = "$quantity",
                        color = brown,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )
                    OutlinedButton(
                        onClick = { quantity = quantity + 1 },
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFFCDD5E0)),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFFCEED8)),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = "+", color = orange, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFBF3EA), RoundedCornerShape(10.dp))
                .border(1.dp, Color(0xFFF2B985), RoundedCornerShape(10.dp))
                .padding(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Subtotal:", color = Color(0xFF8D5A3B), fontSize = 13.sp)
                Text(text = formatRupiah(subtotal), color = Color(0xFF8D5A3B), fontSize = 13.sp)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Pajak Resto (10%):", color = Color(0xFF8D5A3B), fontSize = 13.sp)
                Text(text = formatRupiah(tax), color = Color(0xFF8D5A3B), fontSize = 13.sp)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp), color = lightBorder)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Total Tagihan:", color = brown, fontWeight = FontWeight.Bold)
                Text(text = formatRupiah(total), color = orange, fontSize = 17.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                Toast.makeText(context, "$quantity Caramel Latte ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = orange)
        ) {
            Text(text = "TAMBAH KE KERANJANG", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoffeeOrderScreenPreview() {
    CoffeeOrderScreen()
}
