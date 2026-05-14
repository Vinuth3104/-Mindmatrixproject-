package com.example.mindmatrixproject

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mindmatrixproject.ui.ChannapatnaApp
import com.example.mindmatrixproject.ui.theme.MindmatrixProjectTheme
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // OSMDroid needs a unique User-Agent for OpenStreetMap tile servers.
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences("osmdroid_prefs", Context.MODE_PRIVATE),
        )
        Configuration.getInstance().userAgentValue = packageName

        enableEdgeToEdge()
        setContent {
            MindmatrixProjectTheme {
                ChannapatnaApp()
            }

        }
    }
}