package com.lurenjia534.materialdrive

import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("https://login.microsoftonline.com/{tenantId}/oauth2/v2.0/token")
    suspend fun getToken(
        @Path("tenantId") tenantId: String,
        @FieldMap requestBody: Map<String, String>
    ):Response<TokenResponse>

    @GET("https://graph.microsoft.com/v1.0/users/{user_id}/drive")
    suspend fun getDriveInfo(
        @Header("Authorization") authHeader: String,
        @Path("user_id") userId: String
    ): Response<DriveInfo>
}