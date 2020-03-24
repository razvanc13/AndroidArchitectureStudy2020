package com.razbyte.architectureexample.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.razbyte.architectureexample.R
import com.razbyte.architectureexample.data.entities.RepositoryData
import com.razbyte.architectureexample.databinding.MainItemRepositoryBinding

class MainRepositoriesAdapter :
    PagedListAdapter<RepositoryData, MainRepositoriesAdapter.ViewHolder>(RepositoriesDiffCallback) {

    var onItemClick: ((RepositoryData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<MainItemRepositoryBinding>(
            inflater, R.layout.main__item_repository, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name.text = getItem(position)?.name
        holder.binding.fullName.text = getItem(position)?.fullName
        holder.binding.stars.text = getItem(position)?.stargazers.toString()
        holder.binding.root.setOnClickListener {
            getItem(position)?.let { onItemClick?.invoke(it) }
        }
    }

    class ViewHolder(val binding: MainItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val RepositoriesDiffCallback = object : DiffUtil.ItemCallback<RepositoryData>() {
            override fun areItemsTheSame(
                oldItem: RepositoryData,
                newItem: RepositoryData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RepositoryData,
                newItem: RepositoryData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}