package com.lurenjia534.materialdrive.Profile

import com.lurenjia534.materialdrive.requset.ApiService

class DriveInfoRepository(
    private val apiService: ApiService
) {
    suspend fun getDriveInfo(token: String, userId: String): DriveInfo {
        return apiService.getDriveInfo(token, userId)
    }
}