package com.example.aplicacion1_prueba2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text

@Composable
fun AddClassScreenUI(onNavigate: (String) -> Unit) {
    var subject by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // Para controlar el menú desplegable
    val daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Mi horario - Añadir asignatura",
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        // Campo para la asignatura
        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Asignatura") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Menú desplegable para el día de la semana
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)) {
            OutlinedTextField(
                value = selectedDay,
                onValueChange = {}, // No cambia directamente
                label = { Text("Día de la semana") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true } // Activa el menú desplegable
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                daysOfWeek.forEach {
                    day ->DropdownMenuItem(
                    text = { Text(text = day) },
                    onClick = {
                        selectedDay = day
                        expanded = false // Cierra el menú
                    }
                )

                }
            }
        }

        // Campo para la hora
        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text("Hora") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        // Botones de acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onNavigate("mainScreen") }) {
                Text("Cancelar")
            }
            Button(onClick = {
                // Agrega la lógica para añadir la clase al horario
                onNavigate("mainScreen")
            }) {
                Text("Añadir")
            }
        }
    }
}
