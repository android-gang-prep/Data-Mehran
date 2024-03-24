package com.example.mehranm3.ui.friends;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mehranm3.OnClickCustom;
import com.example.mehranm3.databinding.UserItemBinding;
import com.example.mehranm3.database.entity.UserModel;

import java.util.List;

public class AdapterFriends extends RecyclerView.Adapter<AdapterFriends.ViewHolder> {
    private List<UserModel> list;
    private OnClickCustom<UserModel> onClickCustom;
    private boolean isClickable = false;

    public AdapterFriends(List<UserModel> list, boolean isClickable, OnClickCustom<UserModel> onClickCustom) {
        this.list = list;
        this.onClickCustom = onClickCustom;
        this.isClickable = isClickable;
    }

    @NonNull
    @Override
    public AdapterFriends.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserItemBinding userItemBinding = UserItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(userItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFriends.ViewHolder holder, int position) {
        holder.binding.setModel(list.get(position));

        holder.binding.checkBox.setVisibility(isClickable ? View.VISIBLE : View.GONE);


        if (isClickable && onClickCustom != null) {
            holder.binding.checkBox.setEnabled(!list.get(position).isSelected());
            holder.binding.checkBox.setChecked(list.get(position).isSelected());
            if (!list.get(position).isSelected()) {
                holder.binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> onClickCustom.onClick(list.get(position)));
                holder.itemView.setOnClickListener(v -> holder.binding.checkBox.setChecked(!holder.binding.checkBox.isChecked()));
            }

        } else if (onClickCustom != null) {
            holder.itemView.setOnClickListener(v -> onClickCustom.onClick(list.get(position)));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        UserItemBinding binding;

        public ViewHolder(@NonNull UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
