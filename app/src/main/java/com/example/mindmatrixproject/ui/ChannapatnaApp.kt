package com.example.mindmatrixproject.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mindmatrixproject.ui.components.NavTransitions
import com.example.mindmatrixproject.ui.screens.ArtisanDetailScreen
import com.example.mindmatrixproject.ui.screens.CatalogScreen
import com.example.mindmatrixproject.ui.screens.CooperativeDetailScreen
import com.example.mindmatrixproject.ui.screens.HomeScreen
import com.example.mindmatrixproject.ui.screens.HowItsMadeScreen
import com.example.mindmatrixproject.ui.screens.MeetTheMakerScreen
import com.example.mindmatrixproject.ui.screens.ToyDetailScreen
import com.example.mindmatrixproject.ui.screens.VerifyScreen

object Routes {
    const val HOME = "home"
    const val VERIFY = "verify"
    const val MAKERS = "makers"
    const val CATALOG = "catalog"
    const val HOW_MADE = "how_made"
    const val TOY_DETAIL = "toy/{toyId}"
    const val ARTISAN_DETAIL = "artisan/{artisanId}"
    const val COOP_DETAIL = "coop/{coopId}"

    fun toy(id: String) = "toy/$id"
    fun artisan(id: String) = "artisan/$id"
    fun coop(id: String) = "coop/$id"
}

private data class TabSpec(val route: String, val label: String, val icon: ImageVector)

private val tabs = listOf(
    TabSpec(Routes.HOME, "Home", Icons.Default.Home),
    TabSpec(Routes.VERIFY, "Verify", Icons.Default.QrCodeScanner),
    TabSpec(Routes.MAKERS, "Makers", Icons.Default.LocationOn),
    TabSpec(Routes.CATALOG, "Catalog", Icons.Default.Storefront),
)

@Composable
fun ChannapatnaApp() {
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val showBottomBar = currentRoute in tabs.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                ChannapatnaBottomBar(
                    currentRoute = currentRoute,
                    onSelect = { route ->
                        nav.navigate(route) {
                            popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Routes.HOME,
            modifier = Modifier.fillMaxSize().padding(padding),
            enterTransition = { NavTransitions.slideEnter() },
            exitTransition = { NavTransitions.slideExit() },
            popEnterTransition = { NavTransitions.slidePopEnter() },
            popExitTransition = { NavTransitions.slidePopExit() },
        ) {
            composable(
                Routes.HOME,
                enterTransition = { NavTransitions.fadeOnly() },
                exitTransition = { NavTransitions.fadeOutOnly() },
            ) {
                HomeScreen(
                    onVerify = { nav.navigate(Routes.VERIFY) },
                    onHowMade = { nav.navigate(Routes.HOW_MADE) },
                    onMakers = { nav.navigate(Routes.MAKERS) },
                    onCatalog = { nav.navigate(Routes.CATALOG) },
                    onToyClick = { id -> nav.navigate(Routes.toy(id)) },
                    onArtisanClick = { id -> nav.navigate(Routes.artisan(id)) },
                )
            }
            composable(
                Routes.VERIFY,
                enterTransition = { NavTransitions.fadeOnly() },
                exitTransition = { NavTransitions.fadeOutOnly() },
            ) {
                VerifyScreen(
                    onArtisanClick = { id -> nav.navigate(Routes.artisan(id)) },
                    onCoopClick = { id -> nav.navigate(Routes.coop(id)) },
                )
            }
            composable(
                Routes.MAKERS,
                enterTransition = { NavTransitions.fadeOnly() },
                exitTransition = { NavTransitions.fadeOutOnly() },
            ) {
                MeetTheMakerScreen(
                    onCoopClick = { id -> nav.navigate(Routes.coop(id)) },
                    onArtisanClick = { id -> nav.navigate(Routes.artisan(id)) },
                )
            }
            composable(
                Routes.CATALOG,
                enterTransition = { NavTransitions.fadeOnly() },
                exitTransition = { NavTransitions.fadeOutOnly() },
            ) {
                CatalogScreen(
                    onToyClick = { id -> nav.navigate(Routes.toy(id)) },
                )
            }

            composable(Routes.HOW_MADE) { HowItsMadeScreen(onBack = { nav.popBackStack() }) }

            composable(
                Routes.TOY_DETAIL,
                arguments = listOf(navArgument("toyId") { type = NavType.StringType }),
            ) { entry ->
                ToyDetailScreen(
                    toyId = entry.arguments?.getString("toyId") ?: "",
                    onBack = { nav.popBackStack() },
                    onArtisanClick = { id -> nav.navigate(Routes.artisan(id)) },
                    onCoopClick = { id -> nav.navigate(Routes.coop(id)) },
                )
            }
            composable(
                Routes.ARTISAN_DETAIL,
                arguments = listOf(navArgument("artisanId") { type = NavType.StringType }),
            ) { entry ->
                ArtisanDetailScreen(
                    artisanId = entry.arguments?.getString("artisanId") ?: "",
                    onBack = { nav.popBackStack() },
                    onCoopClick = { id -> nav.navigate(Routes.coop(id)) },
                    onToyClick = { id -> nav.navigate(Routes.toy(id)) },
                )
            }
            composable(
                Routes.COOP_DETAIL,
                arguments = listOf(navArgument("coopId") { type = NavType.StringType }),
            ) { entry ->
                CooperativeDetailScreen(
                    coopId = entry.arguments?.getString("coopId") ?: "",
                    onBack = { nav.popBackStack() },
                    onArtisanClick = { id -> nav.navigate(Routes.artisan(id)) },
                    onToyClick = { id -> nav.navigate(Routes.toy(id)) },
                )
            }
        }
    }
}

@Composable
private fun ChannapatnaBottomBar(currentRoute: String?, onSelect: (String) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
    ) {
        tabs.forEach { tab ->
            val selected = currentRoute == tab.route
            NavigationBarItem(
                selected = selected,
                onClick = { if (!selected) onSelect(tab.route) },
                icon = {
                    androidx.compose.material3.Icon(
                        tab.icon,
                        contentDescription = tab.label,
                    )
                },
                label = {
                    Text(
                        tab.label,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )
        }
    }
}