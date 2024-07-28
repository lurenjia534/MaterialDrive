package com.lurenjia534.materialdrive

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lurenjia534.materialdrive.savedata.PreferencesManager
import kotlinx.coroutines.launch
import retrofit2.Response

class TokenRequestViewModel(application: Application) : AndroidViewModel(application) {
    private val preferencesManager = PreferencesManager(application)
    var clientId = mutableStateOf(preferencesManager.clientId)
    var clientSecret = mutableStateOf(preferencesManager.clientSecret)
    var tenantId = mutableStateOf(preferencesManager.tenantId)
    var userId = mutableStateOf(preferencesManager.userId)
    var scope = mutableStateOf("https://graph.microsoft.com/.default offline_access")
    var token = mutableStateOf("")
    var driveInfo = mutableStateOf<DriveInfo?>(null)

    fun updateClientId(newClientId: String) {
        clientId.value = newClientId
        preferencesManager.clientId = newClientId
    }

    fun updateClientSecret(newClientSecret: String) {
        clientSecret.value = newClientSecret
        preferencesManager.clientSecret = newClientSecret
    }

    fun updateTenantId(newTenantId: String) {
        tenantId.value = newTenantId
        preferencesManager.tenantId = newTenantId
    }

    fun updateUserId(newUserId: String) {
        userId.value = newUserId
        preferencesManager.userId = newUserId
    }

    fun updateScope(newScope: String) {
        scope.value = newScope
    }

    fun fetchToken(onTokenReceived: (String) -> Unit) {
        viewModelScope.launch {
            val response = fetchTokenFromServer()
            if (response.isSuccessful) {
                response.body()?.let {
                    token.value = it.accessToken
                    onTokenReceived(it.accessToken)
                }
            }
        }
    }

    private suspend fun fetchTokenFromServer(): Response<TokenResponse> {
        val apiService = ApiClient.create()
        val requestBody = mapOf(
            "client_id" to clientId.value,
            "client_secret" to clientSecret.value,
            "scope" to scope.value,
            "grant_type" to "client_credentials"
        )
        return apiService.getToken(tenantId.value, requestBody)
    }

    fun fetchDriveInfo(userId: String) {
        viewModelScope.launch {
            val response = fetchDriveInfoFromServer(userId)
            if (response.isSuccessful) {
                driveInfo.value = response.body()
            }
        }
    }

    private suspend fun fetchDriveInfoFromServer(userId: String): Response<DriveInfo> {
        val apiService = ApiClient.create()
        return apiService.getDriveInfo("Bearer ${token.value}", userId)
    }
}
