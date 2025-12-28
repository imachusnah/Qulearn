package pratheekv39.bridgelearn.io.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import pratheekv39.bridgelearn.io.ui.screens.topic.TopicDetailScreen
import kotlinx.coroutines.launch

// --- IMPORTS DARI FOLDER DATA & VIEWMODEL ---
import pratheekv39.bridgelearn.io.data.UserPreferences
import pratheekv39.bridgelearn.io.theme.ThemeViewModel

// --- IMPORTS DARI FOLDER SCREENS ---
import pratheekv39.bridgelearn.io.ui.screens.auth.LoginScreen
import pratheekv39.bridgelearn.io.ui.screens.auth.RegisterScreen
import pratheekv39.bridgelearn.io.ui.screens.home.HomeScreen
import pratheekv39.bridgelearn.io.ui.screens.home.SubjectScreen
import pratheekv39.bridgelearn.io.ui.screens.learn.LearnScreen
import pratheekv39.bridgelearn.io.ui.screens.profile.ProfileScreen
import pratheekv39.bridgelearn.io.ui.screens.profile.HistoryScreen
import pratheekv39.bridgelearn.io.ui.screens.quiz.QuizScreen
import pratheekv39.bridgelearn.io.ui.screens.quiz.QuizListScreen
import pratheekv39.bridgelearn.io.ui.screens.quiz.QuizDetailScreen

// --- IMPORTS DARI FOLDER READINGS ---
import pratheekv39.bridgelearn.io.ui.readings.EnergyConservationScreen
import pratheekv39.bridgelearn.io.ui.readings.PeriodicTableScreen

// --- IMPORTS DARI FOLDER SIMULATIONS ---
import pratheekv39.bridgelearn.io.ui.simulations.AcidBaseInteractiveLab
import pratheekv39.bridgelearn.io.ui.simulations.PendulumScreen
import pratheekv39.bridgelearn.io.ui.simulations.SpringScreen
import pratheekv39.bridgelearn.io.ui.simulations.NewtonsLawsScreen
import pratheekv39.bridgelearn.io.ui.simulations.ChemicalBonding

// ===================== NAVIGATION SCREENS =====================
sealed class Screen(
    val route: String,
    val title: String,
    val icon: @Composable () -> Unit
) {
    data object Home : Screen("home", "Home", { Icon(Icons.Filled.Home, "Home") })
    data object Quiz : Screen("quiz", "Quiz", { Icon(Icons.Filled.Quiz, "Quiz") })
    data object Learn : Screen("learn", "Learn", { Icon(Icons.Filled.School, "Learn") })
    data object Profile : Screen("profile", "Profile", { Icon(Icons.Filled.Person, "Profile") })
    data object Subject : Screen("subject/{subjectId}", "Subject", { Icon(Icons.Filled.Description, "Subject") }) {
        fun createRoute(subjectId: String) = "subject/$subjectId"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    userPreferences: UserPreferences,
    themeViewModel: ThemeViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val isLoggedIn by userPreferences.isLoggedIn.collectAsState(initial = false)
    val darkMode by themeViewModel.darkMode.collectAsState()
    val scope = rememberCoroutineScope()

    val screens = listOf(Screen.Home, Screen.Quiz, Screen.Learn, Screen.Profile)

    val startDestination = if (isLoggedIn) "home" else "register"

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            if (currentRoute !in listOf(
                    "login", "register", "Interactive", "Pendulum", "Spring",
                    "PeriodicTable", "EnergyConservation", "NewtonLaws", "ChemicalBonding"
                )
            ) {
                NavigationBar(
                    containerColor = if (darkMode) Color(0xFF6C4F74) else Color(0xFF433549),
                    contentColor = Color.White
                ) {
                    screens.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = when (screen) {
                                        is Screen.Home -> Icons.Filled.Home
                                        is Screen.Quiz -> Icons.Filled.Quiz
                                        is Screen.Learn -> Icons.Filled.School
                                        is Screen.Profile -> Icons.Filled.Person
                                        else -> Icons.Filled.Description
                                    },
                                    contentDescription = screen.title,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .padding(4.dp),
                                    tint = if (currentRoute == screen.route)
                                        Color.White
                                    else
                                        Color.White.copy(alpha = 0.6f)
                                )
                            },
                            selected = currentRoute == screen.route,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = if (currentRoute == screen.route)
                                    if (darkMode) Color(0xFF694D72) else Color(0xFF392D41)
                                else
                                    Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(innerPadding)
        ) {

            // ===================== 🔐 AUTH =====================
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        scope.launch { userPreferences.setLoggedIn(true) }
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate("register") }
                )
            }

            composable("register") {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    },
                    onNavigateToLogin = { navController.navigate("login") }
                )
            }

            // ===================== 🏠 MAIN =====================
            composable(Screen.Home.route) {
                HomeScreen(onSubjectClick = { subjectId ->
                    navController.navigate(Screen.Subject.createRoute(subjectId))
                })
            }

            composable(Screen.Quiz.route) { QuizScreen(navController) }
            composable(Screen.Learn.route) { LearnScreen(navController) }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController = navController,
                    darkMode = darkMode,
                    onDarkModeChanged = { isChecked -> themeViewModel.setDarkMode(isChecked) },
                    onLogout = {
                        scope.launch { userPreferences.setLoggedIn(false) }
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }

            composable("history") { HistoryScreen(navController = navController) }

            // ===================== 📘 SUBJECTS =====================
            composable(
                route = Screen.Subject.route,
                arguments = listOf(navArgument("subjectId") { type = NavType.StringType })
            ) { backStackEntry ->
                val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
                SubjectScreen(
                    navController = navController,
                    subjectId = subjectId,
                    onNavigateBack = { navController.navigateUp() },
                    onTopicClick = { topicId ->
                        navController.navigate("topic_detail/$topicId")
                    }
                )
            }

            composable(
                "topic_detail/{topicId}",
                arguments = listOf(navArgument("topicId") { type = NavType.IntType })
            ) { backStackEntry ->
                val topicId = backStackEntry.arguments?.getInt("topicId") ?: 0
                TopicDetailScreen(navController, topicId)
            }

            // ===================== 🧪 SIMULATIONS =====================
            composable("Interactive") { AcidBaseInteractiveLab(navController) }
            composable("Pendulum") { PendulumScreen(navController) }
            composable("Spring") { SpringScreen(navController) }

            composable("PeriodicTable") {
                val context = LocalContext.current
                PeriodicTableScreen(navController, context)
            }

            composable("EnergyConservation") { EnergyConservationScreen(navController) }
            composable("NewtonLaws") { NewtonsLawsScreen(navController) }
            composable("ChemicalBonding") { ChemicalBonding(navController) }

            // ===================== 🧩 QUIZ =====================
            composable(
                "quiz_list/{subjectId}",
                arguments = listOf(navArgument("subjectId") { type = NavType.StringType })
            ) { backStackEntry ->
                val subjectId = backStackEntry.arguments?.getString("subjectId")
                if (subjectId != null) {
                    QuizListScreen(
                        navController = navController,
                        subjectId = subjectId,
                        onNavigateBack = { navController.navigateUp() },
                        onQuizClick = { quizId ->
                            navController.navigate("quiz_detail/$subjectId/$quizId")
                        }
                    )
                }
            }

            composable(
                "quiz_detail/{subjectId}/{quizId}",
                arguments = listOf(
                    navArgument("subjectId") { type = NavType.StringType },
                    navArgument("quizId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
                val quizId = backStackEntry.arguments?.getInt("quizId") ?: 0
                QuizDetailScreen(
                    navController,
                    subjectId,
                    quizId,
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
    }
}
