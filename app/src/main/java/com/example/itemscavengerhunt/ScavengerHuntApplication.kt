package com.example.itemscavengerhunt

import android.app.Application

class ScavengerHuntApplication : Application() {

    override fun onCreate(){
        super.onCreate()
        ItemRepository.initialize(this)
    }

}