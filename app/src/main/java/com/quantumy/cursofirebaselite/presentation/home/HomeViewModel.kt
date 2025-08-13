package com.quantumy.cursofirebaselite.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.quantumy.cursofirebaselite.domain.CanAccessToApp
import com.quantumy.cursofirebaselite.presentation.model.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class  HomeViewModel:ViewModel(){

    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    private var db: FirebaseFirestore = Firebase.firestore
    private var canAccessToApp: CanAccessToApp = CanAccessToApp()


    val artist: StateFlow<List<Artist>> = _artists

    private val _blockVersion = MutableStateFlow<Boolean>(false)
    val blockVersion: StateFlow<Boolean> = _blockVersion

    init {
//        repeat(20){
//            loadData()
//        }
        checkUserVersion()
     db = Firebase.firestore
     getArtists()
    }

    private fun checkUserVersion() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                canAccessToApp.invoke()
            }
            _blockVersion.value = !result
        }
    }

    private fun loadData() {
        val random = (1 .. 10000).random()
    val artist = Artist(name = "Ramdom $random" , description = "Descriptión randon num $random", image = "https://www.grupoxcaret.com/es/wp-content/uploads/2021/03/aves-boris-1068x712.jpg")

    db.collection("artists").add(artist)
        .addOnSuccessListener {
            Log.i("Lash ","SUCCESS")
        }
        .addOnFailureListener {
            Log.i("Lash ","FAILURE")
        }
        .addOnCompleteListener {
            Log.i("Lash ","COMPLETE")
        }
    }


    private fun getArtists() {
        viewModelScope.launch {
            val result: List<Artist> = withContext(Dispatchers.IO) {
                getAllArtists()
            }
            _artists.value = result
        }
    }

    private suspend fun getAllArtists(): List<Artist> {
        return try {
            db.collection("artists").get().await().documents.mapNotNull { snapshot ->
                snapshot.toObject(Artist::class.java)
            }
        } catch (e: Exception) {
            Log.i("aris", e.toString())
            emptyList()
        }
    }
}