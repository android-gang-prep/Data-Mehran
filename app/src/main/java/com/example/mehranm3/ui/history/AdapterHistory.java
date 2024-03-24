package com.example.mehranm3.ui.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mehranm3.OnClickCustom;
import com.example.mehranm3.databinding.GroupItemBinding;
import com.example.mehranm3.databinding.HistoryItemBinding;
import com.example.mehranm3.models.HistoryModel;
import com.example.mehranm3.models.HistoryModel;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    private List<HistoryModel> list;
    private OnClickCustom<HistoryModel> onClickCustom;
    private OnClickCustom<HistoryModel> onDelete;

    public AdapterHistory(List<HistoryModel> list, OnClickCustom<HistoryModel> onClickCustom, OnClickCustom<HistoryModel> onDelete) {
        this.list = list;
        this.onClickCustom = onClickCustom;
        this.onDelete = onDelete;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryItemBinding binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setModel(list.get(position));
        if (onClickCustom != null)
            holder.itemView.setOnClickListener(v -> onClickCustom.onClick(list.get(position)));
        if (onDelete!=null)
            holder.binding.delete.setOnClickListener(v -> onDelete.onClick(list.get(position)));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HistoryItemBinding binding;

        public ViewHolder(@NonNull HistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
