package com.abhi.lokaldemo.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi.lokaldemo.R;
import com.abhi.lokaldemo.api.model.PostResponse;
import com.abhi.lokaldemo.databinding.PostListItemBinding;
import com.abhi.lokaldemo.ui.main.callback.PostClickCallback;

import java.util.List;
import java.util.Objects;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {


    List<? extends PostResponse> postList;

    @Nullable
    private final PostClickCallback postClickCallback;

    public PostListAdapter(@Nullable PostClickCallback postClickCallback) {
        this.postClickCallback = postClickCallback;
    }


    public void setProjectList(final List<? extends PostResponse> postList) {
        if (this.postList == null) {
            this.postList = postList;
            notifyItemRangeInserted(0, postList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return PostListAdapter.this.postList.size();
                }

                @Override
                public int getNewListSize() {
                    return postList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return PostListAdapter.this.postList.get(oldItemPosition).id ==
                            postList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    PostResponse newPost = postList.get(newItemPosition);
                    PostResponse oldPost = postList.get(oldItemPosition);
                    return newPost.id == oldPost.id;
                }
            });
            this.postList = postList;
            result.dispatchUpdatesTo(this);
        }
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.post_list_item,
                        parent, false);

        binding.setCallback(postClickCallback);

        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.binding.setPost(postList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    static class PostViewHolder extends RecyclerView.ViewHolder {

        final PostListItemBinding binding;

        public PostViewHolder(PostListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
