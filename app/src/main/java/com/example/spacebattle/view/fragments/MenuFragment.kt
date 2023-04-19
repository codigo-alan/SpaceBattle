package com.example.spacebattle.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebattle.R
import com.example.spacebattle.databinding.FragmentMenuBinding
import com.example.spacebattle.viewmodels.GameViewModel
import com.example.spacebattle.viewmodels.MenuViewModel


class MenuFragment : Fragment(), AdapterView.OnItemClickListener {

    private lateinit var binding: FragmentMenuBinding
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Dropdown
        val players = viewModel.getNames()
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,players)//array adapter to put later in autoCompleteTextView
        with(binding.autoCompleteTextView){
            setAdapter(adapter)
            onItemClickListener = this@MenuFragment //this is the context in fragments maybe
        }

        //observe playerData
        viewModel.playerDataLiveData.observe(viewLifecycleOwner){
            if (it == null) {
                binding.playerIv.visibility = View.GONE
                binding.speedTv.visibility = View.GONE
                binding.shotsTv.visibility = View.GONE
                binding.yodaIv.visibility = View.GONE
            }else{
                binding.playerIv.visibility = View.VISIBLE
                binding.speedTv.visibility = View.VISIBLE
                binding.shotsTv.visibility = View.VISIBLE
                binding.yodaIv.visibility = View.VISIBLE
                binding.playerIv.setImageResource(it.image)
                binding.speedTv.text = "Speed: ${it.speed}"
                binding.shotsTv.text = "Shots: ${it.shots}"
            }

        }

        //Start button
        binding.startBtn.setOnClickListener {
            if (viewModel.playerDataLiveData.value == null) {
                Toast.makeText(context, "Select your player", Toast.LENGTH_SHORT).show()
            }else{
                findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
            }
        }

    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item = p0?.getItemAtPosition(p2).toString()
        viewModel.setPlayerData(item)
    }

}