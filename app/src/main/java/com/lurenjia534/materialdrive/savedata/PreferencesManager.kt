package com.lurenjia534.materialdrive.savedata

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MaterialDrivePrefs", Context.MODE_PRIVATE)

    var clientId: String
        get() = sharedPreferences.getString("CLIENT_ID", "") ?: ""
        set(value) = sharedPreferences.edit().putString("CLIENT_ID", value).apply()

    var clientSecret: String
        get() = sharedPreferences.getString("CLIENT_SECRET", "") ?: ""
        set(value) = sharedPreferences.edit().putString("CLIENT_SECRET", value).apply()

    var tenantId: String
        get() = sharedPreferences.getString("TENANT_ID", "") ?: ""
        set(value) = sharedPreferences.edit().putString("TENANT_ID", value).apply()

    var userId: String
        get() = sharedPreferences.getString("USER_ID", "") ?: ""
        set(value) = sharedPreferences.edit().putString("USER_ID", value).apply()
}
