package com.lurenjia534.materialdrive.requset

import com.lurenjia534.materialdrive.Profile.DriveInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url
// ApiService.kt
interface ApiService {
    @FormUrlEncoded
    @POST
    suspend fun getToken(
        @Url url: String,
        @Field("client_id") clientId: String,
        @Field("scope") scope: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String
    ): TokenResponse

    @GET("v1.0/users/{userId}/drive")
    suspend fun getDriveInfo(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): DriveInfo
}