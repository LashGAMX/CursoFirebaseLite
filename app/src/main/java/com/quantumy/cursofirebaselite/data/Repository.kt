package com.quantumy.cursofirebaselite.data

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.quantumy.cursofirebaselite.CursoFirebaseApp.Companion.context
import kotlinx.coroutines.tasks.await

class Repository {

    companion object{
        const val MIN_VERSION = "min_version"
    }

    val remoteConfig:FirebaseRemoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 30 })
        fetchAndActivate()
    }

    suspend fun getMinAllowedVersion(): List<Int> {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        val minVersion = remoteConfig.getString(MIN_VERSION)
        if (minVersion.isBlank()) return listOf(0,0,0)

        return minVersion.split(".").map { it.toInt() }
    }

    fun getCurrentVersion(): List<Int> {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName!!.split(".").map { it.toInt() }
        }catch (e: Exception){
            listOf(0,0,0)
        }
    }


}