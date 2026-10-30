package com.example.shalatku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TasbihScreen(
    count: Int,
    rounds: Int,
    roundSize: Int,
    roundSizeOptions: List<Int>,
    onSelectRoundSize: (Int) -> Unit,
    onIncrement: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Tasbih", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        // Pilihan target: screen hanya menampilkan dan melapor tap
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            roundSizeOptions.forEach { size ->
                FilterChip(
                    selected = size == roundSize,
                    onClick = { onSelectRoundSize(size) },
                    label = { Text("$size") }
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("$count / $roundSize", style = MaterialTheme.typography.displayLarge)
        Text("Rounds: $rounds", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onIncrement,
            shape = CircleShape,
            modifier = Modifier.size(160.dp)
        ) {
            Text("Tap", style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(Modifier.height(16.dp))
        OutlinedButton(onClick = onReset) {
            Text("Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasbihScreenPreview() {
    TasbihScreen(
        count = 12,
        rounds = 2,
        roundSize = 33,
        roundSizeOptions = listOf(33, 99, 100),
        onSelectRoundSize = {},
        onIncrement = {},
        onReset = {}
    )
}