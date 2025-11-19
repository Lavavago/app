package com.example.myapplication.ui.components // Asegúrate de que este paquete sea correcto

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Review
import com.example.myapplication.viewmodel.ReviewsViewModel
import java.time.LocalDateTime
import java.util.UUID
import androidx.compose.foundation.BorderStroke

// --- 1. FUNCIÓN PRINCIPAL DEL FORMULARIO ---
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewForm(
    placeID: String,
    viewModel: ReviewsViewModel,
    onReviewSubmitted: () -> Unit
) {
    // Variables de estado
    var rating by remember { mutableIntStateOf(5) }
    var comment by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Selector de Rating (Números) ---
        Text(
            text = "Tu Valoración (1-5):",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Usamos el nuevo componente sin iconos
        RatingSelector(
            rating = rating,
            onRatingChange = { rating = it },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // --- Campo de Comentario ---
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Escribe tu comentario...") },
            singleLine = false,
            maxLines = 5,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .padding(bottom = 16.dp)
        )

        // --- Botón de Envío ---
        Button(
            onClick = {
                if (comment.isNotBlank() && rating in 1..5 && !isSending) {
                    isSending = true

                    val newReview = Review(
                        id = UUID.randomUUID().toString(),
                        // **¡CORRECCIÓN/RECORDATORIO!** Debes usar el ID del usuario logueado.
                        userID = "999",
                        // **¡CORRECCIÓN/RECORDATORIO!** Debes usar el nombre del usuario logueado.
                        username = "Usuario Actual",
                        placeID = placeID,
                        rating = rating,
                        comment = comment.trim(),
                        date = LocalDateTime.now()
                    )

                    viewModel.create(newReview)

                    isSending = false
                    comment = ""
                    rating = 5
                    onReviewSubmitted()
                }
            },
            enabled = comment.isNotBlank() && rating in 1..5 && !isSending,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isSending) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                // Aquí solo usamos el ícono de "Send" que es básico
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Enviar",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Enviar Reseña y Comentario")
            }
        }
    }
}

// --- 2. COMPONENTE AUXILIAR RatingSelector (Selector de botones de número) ---
@Composable
fun RatingSelector(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxRating: Int = 5
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 1..maxRating) {
            val isSelected = i == rating

            // Usamos un OutlinedButton para hacerlo visualmente distinto
            OutlinedButton(
                onClick = { onRatingChange(i) },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                ),
                border = BorderStroke(
                    width = if (isSelected) 0.dp else 1.dp,
                    color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
            ) {
                Text(
                    text = "$i",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}