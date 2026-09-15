package com.example.model

data class ForexPair(
    val symbol: String,
    val name: String,
    val pipValue: Double,
    val pipSize: Double,
    val category: String,
    val note: String,
    val contractSize: Long = 100_000L
) {
    companion object {
        val ALL_PAIRS = listOf(
            ForexPair(
                symbol = "EUR/USD",
                name = "Euro / US Dollar",
                pipValue = 10.00,
                pipSize = 0.0001,
                category = "Major (Direct)",
                note = "Standard 100,000 units base • $10.00/pip"
            ),
            ForexPair(
                symbol = "GBP/USD",
                name = "British Pound / US Dollar",
                pipValue = 10.00,
                pipSize = 0.0001,
                category = "Major (Direct)",
                note = "Standard 100,000 units base • $10.00/pip"
            ),
            ForexPair(
                symbol = "USD/JPY",
                name = "US Dollar / Japanese Yen",
                pipValue = 6.47,
                pipSize = 0.01,
                category = "Major (Cross Quote)",
                note = "1 pip = 0.01 JPY • Normalized ~$6.47/pip"
            ),
            ForexPair(
                symbol = "USD/CAD",
                name = "US Dollar / Canadian Dollar",
                pipValue = 7.30,
                pipSize = 0.0001,
                category = "Major (Cross Quote)",
                note = "1 pip = 0.0001 CAD • Normalized ~$7.30/pip"
            ),
            ForexPair(
                symbol = "USD/CHF",
                name = "US Dollar / Swiss Franc",
                pipValue = 11.10,
                pipSize = 0.0001,
                category = "Major (Cross Quote)",
                note = "1 pip = 0.0001 CHF • Normalized ~$11.10/pip"
            ),
            ForexPair(
                symbol = "AUD/CAD",
                name = "Aussie Dollar / Canadian Dollar",
                pipValue = 7.30,
                pipSize = 0.0001,
                category = "Minor Cross",
                note = "Standard 100k units • ~$7.30/pip"
            ),
            ForexPair(
                symbol = "EUR/AUD",
                name = "Euro / Australian Dollar",
                pipValue = 6.60,
                pipSize = 0.0001,
                category = "Minor Cross",
                note = "Standard 100k units • ~$6.60/pip"
            ),
            ForexPair(
                symbol = "GBP/CHF",
                name = "British Pound / Swiss Franc",
                pipValue = 11.10,
                pipSize = 0.0001,
                category = "Minor Cross",
                note = "Standard 100k units • ~$11.10/pip"
            ),
            ForexPair(
                symbol = "XAU/USD",
                name = "Gold / US Dollar",
                pipValue = 10.00,
                pipSize = 0.10,
                category = "Precious Metal",
                note = "100 oz contract • $10.00/pip (0.10 tick)",
                contractSize = 100L
            )
        )
    }
}

enum class RiskType {
    PERCENTAGE,
    FIXED_CASH
}
