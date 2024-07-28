package com.lurenjia534.materialdrive.requset

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// TokenRequestViewModel.kt
class TokenRequestViewModel : ViewModel() {
    private val apiService = ApiClient.create()
    private val _tokenResponse = MutableLiveData<TokenResponse>()
    val tokenResponse: LiveData<TokenResponse> get() = _tokenResponse

    fun requestToken(clientId: String, clientSecret: String, tenantId: String) {
        val url = "https://login.microsoftonline.com/$tenantId/oauth2/v2.0/token"
        viewModelScope.launch {
            try {
                val response = apiService.getToken(
                    url = url,
                    clientId = clientId,
                    scope = "https://graph.microsoft.com/.default offline_access",
                    clientSecret = clientSecret,
                    grantType = "client_credentials"
                )
                _tokenResponse.value = response
            } catch (e: Exception) {
                // 处理异常
                Log.e("TokenRequestViewModel", "Error fetching token", e)
            }
        }
    }

    fun getTokenResponse(): TokenResponse? {
        return _tokenResponse.value
    }
}
