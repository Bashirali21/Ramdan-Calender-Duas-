package com.example.ramzancalender.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ramzancalender.LoadingDialog
import com.example.ramzancalender.R
import com.example.ramzancalender.databinding.FragmentHomeBinding
import com.example.ramzancalender.extractTime
import com.example.ramzancalender.getCity
import com.example.ramzancalender.getLatLng
import com.example.ramzancalender.helper.Resource
import com.example.ramzancalender.viewmodel.MainAppViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


class HomeFragment : Fragment() {
    val viewModel by activityViewModels<MainAppViewModel>()
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadingDialog = LoadingDialog(requireContext())
        val sharedPreferences = requireActivity().getSharedPreferences("main", Context.MODE_PRIVATE)
        val location = sharedPreferences.getLatLng()
        if (location.first == 0.0) {
            findNavController().navigate(R.id.action_homeFragment_to_viewPagerFragment)
        }
        binding.tvAddress.setText(sharedPreferences.getCity())
        binding.layoutMasnonDuas.image.setImageResource(R.drawable.pray)
        binding.layoutMasnonDuas.tvDesc.setText("Masnoon Duas")

        binding.layoutFullCalender.image.setImageResource(R.drawable.ramadan)
        binding.layoutFullCalender.tvDesc.setText("Full Calender")

        binding.layoutTasbeeh.image.setImageResource(R.drawable.ic_count)
        binding.layoutTasbeeh.tvDesc.setText("Iftar Counter")

        binding.layoutFullCalender.root.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_ramzanCalenderFragment)
        }

        binding.layoutMasnonDuas.root.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_duasCategoriesFragment)
        }
        binding.layoutTasbeeh.root.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_countDownFragment)
        }

        binding.layoutPrayerTimes.root.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_nimazTimingAdapter)
        }
        lifecycleScope.launch {
            viewModel.calenderData.collect {
                when (it) {
                    is Resource.Loading -> {
                        if (location.first != 0.0) {
                            loadingDialog.show()
                        }

                    }

                    is Resource.Success -> {
                        loadingDialog.dismiss()
                        val currentDate =
                            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                                java.util.Date()
                            )
                        val filteredModel =
                            viewModel.combinedList.value.find { it.currentData == currentDate }
                        filteredModel?.let {
                            viewModel.currentObj = it
                            binding.textView3.text = it.currentData
                            binding.tvSehri.text = extractTime(it.sehri) + " (Am)"
                            binding.tvIftari.text = extractTime(it.iFtari) + " (Pm)"
                            binding.textView4.text = it.hirjriDate
                        }


                    }

                    is Resource.Error -> {
                        loadingDialog.dismiss()
                        Log.d("Status", it.error!!.message!!)
                    }
                }
            }
        }

    }


}