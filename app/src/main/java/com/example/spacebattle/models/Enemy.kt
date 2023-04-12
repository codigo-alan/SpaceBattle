package com.example.spacebattle.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Enemy(context: Context, screenX: Int, screenY: Int, drawableImage: Int, speed: Int) {

    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, drawableImage)
    val width = screenX / 10f
    val height = screenY / 10f
    var positionX : Int
    var positionY = screenY / 2
    var speed = speed

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
        positionX = (screenX - (100..800 step width.toInt()).shuffled().first().toFloat()).toInt()
    }

    fun updateElement(){
        //positionX += speed
        //TODO it does not change the position
        positionY -= speed
    }


}