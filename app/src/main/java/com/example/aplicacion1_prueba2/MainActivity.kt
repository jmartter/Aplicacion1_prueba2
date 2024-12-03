package com.example.aplicacion1_prueba2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicacion1_prueba2.Pantalla1.MainScreenUI
import com.example.aplicacion1_prueba2.Pantalla2.AddClassScreenUI
import com.example.aplicacion1_prueba2.Pantalla3.ViewScheduleScreenUI
import com.example.aplicacion1_prueba2.Pantalla4.CurrentClassScreenUI
import com.example.aplicacion1_prueba2.ui.theme.Aplicacion1_prueba2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Aplicacion1_prueba2Theme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationComponent(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "mainScreen", modifier = modifier) {
        composable("mainScreen") { MainScreenUI(onNavigate = { navController.navigate(it) }) }
        composable("addClass") { AddClassScreenUI(onNavigate = { navController.navigate(it) }) }
        composable("viewSchedule") { ViewScheduleScreenUI() }
        composable("currentClass") { CurrentClassScreenUI() }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Aplicacion1_prueba2Theme {
        MainScreenUI(onNavigate = {})
    }
}