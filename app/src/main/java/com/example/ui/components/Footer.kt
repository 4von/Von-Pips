package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderNavy
import com.example.ui.theme.Navy900
import com.example.ui.theme.TextSlate400
import com.example.ui.theme.TextSlate500

@Composable
fun Footer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Navy900.copy(alpha = 0.5f))
            .border(width = 1.dp, color = BorderNavy.copy(alpha = 0.5f))
            .padding(vertical = 16.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "FXPULSE TERMINAL • CLIENT-SIDE RISK ENGINE",
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                color = TextSlate500
            )
            Text(
                text = "FORMULA: RISK ($) / (SL PIPS × PIP VALUE)",
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                color = TextSlate500
            )
            Text(
                text = "Precision: 2 Decimal Places (Lots)",
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                color = TextSlate400
            )
        }
    }
}
