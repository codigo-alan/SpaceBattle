package com.example.spacebattle.models

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.MotionEvent
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
    var score = 0
    val enemies = mutableListOf<Enemy>()
    val player = Player(context, size.x, size.y, R.drawable.player)
    var tret : Tret? = null

    init {
        startGame()
    }

    private fun addEnemies() {

        if (enemies.size < 5) {
            when ((0..100).random()){
                10 -> {
                    val randomSpeed = (5..15 step 5).shuffled().first()
                    val randomEnemyImage = listOf<Int>(R.drawable.enemybabylon, R.drawable.enemybabylon2, R.drawable.enemyvulcans).random()
                    val enemy = Enemy(context, size.x, size.y, randomEnemyImage, randomSpeed)
                    enemies.add(enemy)
                }
            }
        }

    }

    private fun startGame(){
        CoroutineScope(Dispatchers.Main).launch{
            while(playing){
                addEnemies()
                draw()
                update()
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
            canvas.drawText("Score: $score", 10f, 75f, paint)
            canvas.drawColor(Color.BLACK)
            //ENEMY
            enemies.forEach {enemy ->
                canvas.drawBitmap(enemy.bitmap, enemy.positionX.toFloat(),enemy.positionY.toFloat(), null)
            }
            //PLAYER
            canvas.drawBitmap(player.bitmap, player.positionX.toFloat(), (size.y - player.height - 250f), null)
            //TRET
            tret?.let { canvas.drawBitmap(it.bitmap, it.positionX.toFloat(), it.positionY.toFloat(), null) }

            holder.unlockCanvasAndPost(canvas)
        }
    }
    private fun update(){
        enemies.forEach {
            it.updateElement()
        }
        tret?.updateElement()
    }

    fun shot(){
        tret = Tret(context,size.x, size.y, player.positionX, (size.y - player.height - 250f).toInt())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when(event.action){
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {

                    //TODO the view of moves is not properly
                    if(event.x > player.positionX){
                        player.updateElement(PlayerMoves.RIGHT)
                    }
                    if(event.x < player.positionX){
                        player.updateElement(PlayerMoves.LEFT)
                    }
                }

            }
        }
        return true
    }



}