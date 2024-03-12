package com.example.ramzancalender.adapters// RamzanDataAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ramzancalender.R
import com.example.ramzancalender.databinding.ItemCalenderBinding
import com.example.ramzancalender.models.FilteredModel

class RamzanDataAdapter(private val dataList: List<FilteredModel>) :
    RecyclerView.Adapter<RamzanDataAdapter.RamzanDataViewHolder>() {
    inner class RamzanDataViewHolder(private val binding: ItemCalenderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FilteredModel) {
            binding.apply {
                val sehri = data.sehri.replace("(GMT)", "Am").trim()
                val iftari = data.iFtari.replace("(GMT)", "pm").trim()
                tvRamzanDay.text = "${data.hirjriDate}"
                tvDate.text = "${data.currentData}"
                sehriTime.text = sehri.trim()
                iftariTime.text = iftari.trim()
            }
            if(data.isCurrentDate){
                binding.root.setCardBackgroundColor(binding.root.context.resources.getColor(R.color.md_theme_primaryContainer))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RamzanDataViewHolder {
        val binding =
            ItemCalenderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RamzanDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RamzanDataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
