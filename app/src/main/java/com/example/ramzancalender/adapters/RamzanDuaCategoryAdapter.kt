package com.example.ramzancalender.adapters// RamzanDataAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ramzancalender.databinding.ItemCalenderBinding
import com.example.ramzancalender.databinding.ItemDuaCategoryBinding
import com.example.ramzancalender.models.Data
import com.example.ramzancalender.models.FilteredModel

class RamzanDuaCategoryAdapter(private val dataList: List<String>,val onItemClick:(String)->Unit) :
    RecyclerView.Adapter<RamzanDuaCategoryAdapter.RamzanDataViewHolder>() {

    inner class RamzanDataViewHolder(private val binding: ItemDuaCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.apply {
             textView.text=data
            }
            binding.root.setOnClickListener{
                onItemClick.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RamzanDataViewHolder {
        val binding =
            ItemDuaCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RamzanDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RamzanDataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
