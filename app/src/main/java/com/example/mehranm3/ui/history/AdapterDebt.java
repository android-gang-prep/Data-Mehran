package com.example.mehranm3.ui.history;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mehranm3.OnClickCustom;
import com.example.mehranm3.databinding.DebtItemBinding;
import com.example.mehranm3.databinding.UserItemBinding;
import com.example.mehranm3.models.DebtModel;

import java.util.List;

public class AdapterDebt extends RecyclerView.Adapter<AdapterDebt.ViewHolder> {
    private List<DebtModel> list;

    public AdapterDebt(List<DebtModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterDebt.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DebtItemBinding binding = DebtItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDebt.ViewHolder holder, int position) {
        holder.binding.setModel(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DebtItemBinding binding;

        public ViewHolder(@NonNull DebtItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
