package com.nextgenacademy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
            onLoginSuccess = { currentScreen = "main_app" },
            onBackToWelcome = { currentScreen = "welcome" }
        )
        "register" -> RegisterScreen(
            onRegisterSuccess = { currentScreen = "main_app" },
            onBackToLogin = { currentScreen = "login" }
        )
        "main_app" -> MainScreenWithBottomNav(
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
            text = "Quality Online Tuition for Classes 6–10",
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
        OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Student Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("Mobile Number") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRegisterSuccess, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Register")
        }
        TextButton(onClick = onBackToLogin) {
            Text(text = "Already have an account? Login")
        }
    }
}

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavScreen("home", "Home", Icons.Default.Home)
    object Classes : BottomNavScreen("classes", "Classes", Icons.Default.School)
    object Live : BottomNavScreen("live", "Live", Icons.Default.LiveTv)
    object Tests : BottomNavScreen("tests", "Tests", Icons.Default.Assignment)
    object Profile : BottomNavScreen("profile", "Profile", Icons.Default.Person)
}

@Composable
fun MainScreenWithBottomNav(onLogout: () -> Unit) {
    var selectedTab by remember { mutableStateOf<String>("home") }
    val items = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Classes,
        BottomNavScreen.Live,
        BottomNavScreen.Tests,
        BottomNavScreen.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = selectedTab == screen.route,
                        onClick = { selectedTab = screen.route }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "home" -> HomeScreen()
                "classes" -> ClassesScreenFlow()
                "live" -> LiveScreen()
                "tests" -> TestsScreen()
                "profile" -> ProfileScreen(onLogout)
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Good Morning, Student!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Today's Live Class", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Subject: Mathematics (Class 10)")
                Text(text = "Time: 10:00 AM")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { }) {
                    Text("Join Class")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Quick Summary", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Card(modifier = Modifier.weight(1f).padding(4.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Tests Pending", fontWeight = FontWeight.Bold)
                    Text("1 Test")
                }
            }
            Card(modifier = Modifier.weight(1f).padding(4.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Assignments", fontWeight = FontWeight.Bold)
                    Text("2 Due")
                }
            }
        }
    }
}

@Composable
fun ClassesScreenFlow() {
    var selectedClass by remember { mutableStateOf<String?>(null) }
    var selectedSubject by remember { mutableStateOf<String?>(null) }

    if (selectedSubject != null) {
        // Subject Detail & Notes View
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { selectedSubject = null }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(text = "$selectedSubject Materials", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Chapter 1: Important Notes", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("PDF Study Material available for download.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {}) { Text("Download PDF Notes") }
                }
            }
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Practice Assignment", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Due Date: Friday | Status: Pending")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {}) { Text("Submit Assignment") }
                }
            }
        }
    } else if (selectedClass != null) {
        // Subjects List View for selected class
        val subjects = listOf("Mathematics", "Science", "Social Science")
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { selectedClass = null }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(text = "$selectedClass Subjects", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(subjects) { subject ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { selectedSubject = subject }
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = subject, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                            Icon(Icons.Default.ChevronRight, contentDescription = "Open")
                        }
                    }
                }
            }
        }
    } else {
        // Classes List View (6 to 10)
        val classes = listOf("Class 6", "Class 7", "Class 8", "Class 9", "Class 10")
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Select Your Class", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(classes) { cls ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { selectedClass = cls }
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = cls, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Icon(Icons.Default.ChevronRight, contentDescription = "Open")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LiveScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Live & Recorded Classes", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Science - Chemical Reactions", fontWeight = FontWeight.Bold)
                Text(text = "Teacher: Expert Faculty")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { }) {
                    Text("Watch Recorded Lecture")
                }
            }
        }
    }
}

@Composable
fun TestsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Tests & Quizzes", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Mathematics Weekly MCQ Test", fontWeight = FontWeight.Bold)
                Text(text = "Duration: 30 Mins | Questions: 20")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { }) {
                    Text("Start Test")
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Student Profile", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Name: Student Name", fontWeight = FontWeight.Bold)
                Text(text = "Class: 10th")
                Text(text = "Email: student@nextgenacademy.com")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}
