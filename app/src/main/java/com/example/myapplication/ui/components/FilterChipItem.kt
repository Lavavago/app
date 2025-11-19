package com.example.myapplication.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.PlaceType

@Composable
fun FilterChipItem(
    label: String,
    iconRes: Int,
    type: PlaceType,
    selectedFilters: List<PlaceType>,
    onChange: (List<PlaceType>) -> Unit
) {
    val isSelected = selectedFilters.contains(type)

    Surface(
        onClick = {
            onChange(
                if (isSelected) selectedFilters - type
                else selectedFilters + type
            )
        },
        shape = RoundedCornerShape(50),
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.White,
        border = BorderStroke(
            1.dp,
            if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
        ),
        modifier = Modifier.shadow(1.dp, RoundedCornerShape(50))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                tint = if (isSelected) Color.White else Color.Gray,
                modifier = Modifier
                    .width(20.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = label,
                color = if (isSelected) Color.White else Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}
