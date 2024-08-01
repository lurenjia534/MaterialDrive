package com.lurenjia534.materialdrive.ux

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenItem(val route: String, val title: String, val icon: ImageVector) {
    object TokenRequest : ScreenItem("TokenRequest", "Login", Icons.Default.Person)
    object Home : ScreenItem("home/{token}/{userId}", "Home", Icons.Default.Home)
    object UserProfile : ScreenItem("userProfile/{token}/{userId}", "Profile", Icons.Default.Person)
}
