package com.example.inventory.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(inventoryApplication().container.itemsRepository)
        }

        // Initializer for ItemEntryViewModel
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
        }
    }
}

fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
