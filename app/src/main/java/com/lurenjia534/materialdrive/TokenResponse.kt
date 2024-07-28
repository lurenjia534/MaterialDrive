package com.lurenjia534.materialdrive

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName("ext_expires_in") val extExpiresIn: Int,
    @SerializedName("access_token") val accessToken: String
)