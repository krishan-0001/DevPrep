package com.krish.devprep.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync

fun groupIcon(title: String) = when(title){
    "Core Android" -> Icons.Default.Android
    "Architecture" -> Icons.Default.AccountTree
    "Data & APIs" -> Icons.Default.Storage
    "Async" -> Icons.Default.Sync
    "Language" -> Icons.Default.Code
    else -> Icons.Default.Folder

}