package com.lurenjia534.materialdrive.requset

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

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
}