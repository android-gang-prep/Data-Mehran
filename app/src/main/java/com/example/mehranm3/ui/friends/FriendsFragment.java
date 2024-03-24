package com.example.mehranm3.ui.friends;

import android.os.Bundle;
import android.util.Log;
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
import com.example.mehranm3.database.AppDatabase;
import com.example.mehranm3.database.entity.GroupsEntity;
import com.example.mehranm3.database.entity.UserModel;
import com.example.mehranm3.databinding.DialogAddUserBinding;
import com.example.mehranm3.databinding.FragmentFriendsBinding;
import com.example.mehranm3.models.GroupModel;
import com.example.mehranm3.models.GroupsModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FriendsFragment extends Fragment {


    private FragmentFriendsBinding binding;
    FriendsViewModel viewModel;
    private List<UserModel> list;
    private AdapterFriends adapter;
    private List<UserModel> selected;
    private List<Long> selectedIds;
    private GroupsModel groupModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(FriendsViewModel.class);
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    int type = 0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        selectedIds = new ArrayList<>();
        type = getArguments().getInt("type", 0);
        binding.add.setImageResource(type == 1 ? R.drawable.ic_baseline_group_add_24 : R.drawable.ic_baseline_person_add_alt_1_24);

        if (type == 2) {
            long[] longs = getArguments().getLongArray("selectedIds");
            for (long aLong : longs) selectedIds.add(aLong);
        }


        if (type == 1 || type == 2) {
            selected = new ArrayList<>();
            adapter = new AdapterFriends(list, true, item -> {
                if (selected.contains(item))
                    selected.remove(item);
                else
                    selected.add(item);

            });
        } else
            adapter = new AdapterFriends(list, false, null);


        if (type == 3) {
            viewModel.init(getArguments().getLong("id"));
            viewModel.getGroup().observe(getViewLifecycleOwner(), userModels -> {
                this.groupModel = userModels;
                list.clear();
                list.addAll(userModels.getUsers());
                adapter.notifyDataSetChanged();
            });
        } else
            viewModel.getUsers().observe(getViewLifecycleOwner(), userModels -> {
                list.clear();
                list.addAll(userModels);
                if (type == 2) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setSelected(selectedIds.contains(list.get(i).getId()));
                    }
                }
                adapter.notifyDataSetChanged();
            });


        binding.add.setOnClickListener(v -> {
            if (type == 0)
                addUser();
            else if (type == 1)
                addGroup();
            else if (type == 2)
                updateGroup();
            else if (type == 3) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 2);
                bundle.putLong("id", getArguments().getLong("id"));
                long[] ids = new long[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    ids[i] = list.get(i).getId();
                }
                bundle.putLongArray("selectedIds", ids);
                try {
                    Navigation.findNavController(getView()).navigate(R.id.action_groupFragment_to_navigation_friends_selectable, bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        binding.rec.setAdapter(adapter);
        binding.rec.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateGroup() {
        if (selected.size() > 0) {
            viewModel.addMembersP(selected, getArguments().getLong("id"));
        }
        try {
            Navigation.findNavController(getView()).popBackStack();
        } catch (Exception e) {
        }
    }

    private void addGroup() {
        if (selected.size() < 2) {
            Toast.makeText(getContext(), "Choose at least 2 friends.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            viewModel.addGroup(new GroupsEntity(getArguments().getString("name")), selected);
            Toast.makeText(getContext(), "Group created successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "The group was not created successfully.", Toast.LENGTH_SHORT).show();
        }

        try {
            Navigation.findNavController(getView()).popBackStack();
        } catch (Exception e) {
        }


    }

    private void addUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        DialogAddUserBinding dialogBinding = DialogAddUserBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());
        builder.setNegativeButton("Close", null);
        builder.setPositiveButton("Add", (dialog, which) -> {
            if (dialogBinding.name.getText().toString().trim().isEmpty()) {
                Toast.makeText(getContext(), "Enter your name.", Toast.LENGTH_SHORT).show();
                return;
            }
            UserModel userModel = new UserModel(dialogBinding.name.getText().toString().trim());
            userModel.setId(viewModel.addUser(userModel));
        });
        builder.show();
    }


}