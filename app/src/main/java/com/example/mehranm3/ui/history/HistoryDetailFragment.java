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
import com.example.mehranm3.databinding.FragmentHistoryBinding;
import com.example.mehranm3.databinding.HistoryDetailBinding;
import com.example.mehranm3.models.DebtModel;
import com.example.mehranm3.models.HistoryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryDetailFragment extends Fragment {
    private HistoryDetailBinding binding;
    HistoryModel historyModels;
    List<DebtModel> list;
    AddHistoryViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddHistoryViewModel.class);
        binding = HistoryDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        AdapterDebt adapter = new AdapterDebt(list);

        binding.rec.setAdapter(adapter);
        binding.rec.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.initHistory(getArguments().getLong("id"));
        viewModel.getHistory().observe(getViewLifecycleOwner(), m -> {
            historyModels = m;
            list.clear();

            for (int i = 0; i < historyModels.getUsers().size(); i++) {
                if (historyModels.getUsers().get(i).getUser().getId() != historyModels.getUser_payed().getId())
                    list.add(new DebtModel(historyModels.getUsers().get(i).user.getName(), historyModels.user_p.getName(), historyModels.getUsers().get(i).userDong.getDong(),historyModels.historyEntity.getUnit()));
            }
            adapter.notifyDataSetChanged();
            binding.setModel(m);
        });

        binding.edit.setOnClickListener(v -> {
            try {
                Bundle bundle = new Bundle();
                bundle.putLong("history_id", getArguments().getLong("id"));
                bundle.putLong("id", (long) historyModels.historyEntity.getGroup_id());
                bundle.putBoolean("edit", true);
                Navigation.findNavController(v).navigate(R.id.action_historyDetailFragment_to_addHistory, bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
