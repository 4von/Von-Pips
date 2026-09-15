package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ForexPair
import com.example.model.RiskType
import com.example.ui.theme.AccentAmber
import com.example.ui.theme.AccentRose
import com.example.ui.theme.BorderNavy
import com.example.ui.theme.CyanBadgeBg
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy850
import com.example.ui.theme.Navy900
import com.example.ui.theme.Navy950
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonCyanDim
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.TextSlate100
import com.example.ui.theme.TextSlate200
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500
import com.example.ui.theme.TextWhite
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TradeParametersCard(
    balance: String,
    onBalanceChange: (String) -> Unit,
    onBalancePreset: (Double) -> Unit,
    selectedPair: ForexPair,
    onPairSelected: (ForexPair) -> Unit,
    riskType: RiskType,
    onRiskTypeChange: (RiskType) -> Unit,
    riskInput: String,
    onRiskInputChange: (String) -> Unit,
    onRiskPreset: (Double) -> Unit,
    stopLossInput: String,
    onStopLossChange: (String) -> Unit,
    onStopLossPreset: (Double) -> Unit,
    validationErrors: List<String>,
    onRecalculate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val currencyFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale.US).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
    }

    var pairDropdownExpanded by remember { mutableStateOf(false) }

    val parsedBalance = balance.toDoubleOrNull() ?: 0.0
    val parsedRisk = riskInput.toDoubleOrNull() ?: 0.0

    val calculatedExposure = if (riskType == RiskType.PERCENTAGE) {
        (parsedBalance * parsedRisk) / 100.0
    } else {
        parsedRisk
    }

    val effectiveExposurePercent = if (parsedBalance > 0) {
        if (riskType == RiskType.PERCENTAGE) parsedRisk else (parsedRisk / parsedBalance) * 100.0
    } else 0.0

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Navy900)
            .border(1.dp, BorderNavy, RoundedCornerShape(16.dp))
    ) {
        // Gradient Accent line on top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(NeonCyan, Color(0xFF3B82F6), NeonEmerald)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header Row
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
                            .size(10.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(NeonCyan)
                    )
                    Text(
                        text = "Trade & Risk Parameters",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextWhite
                    )
                }
                Text(
                    text = "Step 1 of 2",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = TextSlate500
                )
            }

            // Validation Alert Banner
            AnimatedVisibility(
                visible = validationErrors.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(AccentRose.copy(alpha = 0.12f))
                        .border(1.dp, AccentRose.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = "Validation Alert",
                        tint = AccentRose,
                        modifier = Modifier.size(18.dp)
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "Input Validation Notice:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = AccentRose
                        )
                        validationErrors.forEach { err ->
                            Text(
                                text = "• $err",
                                fontSize = 11.sp,
                                color = TextSlate200
                            )
                        }
                    }
                }
            }

            // Field 1: Account Balance ($ USD)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ACCOUNT BALANCE ($ USD)",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp,
                        color = TextSlate200
                    )
                    // Preset Chips
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf(5000.0 to "$5k", 10000.0 to "$10k", 25000.0 to "$25k", 100000.0 to "$100k").forEach { (amt, label) ->
                            PresetChip(
                                label = label,
                                isSelected = parsedBalance == amt,
                                onClick = { onBalancePreset(amt) }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = balance,
                    onValueChange = onBalanceChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("account_balance_input"),
                    leadingIcon = {
                        Text(
                            text = "$",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = NeonCyan
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Navy950,
                        unfocusedContainerColor = Navy950.copy(alpha = 0.8f),
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = Navy700,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total equity or funded prop-firm account capital.",
                        fontSize = 11.sp,
                        color = TextSlate500
                    )
                    Text(
                        text = "Equity: ${currencyFormatter.format(parsedBalance.coerceAtLeast(0.0))}",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = NeonEmerald
                    )
                }
            }

            // Field 2: Currency Pair / Asset Dropdown Selector
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CURRENCY PAIR / ASSET",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp,
                        color = TextSlate200
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(CyanBadgeBg)
                            .border(1.dp, NeonCyan.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Pip Value: $${String.format(Locale.US, "%.2f", selectedPair.pipValue)} / lot",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            color = NeonCyan
                        )
                    }
                }

                // Custom clickable dropdown container
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("currency_pair_dropdown")
                            .clip(RoundedCornerShape(10.dp))
                            .background(Navy950)
                            .border(1.dp, if (pairDropdownExpanded) NeonCyan else Navy700, RoundedCornerShape(10.dp))
                            .clickable { pairDropdownExpanded = true }
                            .padding(horizontal = 14.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${selectedPair.symbol} (${selectedPair.name})",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = TextWhite
                            )
                            Text(
                                text = "${selectedPair.category} • Pip Size: ${selectedPair.pipSize}",
                                fontSize = 11.sp,
                                color = TextSlate400
                            )
                        }
                        Icon(
                            imageVector = Icons.Rounded.ArrowDropDown,
                            contentDescription = "Select currency pair",
                            tint = NeonCyan,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = pairDropdownExpanded,
                        onDismissRequest = { pairDropdownExpanded = false },
                        modifier = Modifier
                            .background(Navy900)
                            .border(1.dp, BorderNavy, RoundedCornerShape(8.dp))
                    ) {
                        ForexPair.ALL_PAIRS.forEach { pair ->
                            val isSelected = pair.symbol == selectedPair.symbol
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = "${pair.symbol} — ${pair.name}",
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                fontSize = 13.sp,
                                                color = if (isSelected) NeonCyan else TextWhite
                                            )
                                            Text(
                                                text = "${pair.category} • $${String.format(Locale.US, "%.2f", pair.pipValue)}/pip",
                                                fontSize = 11.sp,
                                                color = TextSlate400
                                            )
                                        }
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Rounded.Check,
                                                contentDescription = "Selected",
                                                tint = NeonCyan,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    onPairSelected(pair)
                                    pairDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Text(
                    text = "Pair Specs: ${selectedPair.note}",
                    fontSize = 11.sp,
                    color = TextSlate500
                )
            }

            // Field 3 & 4: Risk Assessment Type & Dynamic Risk Input
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Navy850)
                    .border(1.dp, Navy800, RoundedCornerShape(12.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "RISK ASSESSMENT TYPE",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = TextSlate200
                    )

                    // Segmented Switch
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Navy950)
                            .border(1.dp, Navy700, RoundedCornerShape(8.dp))
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        SegmentedOptionButton(
                            text = "Percentage (%)",
                            isSelected = riskType == RiskType.PERCENTAGE,
                            onClick = { onRiskTypeChange(RiskType.PERCENTAGE) },
                            testTag = "risk_type_percentage"
                        )
                        SegmentedOptionButton(
                            text = "Fixed Cash ($)",
                            isSelected = riskType == RiskType.FIXED_CASH,
                            onClick = { onRiskTypeChange(RiskType.FIXED_CASH) },
                            testTag = "risk_type_cash"
                        )
                    }
                }

                // Dynamic Risk Input
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (riskType == RiskType.PERCENTAGE) "Account Risk Percentage (%)" else "Fixed Risk Amount ($ USD)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextSlate200
                        )

                        // Preset Chips
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            if (riskType == RiskType.PERCENTAGE) {
                                listOf(0.5 to "0.5%", 1.0 to "1.0%", 2.0 to "2.0%", 3.0 to "3.0%").forEach { (r, lbl) ->
                                    PresetChip(
                                        label = lbl,
                                        isSelected = parsedRisk == r,
                                        onClick = { onRiskPreset(r) }
                                    )
                                }
                            } else {
                                listOf(50.0 to "$50", 100.0 to "$100", 250.0 to "$250", 500.0 to "$500").forEach { (c, lbl) ->
                                    PresetChip(
                                        label = lbl,
                                        isSelected = parsedRisk == c,
                                        onClick = { onRiskPreset(c) }
                                    )
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = riskInput,
                        onValueChange = onRiskInputChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("risk_input_field"),
                        leadingIcon = if (riskType == RiskType.FIXED_CASH) {
                            {
                                Text(
                                    text = "$",
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = NeonCyan
                                )
                            }
                        } else null,
                        trailingIcon = if (riskType == RiskType.PERCENTAGE) {
                            {
                                Text(
                                    text = "%",
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = NeonCyan
                                )
                            }
                        } else null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Navy950,
                            unfocusedContainerColor = Navy950.copy(alpha = 0.8f),
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = Navy700,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (riskType == RiskType.PERCENTAGE) "Calculated Dollar Exposure:" else "Effective Account Exposure:",
                            fontSize = 11.sp,
                            color = TextSlate400
                        )
                        Text(
                            text = if (riskType == RiskType.PERCENTAGE) {
                                currencyFormatter.format(calculatedExposure)
                            } else {
                                "${String.format(Locale.US, "%.2f", effectiveExposurePercent)}% of Balance"
                            },
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonEmerald
                        )
                    }
                }
            }

            // Field 5: Stop Loss Distance (Pips)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                val parsedSl = stopLossInput.toDoubleOrNull() ?: 0.0

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "STOP LOSS DISTANCE (PIPS)",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp,
                        color = TextSlate200
                    )

                    // Preset Chips
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf(10.0 to "10p", 15.0 to "15p", 25.0 to "25p", 50.0 to "50p").forEach { (sl, lbl) ->
                            PresetChip(
                                label = lbl,
                                isSelected = parsedSl == sl,
                                onClick = { onStopLossPreset(sl) }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = stopLossInput,
                    onValueChange = onStopLossChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("stop_loss_input"),
                    trailingIcon = {
                        Text(
                            text = "PIPS",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = TextSlate400
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            onRecalculate()
                        }
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Navy950,
                        unfocusedContainerColor = Navy950.copy(alpha = 0.8f),
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = Navy700,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite
                    )
                )

                Text(
                    text = "Accepts decimal stop loss values (e.g. 13.5 pips for scalp/day trades).",
                    fontSize = 11.sp,
                    color = TextSlate500
                )
            }

            // Recalculate Button
            Button(
                onClick = {
                    keyboardController?.hide()
                    onRecalculate()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("calculate_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(NeonCyan, NeonCyanDim, NeonEmerald)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Calculate,
                            contentDescription = null,
                            tint = Navy950,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "RECALCULATE LOT SIZE",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp,
                            color = Navy950
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PresetChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) NeonCyan else Navy800)
            .clickable(onClick = onClick)
            .padding(horizontal = 7.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            color = if (isSelected) Navy950 else TextSlate200
        )
    }
}

@Composable
private fun SegmentedOptionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Box(
        modifier = Modifier
            .testTag(testTag)
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) NeonCyan else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Navy950 else TextSlate400
        )
    }
}
