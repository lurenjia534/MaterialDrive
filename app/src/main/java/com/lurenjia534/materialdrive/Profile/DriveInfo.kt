package com.lurenjia534.materialdrive.Profile

// DriveInfo.kt
data class DriveInfo(
    val createdDateTime: String,
    val description: String?,
    val id: String,
    val lastModifiedDateTime: String,
    val name: String,
    val webUrl: String,
    val driveType: String,
    val createdBy: UserInfo,
    val lastModifiedBy: UserInfo,
    val owner: UserInfo,
    val quota: QuotaInfo
)

data class UserInfo(
    val user: User
)

data class User(
    val displayName: String,
    val email: String?,
    val id: String
)

data class QuotaInfo(
    val deleted: Long,
    val remaining: Long,
    val state: String,
    val total: Long,
    val used: Long
)
