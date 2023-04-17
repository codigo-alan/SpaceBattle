package com.example.spacebattle.models

import android.content.Context
import android.graphics.*
import android.media.MediaPlayer
import android.view.MotionEvent
import android.view.SurfaceView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.spacebattle.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.round

class GameView(context: Context, private val size: Point) : SurfaceView(context) {

    var canvas: Canvas = Canvas()
    val paint: Paint = Paint()
    var playing = MutableLiveData<Boolean>().apply { true }
    val totalTrets = 20
    var tretsMades = 0
    var enemyDown = 0
    var totalEnemies = 0
    val lostEnemies get() = totalEnemies - enemyDown
    var score: Double = 0.0
    val enemies = mutableListOf<Enemy>()
    val player = Player(context, size.x, size.y, R.drawable.player)
    var tret : Tret? = null
    var mediaPlayer: MediaPlayer? = MediaPlayer.create(context, R.raw.imperial_march)

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
                    totalEnemies++
                }
            }
        }
    }
    private fun deleteEnemies() {
        enemies.removeIf { (it.positionY >= size.y) || !(it.keepAlive) }
    }

    private fun startGame(){
        CoroutineScope(Dispatchers.Main).launch{
            mediaPlayer?.start()
            mediaPlayer?.setOnCompletionListener {
                mediaPlayer?.start()
            }
            while(totalEnemies <= 10){
                deleteEnemies()
                addEnemies()
                verifyCollisions()
                draw()
                update()
                delay(10)
                if (!player.keepAlive) break
            }
            score = (enemyDown.toDouble() / (lostEnemies+1))
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
                //TODO add explode with soundPool
            }
            if(RectF.intersects(positionEnemy, positionTret)){
                enemy.kill()
                tret = null
                enemyDown++
                //TODO add explode with soundPool
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
            canvas.drawText("Asserted: $enemyDown", 10f, 75f, paint)
            canvas.drawText("Left shots: ${totalTrets - tretsMades}", 10f, 150f, paint)
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
        if (tretsMades < totalTrets) {
            tret = Tret(context,size.x, size.y, player.positionX, (size.y - player.height - 250f).toInt())
            tretsMades++
        } else {
            Toast.makeText(context,"Not enough munitions!", Toast.LENGTH_SHORT).show()
        }
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