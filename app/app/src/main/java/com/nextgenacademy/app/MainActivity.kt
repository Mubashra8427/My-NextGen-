package com.nextgenacademy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextgenacademy.app.ui.theme.NextGenAcademyTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NextGenAcademyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("splash") }

    when (currentScreen) {
        "splash" -> SplashScreen { currentScreen = "welcome" }
        "welcome" -> WelcomeScreen(
            onLoginClick = { currentScreen = "login" },
            onRegisterClick = { currentScreen = "register" }
        )
        "login" -> LoginScreen(
            onLoginSuccess = { currentScreen = "home" },
            onBackToWelcome = { currentScreen = "welcome" }
        )
        "register" -> RegisterScreen(
            onRegisterSuccess = { currentScreen = "home" },
            onBackToLogin = { currentScreen = "login" }
        )
        "home" -> HomeDashboardScreen(
            onLogout = { currentScreen = "welcome" }
        )
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(true) {
        delay(2000)
        onTimeout()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "NextGen Academy",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Learn. Grow. Succeed.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun WelcomeScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to NextGen Academy",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Quality Online Tuition for Classes 6–10[span_3](start_span)[span_3](end_span)",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onLoginClick, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = onRegisterClick, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Create Account")
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onBackToWelcome: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Student Login", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Student Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLoginSuccess, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Login")
        }
        TextButton(onClick = onBackToWelcome) {
            Text(text = "Back to Welcome")
        }
    }
}

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onBackToLogin: () -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Student Registration", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Student Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = mobile,
            onValueChange = { mobile = it },
            label = { Text("Mobile Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRegisterSuccess, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Register")
        }
        TextButton(onClick = onBackToLogin) {
            Text(text = "Already have an account? Login")
        }
    }
}

@Composable
fun HomeDashboardScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Good Morning, Student[span_4](start_span)[span_4](end_span)", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Button(onClick = onLogout) {
                Text(text = "Logout")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Today's Class: Mathematics", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Time: 10:00 AM - Live Interactive Class[span_5](start_span)[span_5](end_span)")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Subjects[span_6](start_span)[span_6](end_span)", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { }) { Text("Mathematics[span_7](start_span)[span_7](end_span)") }
            Button(onClick = { }) { Text("Science[span_8](start_span)[span_8](end_span)") }
            Button(onClick = { }) { Text("Social Science[span_9](start_span)[span_9](end_span)") }
        }
    }
}
