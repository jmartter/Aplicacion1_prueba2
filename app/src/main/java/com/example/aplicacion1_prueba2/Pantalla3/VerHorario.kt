package com.example.aplicacion1_prueba2.Pantalla3

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
import com.google.firebase.firestore.QuerySnapshot

@Composable
fun ViewScheduleScreenUI() {
    var selectedDay by remember { mutableStateOf("") }
    var dayExpanded by remember { mutableStateOf(false) }
    val daysOfWeek = listOf("lunes", "martes", "miercoles", "jueves", "viernes", "sabado", "domingo")
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
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Mi horario - Ver horario",
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = TextAlign.Center
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
                            fetchSubjectsForDay(day)
                        }
                    )
                }
            }
        }

        // Display the list of subjects for the selected day
        Column(modifier = Modifier.fillMaxWidth()) {
            subjects.forEach { subject ->
                Text(
                    text = subject["subject"] as? String ?: "Unknown",
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewScheduleScreenUIPreview() {
    ViewScheduleScreenUI()
}