package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CalculationResult
import com.example.ui.theme.AccentBlue
import com.example.ui.theme.BorderNavy
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.Navy950
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.TextSlate300
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite
import java.util.Locale

@Composable
fun LotTierCard(
    result: CalculationResult,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Navy900)
            .border(1.dp, BorderNavy, RoundedCornerShape(16.dp))
            .padding(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LOT TIER EQUIVALENT",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    letterSpacing = 0.5.sp,
                    color = TextWhite
                )
                Text(
                    text = "CONTRACT DISTRIBUTION",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.sp,
                    letterSpacing = 0.5.sp,
                    color = TextSlate500
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Standard Lots
                TierRow(
                    indicatorColor = NeonCyan,
                    label = "Standard Lots (100k)",
                    value = if (result.isValid) String.format(Locale.US, "%.2f", result.standardLots) else "0.00",
                    valueColor = NeonCyan
                )

                // Mini Lots
                TierRow(
                    indicatorColor = AccentBlue,
                    label = "Mini Lots (10k)",
                    value = if (result.isValid) String.format(Locale.US, "%.2f", result.miniLots) else "0.00",
                    valueColor = AccentBlue
                )

                // Micro Lots
                TierRow(
                    indicatorColor = NeonEmerald,
                    label = "Micro Lots (1k)",
                    value = if (result.isValid) String.format(Locale.US, "%.2f", result.microLots) else "0.00",
                    valueColor = NeonEmerald
                )
            }
        }
    }
}

@Composable
private fun TierRow(
    indicatorColor: androidx.compose.ui.graphics.Color,
    label: String,
    value: String,
    valueColor: androidx.compose.ui.graphics.Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Navy950)
            .border(1.dp, Navy800, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 9.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(indicatorColor)
            )
            Text(
                text = label,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                color = TextSlate300
            )
        }

        Text(
            text = value,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = valueColor
        )
    }
}
