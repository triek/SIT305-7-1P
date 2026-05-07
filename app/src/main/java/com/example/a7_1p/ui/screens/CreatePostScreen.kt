package com.example.a7_1p.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val categories = listOf("Electronics", "Pets", "Wallets", "Keys", "Other")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen() {
    val context = LocalContext.current

    var type by rememberSaveable { mutableStateOf("Lost") }
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf(categories.first()) }
    var imageUri by rememberSaveable { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }

    var showErrors by rememberSaveable { mutableStateOf(false) }

    val isNameError = showErrors && name.isBlank()
    val isPhoneError = showErrors && phone.isBlank()
    val isDescriptionError = showErrors && description.isBlank()
    val isDateError = showErrors && date.isBlank()
    val isLocationError = showErrors && location.isBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Create Lost/Found Post", style = MaterialTheme.typography.headlineSmall)

        Text("Type", style = MaterialTheme.typography.titleMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = type == "Lost", onClick = { type = "Lost" })
            Text("Lost", modifier = Modifier.padding(end = 16.dp))
            RadioButton(selected = type == "Found", onClick = { type = "Found" })
            Text("Found")
        }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Item name*") },
            isError = isNameError,
            supportingText = {
                if (isNameError) Text("Name is required")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone number*") },
            isError = isPhoneError,
            supportingText = {
                if (isPhoneError) Text("Phone is required")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description*") },
            isError = isDescriptionError,
            supportingText = {
                if (isDescriptionError) Text("Description is required")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date*") },
            placeholder = { Text("e.g. 2026-05-07") },
            isError = isDateError,
            supportingText = {
                if (isDateError) Text("Date is required")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location*") },
            isError = isLocationError,
            supportingText = {
                if (isLocationError) Text("Location is required")
            },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = categoryExpanded,
            onExpandedChange = { categoryExpanded = !categoryExpanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false }
            ) {
                categories.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            category = option
                            categoryExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedButton(
            onClick = { imageUri = "selected-image-placeholder" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (imageUri.isBlank()) "Pick an image" else "Image selected")
        }

        Button(
            onClick = {
                showErrors = true
                val hasErrors = name.isBlank() || phone.isBlank() || description.isBlank() ||
                    date.isBlank() || location.isBlank()

                if (hasErrors) {
                    Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Post saved", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}
