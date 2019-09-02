package com.abhi.lokaldemo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.abhi.lokaldemo.R;
import com.abhi.lokaldemo.api.model.PostResponse;
import com.abhi.lokaldemo.databinding.MainFragmentBinding;
import com.abhi.lokaldemo.ui.main.adapter.PostListAdapter;
import com.abhi.lokaldemo.ui.main.callback.PostClickCallback;
import com.abhi.lokaldemo.viewmodel.main.PostListViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MainFragment extends DaggerFragment {

    private PostListAdapter postListAdapter;
    private MainFragmentBinding binding;

    @Inject
    public PostListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);

        postListAdapter = new PostListAdapter(postClickCallback);
        binding.projectList.setAdapter(postListAdapter);
        binding.setIsLoading(true);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        observeViewModel(viewModel);
    }

    private void observeViewModel(final PostListViewModel viewModel) {
        // Observe project data
        viewModel.getPostListObservable().observe(this, new Observer<List<PostResponse>>() {
            @Override
            public void onChanged(@Nullable List<PostResponse> projects) {
                if (projects != null) {
                    binding.setIsLoading(false);
                    postListAdapter.setProjectList(projects);
                }
            }
        });
    }

    private final PostClickCallback postClickCallback = new PostClickCallback() {
        @Override
        public void onClick(PostResponse post) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).downloadImage(post);
            }
        }
    };
}
