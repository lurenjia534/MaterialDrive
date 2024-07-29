package com.lurenjia534.materialdrive.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// UserProfileViewModel.kt
class UserProfileViewModel(private val repository: DriveInfoRepository) : ViewModel() {
    private val _driveInfo = MutableLiveData<DriveInfo>()
    val driveInfo: LiveData<DriveInfo> get() = _driveInfo

    fun fetchDriveInfo(token: String, userId: String) {
        viewModelScope.launch {
            try {
                val info = repository.getDriveInfo(token, userId)
                _driveInfo.value = info
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

class UserProfileViewModelFactory(private val repository: DriveInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
