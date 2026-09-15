package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.CalculationResult
import com.example.model.ForexPair
import com.example.model.RiskType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToLong

class CalculatorViewModel : ViewModel() {

    private val _balanceInput = MutableStateFlow("10000")
    val balanceInput: StateFlow<String> = _balanceInput.asStateFlow()

    private val _selectedPair = MutableStateFlow(ForexPair.ALL_PAIRS.first())
    val selectedPair: StateFlow<ForexPair> = _selectedPair.asStateFlow()

    private val _riskType = MutableStateFlow(RiskType.PERCENTAGE)
    val riskType: StateFlow<RiskType> = _riskType.asStateFlow()

    private val _riskInput = MutableStateFlow("1")
    val riskInput: StateFlow<String> = _riskInput.asStateFlow()

    private val _stopLossInput = MutableStateFlow("20")
    val stopLossInput: StateFlow<String> = _stopLossInput.asStateFlow()

    private val _result = MutableStateFlow(CalculationResult())
    val result: StateFlow<CalculationResult> = _result.asStateFlow()

    private val _validationErrors = MutableStateFlow<List<String>>(emptyList())
    val validationErrors: StateFlow<List<String>> = _validationErrors.asStateFlow()

    private val _copiedNotice = MutableStateFlow(false)
    val copiedNotice: StateFlow<Boolean> = _copiedNotice.asStateFlow()

    init {
        recalculate()
    }

    fun onBalanceChange(newBalance: String) {
        _balanceInput.value = newBalance
        recalculate()
    }

    fun setPresetBalance(amount: Double) {
        _balanceInput.value = if (amount % 1.0 == 0.0) amount.toInt().toString() else amount.toString()
        recalculate()
    }

    fun onPairSelected(pair: ForexPair) {
        _selectedPair.value = pair
        recalculate()
    }

    fun onRiskTypeChange(type: RiskType) {
        if (_riskType.value != type) {
            _riskType.value = type
            val balance = _balanceInput.value.toDoubleOrNull() ?: 10000.0
            if (type == RiskType.FIXED_CASH) {
                // Default 1% converted to cash
                val cashVal = (balance * 0.01).coerceAtLeast(1.0)
                _riskInput.value = if (cashVal % 1.0 == 0.0) cashVal.toInt().toString() else String.format(Locale.US, "%.1f", cashVal)
            } else {
                _riskInput.value = "1"
            }
            recalculate()
        }
    }

    fun onRiskInputChange(newRisk: String) {
        _riskInput.value = newRisk
        recalculate()
    }

    fun setPresetRisk(value: Double) {
        _riskInput.value = if (value % 1.0 == 0.0) value.toInt().toString() else value.toString()
        recalculate()
    }

    fun onStopLossChange(newSl: String) {
        _stopLossInput.value = newSl
        recalculate()
    }

    fun setPresetStopLoss(pips: Double) {
        _stopLossInput.value = if (pips % 1.0 == 0.0) pips.toInt().toString() else pips.toString()
        recalculate()
    }

    fun resetDefaults() {
        _balanceInput.value = "10000"
        _selectedPair.value = ForexPair.ALL_PAIRS.first()
        _riskType.value = RiskType.PERCENTAGE
        _riskInput.value = "1"
        _stopLossInput.value = "20"
        recalculate()
    }

    fun recalculate() {
        val balance = _balanceInput.value.toDoubleOrNull()
        val riskVal = _riskInput.value.toDoubleOrNull()
        val slPips = _stopLossInput.value.toDoubleOrNull()
        val pair = _selectedPair.value
        val pipVal = pair.pipValue

        val errors = mutableListOf<String>()

        if (balance == null || balance <= 0.0) {
            errors.add("Account Balance must be a positive number greater than $0.")
        }
        if (riskVal == null || riskVal <= 0.0) {
            errors.add(if (_riskType.value == RiskType.PERCENTAGE) "Risk percentage must be greater than 0%." else "Cash risk must be greater than $0.")
        }
        if (_riskType.value == RiskType.PERCENTAGE && riskVal != null && riskVal > 100.0) {
            errors.add("Risk percentage cannot exceed 100% of account balance.")
        }
        if (_riskType.value == RiskType.FIXED_CASH && balance != null && riskVal != null && riskVal > balance) {
            errors.add("Cash risk cannot exceed your total account balance.")
        }
        if (slPips == null || slPips <= 0.0) {
            errors.add("Stop Loss must be greater than 0 pips (decimal values like 13.2 are supported).")
        }

        _validationErrors.value = errors

        val timeFormat = SimpleDateFormat("h:mm:ss a", Locale.US)
        val currentTime = timeFormat.format(Date())

        if (errors.isNotEmpty() || balance == null || riskVal == null || slPips == null) {
            _result.value = CalculationResult(
                isValid = false,
                errorMessages = errors,
                timestamp = currentTime
            )
            return
        }

        val cashRisked: Double
        val effectivePercent: Double

        if (_riskType.value == RiskType.PERCENTAGE) {
            cashRisked = (balance * riskVal) / 100.0
            effectivePercent = riskVal
        } else {
            cashRisked = riskVal
            effectivePercent = if (balance > 0) (cashRisked / balance) * 100.0 else 0.0
        }

        val denominator = slPips * pipVal
        val rawLotSize = if (denominator > 0) cashRisked / denominator else 0.0
        // Round exact lot size to 2 decimal places per requirement
        val roundedLotSize = kotlin.math.round(rawLotSize * 100.0) / 100.0

        val contractUnits = pair.contractSize
        val positionUnits = (roundedLotSize * contractUnits).roundToLong()
        val lossPerPip = roundedLotSize * pipVal

        val classification = when {
            roundedLotSize <= 0.01 -> "Micro Position (≤ 0.01 Lots)"
            roundedLotSize < 1.0 -> "Mini / Scaled Volume (${String.format(Locale.US, "%.2f", roundedLotSize)} Lots)"
            roundedLotSize < 5.0 -> "Standard Volume (${String.format(Locale.US, "%.2f", roundedLotSize)} Lots)"
            else -> "Institutional Size (${String.format(Locale.US, "%.2f", roundedLotSize)} Lots)"
        }

        _result.value = CalculationResult(
            lotSize = roundedLotSize,
            cashRisked = cashRisked,
            effectiveRiskPercent = effectivePercent,
            positionUnits = positionUnits,
            pipValuePerLot = pipVal,
            slPips = slPips,
            lossPerPip = lossPerPip,
            standardLots = roundedLotSize,
            miniLots = kotlin.math.round(roundedLotSize * 10.0 * 100.0) / 100.0,
            microLots = kotlin.math.round(roundedLotSize * 100.0 * 100.0) / 100.0,
            classification = classification,
            isValid = true,
            errorMessages = emptyList(),
            timestamp = currentTime
        )
    }

    fun triggerCopyFeedback() {
        _copiedNotice.value = true
    }

    fun dismissCopyFeedback() {
        _copiedNotice.value = false
    }
}
