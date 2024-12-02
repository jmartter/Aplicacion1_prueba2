package com.example.aplicacion1_prueba2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AddClassScreenUI(onNavigate: (String) -> Unit) {
    var subject by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf("") }
    var selectedHour by remember { mutableStateOf("") }
    var dayExpanded by remember { mutableStateOf(false) }
    var hourExpanded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val daysOfWeek = listOf("lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo")
    val hours = (0..23).map { "${it.toString().padStart(2, '0')}:00" }

    val db = FirebaseFirestore.getInstance()

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
                text = "Mi horario - Añadir asignatura",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp) // Texto alineado a la izquierda
            )
        }
        Spacer(modifier = Modifier.height(40.dp)) // Espaciado entre el encabezado y el primer campo

        // Input field for subject
        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Asignatura") },
            modifier = Modifier
                .fillMaxWidth(0.9f) // Ancho al 90%
                .padding(bottom = 16.dp)
        )

        // Dropdown button for day of the week
        Box(modifier = Modifier
            .fillMaxWidth(0.9f) // Ancho al 90%
            .padding(bottom = 16.dp)) {
            Button(onClick = { dayExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(if (selectedDay.isEmpty()) "Seleccionar día" else selectedDay)
            }
            DropdownMenu(
                expanded = dayExpanded,
                onDismissRequest = { dayExpanded = false }
            ) {
                daysOfWeek.forEach { day ->
                    DropdownMenuItem(
                        text = { Text(text = day) },
                        onClick = {
                            selectedDay = day
                            dayExpanded = false
                        }
                    )
                }
            }
        }

        // Dropdown button for hour
        Box(modifier = Modifier
            .fillMaxWidth(0.9f) // Ancho al 90%
            .padding(bottom = 32.dp)) {
            Button(onClick = { hourExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(if (selectedHour.isEmpty()) "Seleccionar hora" else selectedHour)
            }
            DropdownMenu(
                expanded = hourExpanded,
                onDismissRequest = { hourExpanded = false },
                modifier = Modifier.heightIn(max = 200.dp) // Altura fija
            ) {
                Column {
                    hours.forEach { hour ->
                        DropdownMenuItem(
                            text = { Text(text = hour) },
                            onClick = {
                                selectedHour = hour
                                hourExpanded = false
                            }
                        )
                    }
                }
            }
        }

        // Display error message if any
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onNavigate("mainScreen") }) {
                Text("Cancelar")
            }
            Button(onClick = {
                // Check if a class already exists for the selected day and hour
                db.collection("classes")
                    .whereEqualTo("day", selectedDay)
                    .whereEqualTo("startHour", selectedHour)
                    .get()
                    .addOnSuccessListener { result ->
                        if (result.isEmpty) {
                            // Calculate end time
                            val startHour = selectedHour.split(":")[0].toInt()
                            val endHour = (startHour + 1).toString().padStart(2, '0') + ":00"
                            val classData = hashMapOf(
                                "subject" to subject,
                                "day" to selectedDay,
                                "startHour" to selectedHour,
                                "endHour" to endHour
                            )
                            db.collection("classes")
                                .add(classData)
                                .addOnSuccessListener {
                                    onNavigate("mainScreen")
                                }
                                .addOnFailureListener { e ->
                                    errorMessage = "Error al añadir la clase: ${e.message}"
                                }
                        } else {
                            errorMessage = "Ya existe una clase a esa hora el mismo día."
                        }
                    }
                    .addOnFailureListener { e ->
                        errorMessage = "Error al comprobar la clase: ${e.message}"
                    }
            }) {
                Text("Añadir")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddClassScreenUIPreview() {
    AddClassScreenUI(onNavigate = {})
}