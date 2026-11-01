package com.example.shalatku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PrayerTimesSection(
    state: PrayerTimesUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        Text("Prayer times", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        // when tanpa else: kalau ada state ke-4, build akan gagal
        when (state) {
            PrayerTimesUiState.Loading -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    CircularProgressIndicator(Modifier.size(32.dp))
                    Text("Memuat jadwal shalat...")
                }
            }

            is PrayerTimesUiState.Success -> {
                state.times.forEach { prayer -> PrayerCard(prayer) }
                Spacer(Modifier.height(8.dp))
                Button(onClick = onRefresh, modifier = Modifier.fillMaxWidth()) {
                    Text("Refresh")
                }
            }

            is PrayerTimesUiState.Error -> {
                Text(
                    state.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Button(onClick = onRefresh, modifier = Modifier.fillMaxWidth()) {
                    Text("Retry")
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Loading")
@Composable
fun PrayerTimesSectionLoadingPreview() {
    PrayerTimesSection(state = PrayerTimesUiState.Loading, onRefresh = {})
}

@Preview(showBackground = true, name = "Success")
@Composable
fun PrayerTimesSectionSuccessPreview() {
    PrayerTimesSection(state = PrayerTimesUiState.Success(samplePrayers), onRefresh = {})
}

@Preview(showBackground = true, name = "Error")
@Composable
fun PrayerTimesSectionErrorPreview() {
    PrayerTimesSection(
        state = PrayerTimesUiState.Error("Gagal memuat jadwal shalat. Coba lagi."),
        onRefresh = {}
    )
}