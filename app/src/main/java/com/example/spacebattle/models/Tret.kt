package com.example.spacebattle.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import com.example.spacebattle.R

class Tret(context: Context, screenX: Int, screenY: Int, positionX: Int, positionY: Int) {

    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.verticallines)
    val width = screenX / 10f
    val height = screenY / 10f
    var positionX = positionX
    var positionY = positionY
    var speed = 100

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }

    fun updateElement(){
        positionY -= speed
    }


}