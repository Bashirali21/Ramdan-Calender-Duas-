package com.example.ramzancalender.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramzancalender.R
import com.example.ramzancalender.adapters.RamzanDataAdapter
import com.example.ramzancalender.adapters.RamzanDuaCategoryAdapter
import com.example.ramzancalender.databinding.FragmentDuasCategoriesBinding
import com.example.ramzancalender.databinding.FragmentRamzanCalenderBinding

class DuasCategoriesFragment : Fragment() {
    private lateinit var ramzanDataAdapter: RamzanDuaCategoryAdapter
    lateinit var binding: FragmentDuasCategoriesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDuasCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val duaList = mutableListOf<String>()
        duaList.add("Daily Duas")
        duaList.add("Evening Duas")
        duaList.add("Morning Duas")
        duaList.add("Salah Duas")
        ramzanDataAdapter = RamzanDuaCategoryAdapter(duaList, onItemClick = { data ->
            val bundle = Bundle()
            bundle.putString("category", data)
            findNavController().navigate(R.id.action_duasCategoriesFragment_to_duaFragment,bundle)
        })

        binding.rec.apply {
            adapter = ramzanDataAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }
}