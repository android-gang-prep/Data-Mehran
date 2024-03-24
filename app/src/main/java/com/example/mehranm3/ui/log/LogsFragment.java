package com.example.mehranm3.ui.log;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mehranm3.R;
import com.example.mehranm3.database.entity.LogEntity;
import com.example.mehranm3.databinding.DialogAddUserBinding;
import com.example.mehranm3.databinding.FragmentGroupsBinding;
import com.example.mehranm3.models.GroupsModel;
import com.example.mehranm3.ui.groups.AdapterGroups;

import java.util.ArrayList;
import java.util.List;

public class LogsFragment extends Fragment {

    private FragmentGroupsBinding binding;
    LogsViewModel viewModel;
    List<LogEntity> list;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(LogsViewModel.class);
        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        AdapterLogs adapter = new AdapterLogs(list);

        viewModel.getLogs().observe(getViewLifecycleOwner(), groupsModels -> {
            list.clear();
            list.addAll(groupsModels);
            adapter.notifyDataSetChanged();
        });

        binding.rec.setAdapter(adapter);
        binding.rec.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.add.setVisibility(View.GONE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}