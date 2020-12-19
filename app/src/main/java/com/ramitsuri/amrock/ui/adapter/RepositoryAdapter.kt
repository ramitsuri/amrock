package com.ramitsuri.amrock.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ramitsuri.amrock.R
import com.ramitsuri.amrock.databinding.RepositoryItemBinding
import com.ramitsuri.amrock.entities.RepositoryInfo
import com.ramitsuri.amrock.utils.DateTimeHelper

class RepositoryAdapter(
    private val list: MutableList<RepositoryInfo>,
    private val dateTimeHelper: DateTimeHelper,
    private val dateTimeFormat: String
) :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    var onItemClick: ((RepositoryInfo) -> Unit)? = null

    fun update(newList: List<RepositoryInfo>) {
        list.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            RepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value: RepositoryInfo = list[position]
        holder.bind(value, position % 2 == 0)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val binding: RepositoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.container.setOnClickListener {
                onItemClick?.invoke(list[adapterPosition])
            }
        }

        fun bind(item: RepositoryInfo, shadeBackground: Boolean) {
            binding.apply {
                textDate.text = dateTimeHelper.format(item.date, dateTimeFormat)
                textName.text = item.name
                textDescription.text = item.description
                if (shadeBackground) {
                    container.setBackgroundColor(
                        ContextCompat.getColor(
                            container.context,
                            R.color.grey
                        )
                    )
                } else {
                    container.setBackgroundColor(
                        ContextCompat.getColor(
                            container.context,
                            android.R.color.transparent
                        )
                    )
                }
            }
        }
    }
}