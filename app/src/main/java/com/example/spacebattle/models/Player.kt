package com.example.spacebattle.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.spacebattle.R

class Player (val context: Context, screenX: Int, screenY: Int, drawableImage: Int,
              var speed: Int, var shots: Int
)  {

    //val playerContext = context
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, drawableImage)
    val width = screenX / 10f
    val height = screenY / 10f
    var positionX = screenX / 2
    var positionY = screenY - height - 250f
    var keepAlive = true

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }

    fun updateElement(direction: PlayerMoves) {
        when (direction){
            PlayerMoves.RIGHT -> positionX += speed
            PlayerMoves.LEFT -> positionX -= speed
        }

    }

    fun kill(){
        changeBitmap(R.drawable.explode)
        keepAlive = false
    }
    private fun changeBitmap(drawableImage : Int){
        bitmap = BitmapFactory.decodeResource(context.resources, drawableImage)
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }


}

