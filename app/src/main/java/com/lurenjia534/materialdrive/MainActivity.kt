package com.lurenjia534.materialdrive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lurenjia534.materialdrive.ui.theme.MaterialDriveTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lurenjia534.materialdrive.Profile.DriveInfoRepository
import com.lurenjia534.materialdrive.Profile.UserProfileViewModel
import com.lurenjia534.materialdrive.Profile.UserProfileViewModelFactory
import com.lurenjia534.materialdrive.requset.ApiClient
import com.lurenjia534.materialdrive.requset.TokenRequestViewModel
import com.lurenjia534.materialdrive.ux.ScreenItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialDriveTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   AppNavHost(navController = navController,innerPadding = innerPadding)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TokenRequestScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    viewModel: TokenRequestViewModel = viewModel()
) {
    val tokenResponse by viewModel.tokenResponse.observeAsState()

    var clientId by remember { mutableStateOf("") }
    var clientSecret by remember { mutableStateOf("") }
    var tenantId by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation icon click */ }) {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = "Information",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(
                        text = "Material Drive Login",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.elevatedCardElevation(1.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        LoginOutlinedTextField(
                            value = clientId,
                            onValueChange = { clientId = it },
                            label = "Client ID"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LoginOutlinedTextField(
                            value = clientSecret,
                            onValueChange = { clientSecret = it },
                            label = "Client Secret"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LoginOutlinedTextField(
                            value = tenantId,
                            onValueChange = { tenantId = it },
                            label = "Tenant ID"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LoginOutlinedTextField(
                            value = userId,
                            onValueChange = { userId = it },
                            label = "User ID"
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                viewModel.requestToken(clientId, clientSecret, tenantId)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Login", color = MaterialTheme.colorScheme.onPrimary)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        tokenResponse?.let {
                            if (it.access_token.isNotEmpty()) {
                                navController.navigate("userProfile/${it.access_token}/$userId")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, innerPadding: PaddingValues) {
    val items = listOf(
        ScreenItem.Home,
        ScreenItem.UserProfile
    )
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "TokenRequest", modifier = Modifier.padding(innerPadding)) {
            composable("TokenRequest") {
                TokenRequestScreen(
                    navController = navController,
                    innerPadding = innerPadding
                )
            }
            composable(ScreenItem.Home.route) {
                HomeScreen()
            }
            composable("userProfile/{token}/{userId}") { backStackEntry ->
                val token = backStackEntry.arguments?.getString("token") ?: ""
                val userId = backStackEntry.arguments?.getString("userId") ?: ""
                UserProfileScreen(navController = navController, token = token, userId = userId)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    Text(text = "114514")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(navController: NavController, token: String, userId: String) {
    val apiService = ApiClient.createGraphService()
    val repository = DriveInfoRepository(apiService)
    val viewModel: UserProfileViewModel = viewModel(factory = UserProfileViewModelFactory(repository))
    val driveInfo by viewModel.driveInfo.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchDriveInfo(token, userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Profile") }
            )
        }
    ) { paddingValues ->
        if (driveInfo != null) {
            val info = driveInfo!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Name: ${info.owner.user.displayName}", style = MaterialTheme.typography.bodyMedium)
                Text("Email: ${info.owner.user.email}", style = MaterialTheme.typography.bodyMedium)
                Text("Drive Type: ${info.driveType}", style = MaterialTheme.typography.bodyMedium)
                Text("Total Space: ${info.quota.total}", style = MaterialTheme.typography.bodyMedium)
                Text("Used Space: ${info.quota.used}", style = MaterialTheme.typography.bodyMedium)
                Text("Remaining Space: ${info.quota.remaining}", style = MaterialTheme.typography.bodyMedium)
                // Add more fields as needed
            }
        } else {
            CircularProgressIndicator()
        }
    }
}

