package net.iesseveroochoa.victorsanchez.tareasv01.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.FotoScreen
import net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.listatareas.ListaTareasScreen
import net.iesseveroochoa.victorsanchez.tareasv01.ui.screens.tarea.taskScreen

// Configuración de la Navegación
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ListaTareasDestination
    ) {
        composable(
            route = ListaTareasDestination,
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(1300),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(1300),
                    targetOffsetX = { it }
                )
            }
        ) {
            ListaTareasScreen(
                onNuevaTareaClick = { navController.navigate(getTareaDestination()) },
                onTareaClick = { tareaId -> navController.navigate(getTareaDestination(tareaId)) }
            )
        }
        composable(
            route = TareaDestination,
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(1300),
                    initialOffsetX = { -it } // Desde la izquierda
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(1300),
                    targetOffsetX = { -it } // Hacia la izquierda
                )
            }
        )
        { backStackEntry ->
            val tareaId = backStackEntry.arguments?.getString("tareaId")?.toLongOrNull()
            taskScreen(
                tareaId = tareaId,
                onNavigateBack = { navController.popBackStack()},
                onFotoClick = {navController.navigate(FotoDestination)}
            )
        }
        composable(
            route = FotoDestination,
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(1300),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(1300),
                    targetOffsetX = { it }
                )
            }
        )
        { backStackEntry ->
            val fotoUri = backStackEntry.arguments?.getString("uriImagen") ?: ""
                FotoScreen(
                    fotoUri = fotoUri,
                    onNavigateBack = { navController.popBackStack() }
                )

        }
    }
}