package com.example.spacebattle.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebattle.R
import com.example.spacebattle.databinding.FragmentResultBinding
import com.example.spacebattle.viewmodels.ResultViewModel


class ResultFragment : Fragment() {


    private lateinit var binding: FragmentResultBinding
    private val viewModel: ResultViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO here the logic of this fragment

        binding.menuBtn.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_menuFragment)
        }

    }

}