package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ForexPair
import com.example.ui.theme.BorderNavy
import com.example.ui.theme.CyanBadgeBg
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy850
import com.example.ui.theme.Navy900
import com.example.ui.theme.Navy950
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextSlate200
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite
import java.util.Locale

@Composable
fun PipMatrixTable(
    selectedPair: ForexPair,
    onSelectPair: (ForexPair) -> Unit,
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
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Institutional Pip Matrix (USD Account Base)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = TextWhite
                    )
                    Text(
                        text = "Benchmark pip values calibrated for 1 standard lot (100,000 units).",
                        fontSize = 11.sp,
                        color = TextSlate400
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(CyanBadgeBg)
                        .border(1.dp, NeonCyan.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "1 Lot = 100,000 Base Units",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp,
                        color = NeonCyan
                    )
                }
            }

            // Table with horizontal scroll support
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Navy800, RoundedCornerShape(10.dp))
                    .background(Navy950)
                    .horizontalScroll(scrollState)
            ) {
                // Table Header
                Row(
                    modifier = Modifier
                        .background(Navy850)
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TableHeaderCell(text = "Pair", width = 85.dp)
                    TableHeaderCell(text = "Category", width = 140.dp)
                    TableHeaderCell(text = "Pip Size", width = 75.dp)
                    TableHeaderCell(text = "Standard Pip Value", width = 130.dp)
                    TableHeaderCell(text = "At 0.10 (Mini)", width = 105.dp)
                    TableHeaderCell(text = "At 0.01 (Micro)", width = 105.dp)
                    TableHeaderCell(text = "Action", width = 70.dp, alignRight = true)
                }

                HorizontalDivider(color = Navy800)

                // Table Rows
                ForexPair.ALL_PAIRS.forEachIndexed { index, pair ->
                    val isCurrent = pair.symbol == selectedPair.symbol
                    Row(
                        modifier = Modifier
                            .background(if (isCurrent) NeonCyan.copy(alpha = 0.08f) else if (index % 2 == 1) Navy950.copy(alpha = 0.6f) else Navy950)
                            .padding(horizontal = 12.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Pair
                        Box(modifier = Modifier.width(85.dp)) {
                            Text(
                                text = pair.symbol,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = if (isCurrent) NeonCyan else TextWhite
                            )
                        }

                        // Category
                        Box(modifier = Modifier.width(140.dp)) {
                            Text(
                                text = pair.category,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                color = TextSlate400
                            )
                        }

                        // Pip Size
                        Box(modifier = Modifier.width(75.dp)) {
                            Text(
                                text = pair.pipSize.toString(),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                color = TextSlate400
                            )
                        }

                        // Standard Pip Value
                        Box(modifier = Modifier.width(130.dp)) {
                            Text(
                                text = "$${String.format(Locale.US, "%.2f", pair.pipValue)}",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = NeonCyan
                            )
                        }

                        // Mini Pip Value
                        Box(modifier = Modifier.width(105.dp)) {
                            Text(
                                text = "$${String.format(Locale.US, "%.2f", pair.pipValue * 0.1)}",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                color = TextSlate200
                            )
                        }

                        // Micro Pip Value
                        Box(modifier = Modifier.width(105.dp)) {
                            Text(
                                text = "$${String.format(Locale.US, "%.2f", pair.pipValue * 0.01)}",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                color = TextSlate200
                            )
                        }

                        // Load button
                        Box(
                            modifier = Modifier.width(70.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Button(
                                onClick = { onSelectPair(pair) },
                                modifier = Modifier
                                    .testTag("load_pair_${pair.symbol.replace('/', '_')}")
                                    .clip(RoundedCornerShape(4.dp)),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isCurrent) NeonCyan else Navy800,
                                    contentColor = if (isCurrent) Navy950 else TextSlate200
                                ),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (isCurrent) "Loaded" else "Load",
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }

                    if (index < ForexPair.ALL_PAIRS.lastIndex) {
                        HorizontalDivider(color = Navy800.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}

@Composable
private fun TableHeaderCell(
    text: String,
    width: androidx.compose.ui.unit.Dp,
    alignRight: Boolean = false
) {
    Box(
        modifier = Modifier.width(width),
        contentAlignment = if (alignRight) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = text,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            color = TextSlate500
        )
    }
}
