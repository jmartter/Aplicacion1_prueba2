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
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CurrentClassScreenUI() {
    val db = FirebaseFirestore.getInstance()
    var currentClass by remember { mutableStateOf<String?>(null) }
    var currentTime by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val calendar = Calendar.getInstance()
        val dayOfWeek = SimpleDateFormat("EEEE", Locale("es", "ES")).format(calendar.time)
        val hour = calendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
        val minute = calendar.get(Calendar.MINUTE).toString().padStart(2, '0')
        currentTime = "$hour:$minute"
        currentDate = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("es", "ES")).format(calendar.time)

        db.collection("classes")
            .whereEqualTo("day", dayOfWeek)
            .get()
            .addOnSuccessListener { result ->
                val matchingClass = result.documents.firstOrNull { document ->
                    val startHour = document.getString("startHour")?.split(":")?.get(0)?.toInt() ?: 0
                    startHour == hour.toInt()
                }
                currentClass = matchingClass?.getString("subject")
            }
            .addOnFailureListener {
                currentClass = null
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
            text = currentDate,
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = currentTime,
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = TextAlign.Center
        )
        if (currentClass != null) {
            Text(
                text = "Estás en clase de $currentClass",
                color = Color.Red,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 4.dp),
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = "No hay ninguna clase registrada",
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrentClassScreenUIPreview() {
    CurrentClassScreenUI()
}