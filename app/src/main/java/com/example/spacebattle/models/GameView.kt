package com.example.spacebattle.models

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.SurfaceView
import com.example.spacebattle.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameView(context: Context, private val size: Point) : SurfaceView(context) {

    var canvas: Canvas = Canvas()
    val paint: Paint = Paint()
    var playing = true
    val enemies = mutableListOf<Enemy>()
    val player = Player(context, size.x, size.y, R.drawable.player)

    init {
        startGame()
    }

    private fun addEnemies() {
        val randomEnemiesQty = (0..100).random()

        when (randomEnemiesQty){
            10 -> {
                val randomEnemyImage = listOf<Int>(R.drawable.enemybabylon, R.drawable.enemybabylon2, R.drawable.enemyvulcans).random()
                val enemy = Enemy(context, size.x, size.y, randomEnemyImage)
                enemies.add(enemy)
            }

        }

    }

    private fun startGame(){
        CoroutineScope(Dispatchers.Main).launch{
            while(playing){
                addEnemies()
                draw()
                //update()
                delay(10)
            }
        }
    }
    private fun draw(){

        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            //SCORE
            paint.color = Color.YELLOW
            paint.textSize = 60f
            paint.textAlign = Paint.Align.LEFT
            canvas.drawText("Score: ", 10f, 75f, paint)
            //ENEMY
            enemies.forEach {enemy ->
                canvas.drawBitmap(enemy.bitmap, enemy.positionX.toFloat(),0f, null)
            }
            //PLAYER
            canvas.drawBitmap(player.bitmap, player.positionX.toFloat(), (size.y - player.height - 100f), null)

            holder.unlockCanvasAndPost(canvas)
        }
    }



}