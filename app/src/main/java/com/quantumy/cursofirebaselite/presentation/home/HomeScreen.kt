package com.quantumy.cursofirebaselite.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.quantumy.cursofirebaselite.presentation.model.Artist
import com.quantumy.cursofirebaselite.ui.theme.Black

@Composable
fun HomeScreen (viewModel: HomeViewModel = HomeViewModel()){

    val artists = viewModel.artist.collectAsState()
    val blockVersion by viewModel.blockVersion.collectAsState()

    if (blockVersion){
        val context = LocalContext.current
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Card (
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ){
                Column (
                    modifier = Modifier.fillMaxSize().
                    height(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(text = "ACTUALIZA TU VERSION", fontSize = 22.sp, color = Black, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Para poder disfrutar de todo nuestro cotenido actualice la app", fontSize = 16.sp, color = Color.Gray)
                    Button(
                        onClick = {
                            navigateToPlayStore(context)
                        }
                    ) {
                        Text(text = "Actualizar")
                    }
                }
            }
        }
    }
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

fun navigateToPlayStore(context: Context){
    val appPackage = context.packageName
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackage")
            )
        )
    }catch (e: Exception){
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackage")
            )
        )
    }
}