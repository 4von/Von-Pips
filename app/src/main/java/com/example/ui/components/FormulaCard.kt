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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.BorderNavy
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.Navy950
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.TextSlate200
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite

@Composable
fun FormulaCard(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Navy900.copy(alpha = 0.7f))
            .border(1.dp, BorderNavy.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
            .padding(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Mathematical Sizing Formula",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = TextSlate200
                    )
                }

                Text(
                    text = "IEEE 754 Accurate",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = NeonCyan
                )
            }

            // Formula Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Navy950)
                    .border(1.dp, Navy800, RoundedCornerShape(8.dp))
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Lot Size",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = NeonEmerald
                    )
                    Text(
                        text = "=",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = TextWhite
                    )
                    Text(
                        text = "Total Cash Risked ($)",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = NeonCyan
                    )
                    Text(
                        text = "÷",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = TextWhite
                    )
                    Text(
                        text = "(",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = TextSlate400
                    )
                    Text(
                        text = "SL Pips",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = AccentAmber
                    )
                    Text(
                        text = "×",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = TextWhite
                    )
                    Text(
                        text = "Standard Pip Value",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = AccentPurple
                    )
                    Text(
                        text = ")",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = TextSlate400
                    )
                }
            }

            Text(
                text = "Standard Forex contracts consist of 100,000 units per 1.00 standard lot. For non-USD quoted pairs (e.g., USD/CAD or EUR/AUD), pip values are normalized into your USD account base using current FX spot rates.",
                fontSize = 11.sp,
                lineHeight = 16.sp,
                color = TextSlate400
            )
        }
    }
}
