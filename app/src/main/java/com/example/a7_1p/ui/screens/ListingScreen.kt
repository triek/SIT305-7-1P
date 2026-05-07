package com.example.a7_1p.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a7_1p.data.LostFoundDatabaseHelper

@Composable
fun ListingScreen(
    onCreatePostClick: () -> Unit,
    onItemClick: (Long) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val databaseHelper = remember { LostFoundDatabaseHelper(context) }
    val items = remember { mutableStateListOf<com.example.a7_1p.data.LostFoundItem>() }

    val refreshItems = {
        items.clear()
        items.addAll(databaseHelper.getAllItems())
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                refreshItems()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Lost & Found", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onCreatePostClick) {
            Text("Create a post")
        }

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp),
            factory = { ctx ->
                RecyclerView(ctx).apply {
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = LostFoundAdapter { item -> onItemClick(item.id) }
                }
            },
            update = { recyclerView ->
                (recyclerView.adapter as? LostFoundAdapter)?.submitList(items)
            }
        )
    }
}
