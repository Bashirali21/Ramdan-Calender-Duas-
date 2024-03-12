package com.example.ramzancalender.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramzancalender.adapters.RamzanDataAdapter
import com.example.ramzancalender.databinding.FragmentRamzanCalenderBinding
import com.example.ramzancalender.helper.Resource
import com.example.ramzancalender.viewmodel.MainAppViewModel
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RamzanCalenderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RamzanCalenderFragment : Fragment() {
    val viewModel by activityViewModels<MainAppViewModel>()
    private lateinit var ramzanDataAdapter: RamzanDataAdapter
    lateinit var binding: FragmentRamzanCalenderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRamzanCalenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.calenderData.collect {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("Status", "Loading")
                    }

                    is Resource.Success -> {
                        binding.progressCircular.visibility = View.GONE

                        ramzanDataAdapter = RamzanDataAdapter(
                            viewModel.combinedList.value.filter { it.hirjriDate.contains("Ramaḍān") }.sortedBy { it.number }.distinctBy { it.number },
                        )
                        binding.recyclerview.apply {
                            adapter = ramzanDataAdapter
                            layoutManager = LinearLayoutManager(requireActivity())
                        }

                    }

                    is Resource.Error -> {
                        binding.progressCircular.visibility = View.GONE
                        Log.d("Status", it.error!!.message!!)
                    }
                }
            }
        }
    }


}

const val vaule = "Ramaḍān"