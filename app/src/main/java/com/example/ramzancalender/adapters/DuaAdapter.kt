package com.example.ramzancalender.adapters// RamzanDataAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ramzancalender.databinding.DuaItemBinding
import com.example.ramzancalender.databinding.ItemCalenderBinding
import com.example.ramzancalender.models.Data
import com.example.ramzancalender.models.Dua
import com.example.ramzancalender.models.FilteredModel

class DuaAdapter(private val dataList: List<Dua>) :
    RecyclerView.Adapter<DuaAdapter.RamzanDataViewHolder>() {

    inner class RamzanDataViewHolder(private val binding: DuaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Dua) {
            binding.apply {
                tvTitle.text = data.title
                tvDua.text = data.arabic
                tvLatin.text = data.latin
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RamzanDataViewHolder {
        val binding =
            DuaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RamzanDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RamzanDataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
