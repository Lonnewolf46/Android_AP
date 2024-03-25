package com.example.android_ap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.android_ap.ui.AP_App

import com.example.android_ap.ui.theme.Android_APTheme
import android.util.Log



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            Android_APTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AP_App()
                }

                val apiCall = APICall()
                apiCall.getTasks { tasks ->
                    // Maneja la lista de tareas aquí
                    for (task in tasks) {
                        Log.d("TaskInfo", "ID: ${task.id}, Title: ${task.title}")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Android_APTheme {
        Greeting("Android")
    }
}

