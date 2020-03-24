package com.razbyte.architectureexample.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseSingleTypeRecyclerAdapter<T0, T1 : ViewDataBinding>(var data: List<T0>) :
    RecyclerView.Adapter<BaseSingleTypeRecyclerAdapter.LocalViewHolder<T1>>() {

    // Override with row layout
    abstract fun provideLayout(): Int

    // View binding logic
    abstract fun bindView(binding: T1, itemData: T0, position: Int)

    // Items comparator
    abstract val compareAreItemsTheSame: (oldItem: T0, newItem: T0) -> Boolean

    var onItemClick: ((itemClicked: T0) -> Unit)? = null

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder<T1> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<T1>(inflater, provideLayout(), parent, false)
        return LocalViewHolder(binding as T1)
    }

    override fun onBindViewHolder(holder: LocalViewHolder<T1>, position: Int) {
        holder.binding.root.setOnClickListener { onItemClick?.invoke(data[position]) }
        bindView(holder.binding, data[position], position)
    }

    class LocalViewHolder<T1 : ViewDataBinding>(val binding: T1) :
        RecyclerView.ViewHolder(binding.root)

    fun update(newData: List<T0>) {
        val diffCallback = ItemDiffCallback<T0>(data, newData, compareAreItemsTheSame)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    private class ItemDiffCallback<T>(
        private val old: List<T>,
        private val new: List<T>,
        private val compareAreItemsTheSame: (oldItem: T, newItem: T) -> Boolean
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = old.size
        override fun getNewListSize() = new.size

        override fun areItemsTheSame(oldIndex: Int, newIndex: Int): Boolean {
            return compareAreItemsTheSame(old[oldIndex], new[newIndex])
        }

        override fun areContentsTheSame(oldIndex: Int, newIndex: Int): Boolean {
            return old[oldIndex] == new[newIndex]
        }
    }

}