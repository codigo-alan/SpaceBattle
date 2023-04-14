package com.example.spacebattle.models

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.lifecycle.MutableLiveData
import com.example.spacebattle.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameView(context: Context, private val size: Point) : SurfaceView(context) {

    var canvas: Canvas = Canvas()
    val paint: Paint = Paint()
    var playing = MutableLiveData<Boolean>().apply { true }
    var miVar : Boolean = true
    var totalEnemies = 10
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
                    totalEnemies --
                }
            }
        }
    }
    private fun deleteEnemies() {
        enemies.removeIf { (it.positionY >= size.y) || !(it.keepAlive) }
    }

    private fun startGame(){
        CoroutineScope(Dispatchers.Main).launch{
            while(totalEnemies >= 0){
                deleteEnemies()
                addEnemies()
                verifyCollisions()
                draw()
                update()
                delay(10)
                if (!player.keepAlive) break
            }
            playing.postValue(false)
        }
    }

    private fun verifyCollisions() {
        enemies.forEach {enemy ->
            var positionPlayer = RectF()
            positionPlayer.left = player.positionX.toFloat()
            positionPlayer.top = player.positionY.toFloat()
            positionPlayer.right = positionPlayer.left + player.width
            positionPlayer.bottom = positionPlayer.top + player.height
            var positionEnemy = RectF()
            positionEnemy.left = enemy.positionX.toFloat()
            positionEnemy.top = enemy.positionY.toFloat()
            positionEnemy.right = positionEnemy.left + enemy.width
            positionEnemy.bottom = positionEnemy.top + enemy.height
            var positionTret = RectF()
            if (tret != null) {
                positionTret.left = tret!!.positionX.toFloat()
                positionTret.top = tret!!.positionY.toFloat()
                positionTret.right = positionTret.left + tret!!.width
                positionTret.bottom = positionTret.top + tret!!.height
            }
            if(RectF.intersects(positionEnemy, positionPlayer)){
                player.kill()
            }
            if(RectF.intersects(positionEnemy, positionTret)){
                enemy.kill()
                tret = null
                score ++
            }
        }

    }

    private fun draw(){

        if (holder.surface.isValid) {

            canvas = holder.lockCanvas()

            //SCORE
            canvas.drawColor(Color.BLACK)
            paint.color = Color.YELLOW
            paint.textSize = 60f
            paint.textAlign = Paint.Align.LEFT
            canvas.drawText("Score: $score", 10f, 75f, paint)
            //ENEMY
            enemies.forEach {enemy ->
                canvas.drawBitmap(enemy.bitmap, enemy.positionX.toFloat(),enemy.positionY.toFloat(), null)
            }
            //PLAYER
            canvas.drawBitmap(player.bitmap, player.positionX.toFloat(), player.positionY.toFloat(), null)
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