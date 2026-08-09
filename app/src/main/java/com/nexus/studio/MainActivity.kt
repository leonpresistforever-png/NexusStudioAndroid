package com.nexus.studio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.nexus.studio.ui.auth.AuthScreen
import com.nexus.studio.ui.theme.NexusStudioTheme
import com.nexus.studio.ui.workspace.WorkspaceScreen

enum class ScreenState {
    AUTH,
    WORKSPACE
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NexusStudioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf(ScreenState.AUTH) }

                    Crossfade(
                        targetState = currentScreen,
                        label = "ScreenTransition"
                    ) { screen ->
                        when (screen) {
                            ScreenState.AUTH -> AuthScreen(
                                onNavigateToWorkspace = { currentScreen = ScreenState.WORKSPACE }
                            )
                            ScreenState.WORKSPACE -> WorkspaceScreen(
                                onNavigateBackToAuth = { currentScreen = ScreenState.AUTH }
                            )
                        }
                    }
                }
            }
        }
    }
}
