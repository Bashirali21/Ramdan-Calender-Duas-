package com.example.ramzancalender.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.ramzancalender.R
import com.example.ramzancalender.databinding.FragmentCountDownBinding
import com.example.ramzancalender.extractTime
import com.example.ramzancalender.viewmodel.MainAppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar


class CountDownFragment : Fragment() {
    val viewModel by activityViewModels<MainAppViewModel>()
    lateinit var binding: FragmentCountDownBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCountDownBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            updateCountdown()
        }

    }

    private suspend fun updateCountdown() {
        viewModel.currentObj?.let {
            val timeStr = extractTime(it.iFtari)
            val parts = timeStr.split(":")

            val targetTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, parts[0].toInt())
                set(Calendar.MINUTE, parts[1].toInt())
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            while (true) {
                val remainingTime = calculateRemainingTime(targetTime)
                remainingTime?.let {
                    binding.countdownTextView.text = remainingTime
                } ?: {
                    binding.textView8.text = "Wishing you a happy Iftar!"
                    binding.countdownTextView.text = "Time has elapsed"
                }

                delay(1000) // Delay for 1 second
            }
        }

    }


    private fun calculateRemainingTime(targetTime: Calendar): String? {
        val currentTime = Calendar.getInstance()


        // Calculate the difference between current time and target time
        val timeDiffInMillis = targetTime.timeInMillis - currentTime.timeInMillis
        if (timeDiffInMillis <= 0) {
            binding.textView8.text = "Wishing you a happy Iftar!"
            binding.countdownTextView.text = "Time has elapsed"
            return null
        }

        // Convert the time difference to hours, minutes, and seconds
        val hours = timeDiffInMillis / (1000 * 60 * 60)
        val minutes = (timeDiffInMillis % (1000 * 60 * 60)) / (1000 * 60)
        val seconds = ((timeDiffInMillis % (1000 * 60 * 60)) % (1000 * 60)) / 1000

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

}