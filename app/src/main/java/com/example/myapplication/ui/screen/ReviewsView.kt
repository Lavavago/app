package com.example.myapplication.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Place
import com.example.myapplication.model.Review
import com.example.myapplication.ui.components.ReviewForm
import com.example.myapplication.viewmodel.ReviewsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewsView(
    place: Place,
    reviewsViewModel: ReviewsViewModel
) {
    // Obtiene las reseñas filtradas para el lugar actual.
    val reviewsList = reviewsViewModel.getReviewsByPlace(place.id)

    // CORRECCIÓN: Se inicializa a 'true' para que el formulario aparezca de inmediato.
    var showReviewForm by remember { mutableStateOf(true) }
    // Nota: Mantenemos el estado 'showReviewForm' en caso de que en el futuro
    // quieras volver a ocultar el formulario después del envío.

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- FORMULARIO DE RESEÑA (Visible por defecto) ---
        if (showReviewForm) {
            ReviewForm(
                placeID = place.id,
                viewModel = reviewsViewModel,
                // Al enviar la reseña, puedes decidir ocultar el formulario (o mantenerlo visible)
                onReviewSubmitted = {
                    // Si quisieras que se oculte después de enviar, descomenta la siguiente línea:
                    // showReviewForm = false
                }
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))
        }

        // --- TÍTULO DE LA LISTA DE COMENTARIOS/RESEÑAS ---
        Text(
            text = "Comentarios (${reviewsList.size})",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // --- LISTA DE RESEÑAS ---
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            if (reviewsList.isEmpty()) {
                item {
                    Text(
                        "Sé el primero en comentar este lugar.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(reviewsList) { review ->
                    ReviewItem(review = review)
                    Divider()
                }
            }
        }
    }
}

// --- Componente Auxiliar ReviewItem para mostrar cada reseña individualmente ---
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewItem(review: Review) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "⭐ ${review.rating}/5",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 12.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = review.username,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = review.date.toLocalDate().toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            // Comentario
            Text(
                text = review.comment,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}