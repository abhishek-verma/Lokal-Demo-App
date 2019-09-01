package com.abhi.lokaldemo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abhi.lokaldemo.R
import com.abhi.lokaldemo.api.model.PostResponse
import com.abhi.lokaldemo.databinding.PostListItemBinding
import com.abhi.lokaldemo.ui.main.callback.PostClickCallback


class PostListAdapter(
    val projectPostClickCallback: PostClickCallback
) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {

    var postList: List<PostResponse>? = null

    fun setProjectList(postList: List<PostResponse>) {
        if (this.postList == null) {
            this.postList = postList
            notifyItemRangeInserted(0, postList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return postList.size
                }

                override fun getNewListSize(): Int {
                    return postList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return postList[oldItemPosition].id == postList[newItemPosition].id
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val post = postList[newItemPosition]
                    val old = postList[oldItemPosition]
                    return post.id == old.id
                }
            })
            this.postList = postList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding: PostListItemBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context), R.layout.post_list_item,
                parent, false
            )

        binding.callback = projectPostClickCallback

        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.post = postList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return postList?.size ?: 0
    }

    class PostViewHolder(val binding: PostListItemBinding) :
        RecyclerView.ViewHolder(binding.getRoot())
}
