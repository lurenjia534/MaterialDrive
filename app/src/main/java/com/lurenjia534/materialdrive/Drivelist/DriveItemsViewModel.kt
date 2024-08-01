package com.lurenjia534.materialdrive.Drivelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lurenjia534.materialdrive.Profile.DriveInfoRepository
import kotlinx.coroutines.launch

class DriveItemsViewModel(private val repository: DriveInfoRepository) : ViewModel() {
    private val _driveItems = MutableLiveData<List<DriveItem>>()
    val driveItems: LiveData<List<DriveItem>> get() = _driveItems

    fun fetchDriveItems(token: String, userId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getDriveItems(token, userId)
                _driveItems.value = response.value
            } catch (e: Exception) {
                // Handle error, possibly log it or show a message to the user
            }
        }
    }
}

class DriveItemsViewModelFactory(private val repository: DriveInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DriveItemsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DriveItemsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
