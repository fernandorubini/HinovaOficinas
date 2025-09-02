package br.com.fernandodev.hinovaoficinas.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

private sealed class BottomDest(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Orders : BottomDest(NavRoutes.OS_LIST, "Ordens", Icons.Outlined.List)
    object Profile : BottomDest("profile", "Perfil", Icons.Outlined.Person)
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(BottomDest.Orders, BottomDest.Profile)

    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        items.forEach { dest ->
            NavigationBarItem(
                selected = currentRoute?.startsWith(dest.route) == true,
                onClick = {
                    navController.navigate(dest.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                icon = { Icon(dest.icon, contentDescription = dest.label) },
                label = { Text(dest.label) }
            )
        }
    }
}
