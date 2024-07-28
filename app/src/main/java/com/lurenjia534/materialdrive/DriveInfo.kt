package com.lurenjia534.materialdrive

import com.google.gson.annotations.SerializedName

data class DriveInfo(
    @SerializedName("@odata.context") val context: String,
    @SerializedName("createdDateTime") val createdDateTime: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: String,
    @SerializedName("lastModifiedDateTime") val lastModifiedDateTime: String,
    @SerializedName("name") val name: String,
    @SerializedName("webUrl") val webUrl: String,
    @SerializedName("driveType") val driveType: String,
    @SerializedName("createdBy") val createdBy: CreatedBy,
    @SerializedName("lastModifiedBy") val lastModifiedBy: ModifiedBy,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("quota") val quota: Quota
)

data class CreatedBy(val user: User)
data class ModifiedBy(val user: User)
data class Owner(val user: User)
data class User(val displayName: String, val email: String, val id: String)
data class Quota(val deleted: Long, val remaining: Long, val state: String, val total: Long, val used: Long)