package com.example.spacebattle.viewmodels

import android.content.Context
import android.graphics.Point
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacebattle.R
import com.example.spacebattle.models.GameView
import com.example.spacebattle.models.PlayerData

class GameViewModel : ViewModel() {

    lateinit var gameView: GameView
    //TODO observe this value
    var playerDataLiveData = MutableLiveData<PlayerData>()
    private val playerDataList = listOf<PlayerData>(
        PlayerData("Home One", R.drawable.player, 10, 20),
        PlayerData("Death Star", R.drawable.enemybabylon, 80, 8),
        PlayerData("Executor", R.drawable.enemybabylon2, 30, 12),
        PlayerData("Millennium Falcon", R.drawable.enemyvulcans, 20, 15),
        PlayerData("Imperial Star Destroyer", R.drawable.player, 15, 45),
    )

    init {
        playerDataLiveData.value = PlayerData("Home One", R.drawable.player, 10, 15) //default playerData
    }

    fun initializeGameView(requireContext: Context, size: Point) {
        gameView = GameView(requireContext, size, playerDataLiveData.value!!)
    }

    fun setPlayerData(name: String) {
        playerDataList.forEach {
            if (it.name == name) {
                playerDataLiveData.postValue(it)
            }
        }
    }

    fun getNames() : Array<String?> {
        val size = playerDataList.size
        val names = arrayOfNulls<String>(size)
        playerDataList.forEachIndexed { index, playerData ->
            names[index] = playerData.name
        }
        return names
    }


}