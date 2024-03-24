package com.example.mehranm3.ui.groups;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mehranm3.R;
import com.example.mehranm3.database.entity.UserModel;
import com.example.mehranm3.databinding.DialogAddUserBinding;
import com.example.mehranm3.databinding.FragmentGroupsBinding;
import com.example.mehranm3.models.GroupsModel;

import java.util.ArrayList;
import java.util.List;

public class GroupsFragment extends Fragment {

    private FragmentGroupsBinding binding;
    GroupsViewModel viewModel;
    List<GroupsModel> list;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(GroupsViewModel.class);

        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        AdapterGroups adapter = new AdapterGroups(list, item -> {
            Bundle bundle = new Bundle();
            bundle.putLong("id", item.getGroup().getId());
            try {
                Navigation.findNavController(getView()).navigate(R.id.action_navigation_groups_to_groupFragment, bundle);
            }catch (Exception e){
            }
        });

        viewModel.getGroups().observe(getViewLifecycleOwner(), groupsModels -> {
            list.clear();
            list.addAll(groupsModels);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getHistory().size()>0){
                    for (int j = 0; j < list.get(i).getHistory().size(); j++) {
                    }
                }
            }
            adapter.notifyDataSetChanged();
        });

        binding.rec.setAdapter(adapter);
        binding.rec.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.add.setOnClickListener(v -> addGroup());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        DialogAddUserBinding dialogBinding = DialogAddUserBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());
        dialogBinding.textInput.setHint("Enter name group");
        builder.setNegativeButton("Close", null);
        builder.setPositiveButton("Add", (dialog, which) -> {
            if (dialogBinding.name.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "Enter name group.", Toast.LENGTH_SHORT).show();
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString("name", dialogBinding.name.getText().toString().trim());
            bundle.putLongArray("selectedIds", new long[]{});
            try {
                Navigation.findNavController(getView()).navigate(R.id.action_navigation_groups_to_navigation_friends_selectable, bundle);
            }catch (Exception e){
            }
        });
        builder.show();
    }
}