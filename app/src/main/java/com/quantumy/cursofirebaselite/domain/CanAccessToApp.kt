package com.quantumy.cursofirebaselite.domain

import com.quantumy.cursofirebaselite.data.Repository

class CanAccessToApp {

    val respository = Repository()

    suspend operator fun invoke(): Boolean{
        val currentVersion = respository.getCurrentVersion() //1.0.3
        val minAllowedVersion = respository.getMinAllowedVersion() //1.0.2

        for ((currentPart,minVersionPart) in currentVersion.zip(minAllowedVersion)){
            if (currentPart!=minVersionPart){
                return currentPart > minVersionPart
            }
        }

        //Login
        return true
    }
}