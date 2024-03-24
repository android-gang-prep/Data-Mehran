package com.example.mehranm3.ui.groups;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mehranm3.OnClickCustom;
import com.example.mehranm3.database.entity.UserModel;
import com.example.mehranm3.databinding.GroupItemBinding;
import com.example.mehranm3.databinding.UserItemBinding;
import com.example.mehranm3.models.GroupModel;
import com.example.mehranm3.models.GroupsModel;

import java.util.List;

public class AdapterGroups extends RecyclerView.Adapter<AdapterGroups.ViewHolder> {
    private List<GroupsModel> list;
    private OnClickCustom<GroupsModel> onClickCustom;

    public AdapterGroups(List<GroupsModel> list, OnClickCustom<GroupsModel> onClickCustom) {
        this.list = list;
        this.onClickCustom = onClickCustom;
    }

    @NonNull
    @Override
    public AdapterGroups.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupItemBinding binding = GroupItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGroups.ViewHolder holder, int position) {
        holder.binding.setModel(list.get(position));
        if (onClickCustom != null)
            holder.itemView.setOnClickListener(v -> onClickCustom.onClick(list.get(position)));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        GroupItemBinding binding;

        public ViewHolder(@NonNull GroupItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
