package com.quantumy.cursofirebaselite.presentation.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen (){

    val db = Firebase.firestore
    Button(onClick = {
        createArtist()
    }) {
        Text(text = "Crear Artista")
    }
}

data class Artist(
    val name:String,
    val numberOfSongs: Int
)

fun createArtist(){

}