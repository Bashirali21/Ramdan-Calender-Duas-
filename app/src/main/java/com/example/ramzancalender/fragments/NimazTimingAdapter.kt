package com.example.ramzancalender.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramzancalender.R
import com.example.ramzancalender.adapters.NimazTimingAdapter
import com.example.ramzancalender.adapters.RamzanDataAdapter
import com.example.ramzancalender.adapters.RamzanDuaCategoryAdapter
import com.example.ramzancalender.databinding.FragmentDuasCategoriesBinding
import com.example.ramzancalender.databinding.FragmentRamzanCalenderBinding
import com.example.ramzancalender.models.NimazModel
import com.example.ramzancalender.viewmodel.MainAppViewModel

class NimazTimingAdapter : Fragment() {
    private lateinit var ramzanDataAdapter: NimazTimingAdapter
    lateinit var binding: FragmentDuasCategoriesBinding
    val vieModel by activityViewModels<MainAppViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDuasCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val duaList = mutableListOf<NimazModel>()
        vieModel.currentObj?.let {
            duaList.add(NimazModel("Fajr",it.sehri))
            duaList.add(NimazModel("Dhuhr",it.zuharTiming))
            duaList.add(NimazModel("Asar",it.AsarTime))
            duaList.add(NimazModel("Maghrid",it.iFtari))
            duaList.add(NimazModel("Isha",it.ishaTime))
            ramzanDataAdapter = NimazTimingAdapter(duaList, onItemClick = { data ->

            })

            binding.rec.apply {
                adapter = ramzanDataAdapter
                layoutManager = LinearLayoutManager(requireActivity())
            }
        }

    }
}