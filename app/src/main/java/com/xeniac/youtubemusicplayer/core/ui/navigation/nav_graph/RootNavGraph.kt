package com.xeniac.youtubemusicplayer.core.ui.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.xeniac.youtubemusicplayer.core.ui.navigation.Screen
import com.xeniac.youtubemusicplayer.feature_youtube_player.presentation.YouTubePlayerScreen

@Composable
fun SetupRootNavGraph(
    rootNavController: NavHostController,
    startDestination: Screen = Screen.YouTubePlayerScreen
) {
    NavHost(
        navController = rootNavController,
        startDestination = startDestination
    ) {
        composable<Screen.YouTubePlayerScreen> {
            YouTubePlayerScreen()
        }
    }
}