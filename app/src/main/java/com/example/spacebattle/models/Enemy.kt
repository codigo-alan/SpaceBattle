package com.example.spacebattle.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.spacebattle.R

class Enemy(context: Context, screenX: Int, screenY: Int, drawableImage: Int, speed: Int) {

    private var enemyContext = context
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, drawableImage)
    val width = screenX / 10f
    val height = screenY / 10f
    var positionX : Int
    var positionY = 0
    var speed = speed
    var keepAlive = true

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
        positionX = (screenX - (100..800 step width.toInt()).shuffled().first().toFloat()).toInt()
    }

    fun updateElement(){
        positionY += speed
    }

    fun kill() {
        changeBitmap(R.drawable.explode)
        keepAlive = false
    }
    private fun changeBitmap(drawableImage : Int){
        bitmap = BitmapFactory.decodeResource(enemyContext.resources, drawableImage)
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }


}