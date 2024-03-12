package com.example.ramzancalender.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramzancalender.adapters.DuaAdapter
import com.example.ramzancalender.databinding.FragmentDuaBinding
import com.example.ramzancalender.models.Dua
import com.example.ramzancalender.helper.JsonReader

class DuaFragment : Fragment() {
    private lateinit var ramzanDataAdapter: DuaAdapter
    lateinit var binding: FragmentDuaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDuaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jsonReader = JsonReader(requireActivity())
        var fileName = ""
        val key = arguments?.getString("category")
        when (key) {
            "Daily Duas" -> {
                fileName = "dailydua.json"
            }

            "Evening Duas" -> {
                fileName = "evening_dikar.json"
            }

            "Morning Duas" -> {
                fileName = "morning_dua.json"
            }

            "Salah Duas" -> {
                fileName = "salahdua.json"
            }
        }

        val jsonArray = jsonReader.readJsonFile(fileName)
        val duaList = mutableListOf<Dua>()
        if (jsonArray != null) {
            for (i in 0 until jsonArray.length()) {
                val duaObject = jsonArray.getJSONObject(i)
                val dua = Dua(
                    duaObject.getString("title"),
                    duaObject.getString("arabic"),
                    duaObject.getString("latin"),
                    duaObject.getString("translation"),
                    duaObject.optString("notes", null),
                    duaObject.getString("fawaid"),
                    duaObject.getString("source")
                )
                duaList.add(dua)
            }
        }
        ramzanDataAdapter = DuaAdapter(duaList)
        binding.rec.apply {
            adapter = ramzanDataAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }


    }

}