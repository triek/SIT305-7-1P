package com.example.a7_1p.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreatePostScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Create Lost/Found Post", style = MaterialTheme.typography.headlineSmall)
        Text("Planned fields:")
        Text("• Type (Lost/Found)")
        Text("• Item name")
        Text("• Phone")
        Text("• Description")
        Text("• Date/Time")
        Text("• Location")
        Text("• Category")
        Text("• Image")
    }
}
