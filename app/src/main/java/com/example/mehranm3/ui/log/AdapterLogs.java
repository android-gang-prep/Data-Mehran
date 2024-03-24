package com.example.mehranm3.ui.log;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mehranm3.OnClickCustom;
import com.example.mehranm3.databinding.GroupItemBinding;
import com.example.mehranm3.database.entity.LogEntity;
import com.example.mehranm3.databinding.LogItemBinding;

import java.util.List;

public class AdapterLogs extends RecyclerView.Adapter<AdapterLogs.ViewHolder> {
    private List<LogEntity> list;

    public AdapterLogs(List<LogEntity> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterLogs.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogItemBinding binding = LogItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLogs.ViewHolder holder, int position) {
        holder.binding.setModel(list.get(position));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LogItemBinding binding;

        public ViewHolder(@NonNull LogItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
