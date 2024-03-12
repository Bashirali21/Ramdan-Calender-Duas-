package com.example.ramzancalender.adapters// RamzanDataAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ramzancalender.databinding.ItemCalenderBinding
import com.example.ramzancalender.databinding.ItemDuaCategoryBinding
import com.example.ramzancalender.databinding.ItemNimazBinding
import com.example.ramzancalender.models.Data
import com.example.ramzancalender.models.FilteredModel
import com.example.ramzancalender.models.NimazModel

class NimazTimingAdapter(
    private val dataList: List<NimazModel>,
    val onItemClick: (String) -> Unit
) :
    RecyclerView.Adapter<NimazTimingAdapter.RamzanDataViewHolder>() {

    inner class RamzanDataViewHolder(private val binding: ItemNimazBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NimazModel) {
            binding.apply {
                tvTime.text = data.time
                tvtitle.text = data.nimaz
            }
            binding.root.setOnClickListener {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RamzanDataViewHolder {
        val binding =
            ItemNimazBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RamzanDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RamzanDataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
