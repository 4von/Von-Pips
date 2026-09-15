package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CalculationResult
import com.example.model.ForexPair
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.BorderNavy
import com.example.ui.theme.CyanBadgeBg
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy850
import com.example.ui.theme.Navy900
import com.example.ui.theme.Navy950
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonCyanDim
import com.example.ui.theme.NeonCyanGlow
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.TextSlate200
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ExecutionOutputCard(
    result: CalculationResult,
    selectedPair: ForexPair,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isCopied by remember { mutableStateOf(false) }

    val currencyFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }
    val integerFormatter = remember {
        NumberFormat.getIntegerInstance(Locale.US)
    }

    LaunchedEffect(isCopied) {
        if (isCopied) {
            delay(2200)
            isCopied = false
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    listOf(Navy900, Navy950)
                )
            )
            .border(2.dp, NeonCyan.copy(alpha = 0.45f), RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .alpha(pulseAlpha)
                            .clip(CircleShape)
                            .background(NeonEmerald)
                    )
                    Text(
                        text = "EXECUTION OUTPUT",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp,
                        color = TextSlate200
                    )
                }

                Text(
                    text = if (result.timestamp.isNotEmpty()) "Calculated at ${result.timestamp}" else "Live updated",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = TextSlate500
                )
            }

            HorizontalDivider(color = Navy800)

            // Prominent Recommended Lot Size Hero
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "RECOMMENDED POSITION VOLUME",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    letterSpacing = 1.sp,
                    color = TextSlate400
                )

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (result.isValid) String.format(Locale.US, "%.2f", result.lotSize) else "--",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Black,
                        fontSize = 46.sp,
                        letterSpacing = (-1).sp,
                        color = NeonCyan,
                        modifier = Modifier.testTag("recommended_lot_size")
                    )
                    Text(
                        text = "LOTS",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = NeonCyanDim,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                // Classification pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(CyanBadgeBg)
                        .border(1.dp, NeonCyan.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (result.isValid) result.classification else "Awaiting Valid Inputs",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        color = NeonCyan
                    )
                }
            }

            // Dual Key Metrics Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Metric 1: Cash Risked
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Navy950)
                        .border(1.dp, Navy800, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "CASH RISKED",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            color = TextSlate400
                        )
                        Text(
                            text = if (result.isValid) currencyFormatter.format(result.cashRisked) else "$0.00",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = NeonEmerald,
                            modifier = Modifier.testTag("total_cash_risked")
                        )
                        Text(
                            text = if (result.isValid) "${String.format(Locale.US, "%.2f", result.effectiveRiskPercent)}% of Balance" else "--",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            color = TextSlate500
                        )
                    }
                }

                // Metric 2: Position Units
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Navy950)
                        .border(1.dp, Navy800, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "POSITION UNITS",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            color = TextSlate400
                        )
                        Text(
                            text = if (result.isValid) integerFormatter.format(result.positionUnits) else "0",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = TextWhite,
                            modifier = Modifier.testTag("position_units")
                        )
                        Text(
                            text = "Base Currency Units",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            color = TextSlate500
                        )
                    }
                }
            }

            // Specifications Box
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Navy950.copy(alpha = 0.6f))
                    .border(1.dp, Navy800, RoundedCornerShape(10.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Pip Value per Lot:",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = TextSlate400
                    )
                    Text(
                        text = "$${String.format(Locale.US, "%.2f", result.pipValuePerLot)} USD",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        color = TextWhite
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Stop Loss Span:",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = TextSlate400
                    )
                    Text(
                        text = "${String.format(Locale.US, "%.1f", result.slPips)} Pips",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        color = TextWhite
                    )
                }

                HorizontalDivider(color = Navy800.copy(alpha = 0.7f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Loss per 1 Pip Move:",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = TextSlate400
                    )
                    Text(
                        text = if (result.isValid) currencyFormatter.format(result.lossPerPip) else "$0.00",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = AccentAmber
                    )
                }
            }

            // Copy Parameters Action Button
            Button(
                onClick = {
                    val orderSpec = buildString {
                        appendLine("Trade Order Spec:")
                        appendLine("Pair: ${selectedPair.symbol}")
                        appendLine("Lot Size: ${String.format(Locale.US, "%.2f", result.lotSize)} Lots")
                        appendLine("Risk Amount: ${currencyFormatter.format(result.cashRisked)}")
                        appendLine("Stop Loss: ${result.slPips} pips")
                        appendLine("Units: ${integerFormatter.format(result.positionUnits)}")
                    }
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("FXPulse Order Spec", orderSpec))
                    isCopied = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("copy_order_parameters_button"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Navy850,
                    contentColor = TextSlate200
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Navy700)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (isCopied) Icons.Rounded.Done else Icons.Rounded.ContentCopy,
                        contentDescription = "Copy",
                        tint = if (isCopied) NeonEmerald else NeonCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = if (isCopied) "✓ Copied Order Spec to Clipboard!" else "Copy Order Parameters to Clipboard",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isCopied) NeonEmerald else TextSlate200
                    )
                }
            }
        }
    }
}
