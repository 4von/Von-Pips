package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderNavy
import com.example.ui.theme.Navy850
import com.example.ui.theme.TextSlate200
import com.example.ui.theme.TextSlate400

@Composable
fun PropRuleCard(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Navy850.copy(alpha = 0.5f))
            .border(1.dp, BorderNavy.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
            .padding(14.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "🛡️",
                fontSize = 18.sp
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "Prop-Trader Risk Rule",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = TextSlate200
                )
                Text(
                    text = "Professional traders rarely risk >1%–2% per setup. Sticking to calculated sizes guarantees surviving a 10-trade losing streak with over 80% capital preserved.",
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = TextSlate400
                )
            }
        }
    }
}
