package com.example.model

data class CalculationResult(
    val lotSize: Double = 0.0,
    val cashRisked: Double = 0.0,
    val effectiveRiskPercent: Double = 0.0,
    val positionUnits: Long = 0L,
    val pipValuePerLot: Double = 10.0,
    val slPips: Double = 20.0,
    val lossPerPip: Double = 0.0,
    val standardLots: Double = 0.0,
    val miniLots: Double = 0.0,
    val microLots: Double = 0.0,
    val classification: String = "",
    val isValid: Boolean = true,
    val errorMessages: List<String> = emptyList(),
    val timestamp: String = ""
)
