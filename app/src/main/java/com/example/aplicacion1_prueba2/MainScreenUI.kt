package com.example.aplicacion1_prueba2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreenUI(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Fondo gris claro
            .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Encabezado azul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF87CEEB)) // Azul cielo
                .height(56.dp)
        ) {
            Text(
                text = "Mi horario",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp) // Texto alineado a la izquierda
            )
        }
        Spacer(modifier = Modifier.height(40.dp)) // Espaciado entre el encabezado y el primer botón

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

@Composable
fun DefaultButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
        modifier = Modifier
            .fillMaxWidth(0.9f) // Ancho al 90%
            .height(48.dp) // Altura de los botones
            .padding(vertical = 8.dp) // Espaciado entre botones
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenUIPreview() {
    MainScreenUI(onNavigate = {})
}