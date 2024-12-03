package com.example.aplicacion1_prueba2.Pantalla1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreenUI(onNavigate: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Fondo gris claro
    ) {
        // Encabezado azul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF87CEEB)) // Azul cielo
                .height(56.dp)
                .align(Alignment.TopCenter) // Alinear en la parte superior
        ) {
            Text(
                text = "Mi horario",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp) // Texto alineado a la izquierda
            )
        }

        // Column for buttons centered vertically
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp), // Espacio para el encabezado
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centrar los botones verticalmente
        ) {
            // Botón "Añadir Clase"
            DefaultButton(
                text = "Añadir Clase",
                onClick = { onNavigate("addClass") }
            )
            // Botón "Ver Horario"
            DefaultButton(
                text = "Ver Horario",
                onClick = { onNavigate("viewSchedule") }
            )
            // Botón "¿Qué toca ahora?"
            DefaultButton(
                text = "¿Qué toca ahora?",
                onClick = { onNavigate("currentClass") }
            )
        }
    }
}

@Composable
fun DefaultButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray), // Gris más fuerte
        shape = RectangleShape, // Forma rectangular
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp, // Elevación moderada
            pressedElevation = 2.dp, // Elevación al presionar
            hoveredElevation = 12.dp // Elevación al pasar el cursor (en escritorio)
        ),
        modifier = Modifier
            .fillMaxWidth(0.9f) // Ancho al 90%
            .height(48.dp) // Altura de los botones
            .padding(vertical = 8.dp) // Espaciado entre botones
    ) {
        Text(text = text, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenUIPreview() {
    MainScreenUI(onNavigate = {})
}