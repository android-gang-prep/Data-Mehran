package com.example.mehranm3.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mehranm3.R;
import com.example.mehranm3.databinding.FragmentFriendsBinding;
import com.example.mehranm3.databinding.FragmentHistoryBinding;
import com.example.mehranm3.models.HistoryModel;
import com.example.mehranm3.ui.friends.FriendsViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding binding;
    AddHistoryViewModel viewModel;
    List<HistoryModel> historyModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddHistoryViewModel.class);
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    AdapterHistory adapterHistory;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyModels = new ArrayList<>();
         adapterHistory = new AdapterHistory(historyModels, item -> {
            Bundle bundle = new Bundle();
            bundle.putLong("id", item.getHistoryEntity().getId());
            try {
                Navigation.findNavController(getView()).navigate(R.id.action_groupFragment_to_historyDetailFragment, bundle);
            } catch (Exception e) {
            }
        }, item -> {
            viewModel.removeHistory(item);
            for (int i = 0; i < historyModels.size(); i++) {
                if (historyModels.get(i).getHistoryEntity().getId() == item.getHistoryEntity().getId()) {
                    historyModels.remove(item);
                    adapterHistory.notifyItemRemoved(i);
                    return;
                }
            }

        });

        binding.rec.setAdapter(adapterHistory);
        binding.rec.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.init(getArguments().getLong("id"));
        viewModel.getGroup().observe(getViewLifecycleOwner(), groupsModel -> {
            historyModels.clear();
            historyModels.addAll(groupsModel.getHistory());
            Collections.reverse(historyModels);
            adapterHistory.notifyDataSetChanged();
        });

        binding.add.setOnClickListener(v -> {
            try {
                Bundle bundle = new Bundle();
                bundle.putLong("id", getArguments().getLong("id"));
                Navigation.findNavController(v).navigate(R.id.action_groupFragment_to_addHistory, bundle);
            } catch (Exception e) {
            }
        });
    }
}
