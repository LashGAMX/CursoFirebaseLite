package com.quantumy.cursofirebaselite.presentation.home

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.quantumy.cursofirebaselite.presentation.model.Artist
import com.quantumy.cursofirebaselite.ui.theme.Black

@Composable
fun HomeScreen (viewModel: HomeViewModel = HomeViewModel()){

    val artists = viewModel.artist.collectAsState()
    Column (
        modifier = Modifier.fillMaxSize().background(Black)
    ){
        Text(
            "Popular artist",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp))



        LazyRow() {
            items(artists.value){
                ArtistItem(it)
            }
        }
    }
}

@Composable
fun ArtistItem(artist: Artist){
    Column(
        modifier = Modifier.padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AsyncImage(
            modifier = Modifier.size(60.dp).clip(CircleShape),
            model = artist.image,
            contentDescription = "Artist Image"
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text= artist.name.orEmpty(), color = Color.White)
    }
}

@Preview
@Composable
fun ArtistItemPreview(){
    val artist = Artist(
        name = "pepe",
        description = "the best",
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwyXeKDN29AmZgZPLS7n0Bepe8QmVappBwZCeA3XWEbWNdiDFB",
        //emptyList()
    )
    ArtistItem(artist = artist)
}

//fun createArtist(db: FirebaseFirestore){
//    val random = (1 .. 10000).random()
//    val artist = Artist(name = "Ramdom $random" , numberOfSongs = random)
//
//    db.collection("artists").add(artist)
//        .addOnSuccessListener {
//            Log.i("Lash ","SUCCESS")
//        }
//        .addOnFailureListener {
//            Log.i("Lash ","FAILURE")
//        }
//        .addOnCompleteListener {
//            Log.i("Lash ","COMPLETE")
//        }
//}