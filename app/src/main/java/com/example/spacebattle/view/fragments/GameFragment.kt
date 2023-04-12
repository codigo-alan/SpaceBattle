package com.example.spacebattle.view.fragments

import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebattle.R
import com.example.spacebattle.databinding.FragmentGameBinding
import com.example.spacebattle.models.GameView
import com.example.spacebattle.viewmodels.GameViewModel


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val gameView = GameView(requireContext(), size)
        val game: FrameLayout = FrameLayout(requireContext())
        val gameButtons: RelativeLayout = RelativeLayout(requireContext())
        val fireButton: Button = Button(requireContext());
        fireButton.text = "Fire"
        fireButton.setBackgroundColor(Color.RED)
        val b1 = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.FILL_PARENT,
            RelativeLayout.LayoutParams.FILL_PARENT
        )
        gameButtons.layoutParams = params
        gameButtons.addView(fireButton)
        b1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
        b1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        fireButton.layoutParams = b1
        game.addView(gameView)
        game.addView(gameButtons)


        return game
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO not recognize the elements of layout
        /*fireButton.setOnClickListener {
            gameView.shot()
        }*/
    }


}