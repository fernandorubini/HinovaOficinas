package br.com.fernandodev.hinovaoficinas.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fernandodev.hinovaoficinas.ui.os.OSDetailScreen
import br.com.fernandodev.hinovaoficinas.ui.os.OSListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = NavRoutes.OS_LIST
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Lista de OS
        composable(route = NavRoutes.OS_LIST) {
            OSListScreen(
                onOpenDetail = { osId ->
                    navController.navigate(NavRoutes.osDetail(osId))
                }
            )
        }

        // Detalhe da OS
        composable(
            route = NavRoutes.OS_DETAIL_ROUTE_PATTERN,
            arguments = listOf(
                navArgument(NavRoutes.ARG_OS_ID) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val osId = backStackEntry.arguments?.getLong(NavRoutes.ARG_OS_ID) ?: 0L
            OSDetailScreen(
                osId = osId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
