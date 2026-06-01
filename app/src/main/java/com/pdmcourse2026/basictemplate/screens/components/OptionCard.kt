package com.pdmcourse2026.basictemplate.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.pdmcourse2026.basictemplate.models.Place

@Composable
fun OptionCard(
    place : Place,
    onPlaceClick: (id: Int) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlaceClick(place.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults
            .elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row() {
            AsyncImage(
                model = place.imageUrl,
                contentDescription = place.name,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column() {
                Text(text = place.name)
                Text(text = "Toca para votar")
            }
        }
    }
}