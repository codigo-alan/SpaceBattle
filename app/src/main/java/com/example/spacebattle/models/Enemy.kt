package com.example.spacebattle.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Enemy(context: Context, screenX: Int, screenY: Int, drawableImage: Int) {

    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, drawableImage)
    val width = screenX / 10f
    val height = screenY / 10f
    var positionX = screenX / 2
    var positionY = screenY / 2
    var speed = 0

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }

    fun updateElement(){
        positionX += speed
        positionY -= speed
    }


}