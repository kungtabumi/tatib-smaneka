package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.adaptive.AdaptiveScreenContainer
import com.example.ui.adaptive.LocalWindowSizeInfo
import com.example.ui.adaptive.ProvideAdaptiveLayout
import com.example.ui.components.AppBottomBar
import com.example.ui.components.AppTopBar
import com.example.ui.navigation.Screen
import com.example.ui.screens.about.AboutScreen
import com.example.ui.screens.ai.TanyaTatibScreen
import com.example.ui.screens.bookmarks.BookmarksScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.learning.LearningScreen
import com.example.ui.screens.learning.QuizScreen
import com.example.ui.screens.pedoman.PedomanScreen
import com.example.ui.screens.pelanggaran.PelanggaranScreen
import com.example.ui.screens.resources.EducationalResourcesScreen
import com.example.ui.screens.search.SmartSearchScreen
import com.example.ui.screens.standards.VisualStandardsScreen
import com.example.ui.theme.GradientAppBackground
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.TatibViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: TatibViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                TatibApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun TatibApp(viewModel: TatibViewModel) {
    ProvideAdaptiveLayout {
        val windowInfo = LocalWindowSizeInfo.current
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

        val rootTabs = listOf(
            Screen.Home.route,
            Screen.Pedoman.route,
            Screen.Pelanggaran.route,
            Screen.TanyaTatib.route,
            Screen.Belajar.route
        )

        val showNavigation = currentRoute in rootTabs

        val showTopBar = currentRoute in listOf(
            Screen.Pedoman.route,
            Screen.Pelanggaran.route,
            Screen.Belajar.route
        )

        val handleNavigation: (String) -> Unit = { targetRoute ->
            if (targetRoute in rootTabs) {
                navController.navigate(targetRoute) {
                    popUpTo(Screen.Home.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            } else {
                navController.navigate(targetRoute) {
                    launchSingleTop = true
                }
            }
        }

        val topBarContent = @Composable {
            if (showTopBar) {
                AppTopBar(
                    currentRoute = currentRoute,
                    onNavigateToSearch = {
                        handleNavigation(Screen.Search.route)
                    },
                    onNavigateToBookmarks = {
                        handleNavigation(Screen.Bookmarks.route)
                    },
                    onNavigateToAbout = {
                        handleNavigation(Screen.About.route)
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        val navHostContent: @Composable (androidx.compose.foundation.layout.PaddingValues) -> Unit = { innerPadding ->
            AdaptiveScreenContainer(
                modifier = Modifier.padding(innerPadding),
                maxWidth = windowInfo.maxContentWidth
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            viewModel = viewModel,
                            onNavigate = handleNavigation
                        )
                    }

                    composable(Screen.Pedoman.route) {
                        PedomanScreen(
                            viewModel = viewModel
                        )
                    }

                    composable(Screen.Pelanggaran.route) {
                        PelanggaranScreen(
                            viewModel = viewModel
                        )
                    }

                    composable(Screen.TanyaTatib.route) {
                        TanyaTatibScreen(
                            viewModel = viewModel,
                            onNavigate = handleNavigation
                        )
                    }

                    composable(Screen.Belajar.route) {
                        LearningScreen(
                            viewModel = viewModel,
                            onNavigate = handleNavigation
                        )
                    }

                    composable(Screen.Quiz.route) {
                        QuizScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(Screen.Standards.route) {
                        VisualStandardsScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(Screen.Search.route) {
                        SmartSearchScreen(
                            viewModel = viewModel,
                            onNavigate = handleNavigation,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(Screen.Bookmarks.route) {
                        BookmarksScreen(
                            viewModel = viewModel,
                            onNavigate = handleNavigation,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(Screen.EducationalResources.route) {
                        EducationalResourcesScreen(
                            viewModel = viewModel,
                            onNavigate = handleNavigation,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(Screen.About.route) {
                        AboutScreen(
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(GradientAppBackground),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets.safeDrawing,
            topBar = topBarContent,
            bottomBar = {
                if (showNavigation) {
                    AppBottomBar(
                        currentRoute = currentRoute,
                        onNavigate = handleNavigation
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GradientAppBackground)
            ) {
                navHostContent(innerPadding)
            }
        }
    }
}
