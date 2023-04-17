package com.example.spacebattle.viewmodels

import android.content.Context
import android.graphics.Point
import androidx.lifecycle.ViewModel
import com.example.spacebattle.models.GameView

class GameViewModel : ViewModel() {

    lateinit var gameView: GameView
    fun initializeGameView(requireContext: Context, size: Point) {
        gameView = GameView(requireContext, size)
    }

    fun stopMediaPlayer(){
        gameView.mediaPlayer?.stop()
    }
    fun destroyGameView(){
        val gameViewSurface = gameView.holder.surface
        gameViewSurface.release()
    }
}