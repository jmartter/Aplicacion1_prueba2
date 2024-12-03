package com.example.aplicacion1_prueba2.Pantalla3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

@Composable
fun ViewScheduleScreenUI() {
    var selectedDay by remember { mutableStateOf("") }
    var dayExpanded by remember { mutableStateOf(false) }
    val daysOfWeek = listOf("lunes", "martes", "miercoles", "jueves", "viernes", "sabado", "domingo") // sin acentos porque sino da errores
    val db = FirebaseFirestore.getInstance()
    var subjects by remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }

    fun fetchSubjectsForDay(day: String) {
        db.collection("classes")
            .whereEqualTo("day", day)
            .get()
            .addOnSuccessListener { result: QuerySnapshot ->
                subjects = result.documents.map { it.data ?: emptyMap() }
            }
            .addOnFailureListener { exception ->

            }
    }

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
                text = "Mi horario - Ver horario",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp) // Texto alineado a la izquierda
            )
        }
        Spacer(modifier = Modifier.height(40.dp)) // Espaciado entre el encabezado y el primer campo

        // Dropdown button for day of the week
        Box(modifier = Modifier
            .fillMaxWidth(0.9f) // Ancho al 90%
            .padding(bottom = 16.dp)) {
            Button(
                onClick = { dayExpanded = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray), // Gris más fuerte
                shape = RectangleShape, // Forma rectangular
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (selectedDay.isEmpty()) "Seleccionar día" else selectedDay)
                Icon(Icons.Filled.ArrowDropDown, contentDescription = null, tint = Color.White)
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
                            fetchSubjectsForDay(day)
                        }
                    )
                }
            }
        }

        // Display the list of subjects for the selected day
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)) { // Margen izquierdo y derecho
            subjects.forEach { subject ->
                Column {
                    Text(
                        text = subject["subject"] as? String ?: "Unknown",
                        color = Color.Black,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Divider(
                        color = Color.Gray.copy(alpha = 0.5f), // Menos opaca
                        thickness = 1.dp,
                        modifier = Modifier.padding(end = 16.dp) // Espacio a la derecha
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewScheduleScreenUIPreview() {
    ViewScheduleScreenUI()
}