package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.ExecutionOutputCard
import com.example.ui.components.Footer
import com.example.ui.components.FormulaCard
import com.example.ui.components.LotTierCard
import com.example.ui.components.PipMatrixTable
import com.example.ui.components.PropRuleCard
import com.example.ui.components.TopBar
import com.example.ui.components.TradeParametersCard
import com.example.ui.theme.CyanBadgeBg
import com.example.ui.theme.EmeraldBadgeBg
import com.example.ui.theme.Navy950
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.TextSlate200
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextWhite
import com.example.viewmodel.CalculatorViewModel

@Composable
fun ForexCalculatorScreen(
    viewModel: CalculatorViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val balance by viewModel.balanceInput.collectAsState()
    val selectedPair by viewModel.selectedPair.collectAsState()
    val riskType by viewModel.riskType.collectAsState()
    val riskInput by viewModel.riskInput.collectAsState()
    val stopLossInput by viewModel.stopLossInput.collectAsState()
    val result by viewModel.result.collectAsState()
    val validationErrors by viewModel.validationErrors.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Navy950,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopBar(
                onReset = { viewModel.resetDefaults() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Navy950)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 1360.dp)
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    val isTabletWide = maxWidth >= 840.dp

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Title / Headline Section
                        HeadlineSection()

                        if (isTabletWide) {
                            // Two Column Grid Layout (Matches Web / Desktop terminal)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(24.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                // Left Column (7 parts)
                                Column(
                                    modifier = Modifier.weight(7f),
                                    verticalArrangement = Arrangement.spacedBy(20.dp)
                                ) {
                                    TradeParametersCard(
                                        balance = balance,
                                        onBalanceChange = { viewModel.onBalanceChange(it) },
                                        onBalancePreset = { viewModel.setPresetBalance(it) },
                                        selectedPair = selectedPair,
                                        onPairSelected = { viewModel.onPairSelected(it) },
                                        riskType = riskType,
                                        onRiskTypeChange = { viewModel.onRiskTypeChange(it) },
                                        riskInput = riskInput,
                                        onRiskInputChange = { viewModel.onRiskInputChange(it) },
                                        onRiskPreset = { viewModel.setPresetRisk(it) },
                                        stopLossInput = stopLossInput,
                                        onStopLossChange = { viewModel.onStopLossChange(it) },
                                        onStopLossPreset = { viewModel.setPresetStopLoss(it) },
                                        validationErrors = validationErrors,
                                        onRecalculate = { viewModel.recalculate() }
                                    )

                                    FormulaCard()
                                }

                                // Right Column (5 parts)
                                Column(
                                    modifier = Modifier.weight(5f),
                                    verticalArrangement = Arrangement.spacedBy(20.dp)
                                ) {
                                    ExecutionOutputCard(
                                        result = result,
                                        selectedPair = selectedPair
                                    )

                                    LotTierCard(result = result)

                                    PropRuleCard()
                                }
                            }
                        } else {
                            // Mobile Compact Flow (Linear & Clean)
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                TradeParametersCard(
                                    balance = balance,
                                    onBalanceChange = { viewModel.onBalanceChange(it) },
                                    onBalancePreset = { viewModel.setPresetBalance(it) },
                                    selectedPair = selectedPair,
                                    onPairSelected = { viewModel.onPairSelected(it) },
                                    riskType = riskType,
                                    onRiskTypeChange = { viewModel.onRiskTypeChange(it) },
                                    riskInput = riskInput,
                                    onRiskInputChange = { viewModel.onRiskInputChange(it) },
                                    onRiskPreset = { viewModel.setPresetRisk(it) },
                                    stopLossInput = stopLossInput,
                                    onStopLossChange = { viewModel.onStopLossChange(it) },
                                    onStopLossPreset = { viewModel.setPresetStopLoss(it) },
                                    validationErrors = validationErrors,
                                    onRecalculate = { viewModel.recalculate() }
                                )

                                ExecutionOutputCard(
                                    result = result,
                                    selectedPair = selectedPair
                                )

                                LotTierCard(result = result)

                                FormulaCard()

                                PropRuleCard()
                            }
                        }

                        // Institutional Pip Matrix Table (Full Width)
                        PipMatrixTable(
                            selectedPair = selectedPair,
                            onSelectPair = { viewModel.onPairSelected(it) }
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                // Bottom Footer
                Footer()
            }
        }
    }
}

@Composable
private fun HeadlineSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Engine Badge
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(CyanBadgeBg)
                    .border(1.dp, NeonCyan.copy(alpha = 0.35f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Bolt,
                    contentDescription = null,
                    tint = NeonCyan,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "PRECISION RISK ENGINE",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp,
                    color = NeonCyan
                )
            }

            // Calc Mode Status
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(EmeraldBadgeBg)
                    .border(1.dp, NeonEmerald.copy(alpha = 0.35f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(NeonEmerald)
                )
                Text(
                    text = "LIVE AUTO-UPDATE",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = NeonEmerald
                )
            }
        }

        Text(
            text = "Forex Position & Lot Size Calculator",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            letterSpacing = (-0.5).sp,
            color = TextWhite
        )

        Text(
            text = "Protect your capital by enforcing mathematical position sizing before executing trades across FX majors, crosses, and commodities.",
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = TextSlate400
        )
    }
}
