package com.example.mehranm3.ui.history;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mehranm3.DongTypes;
import com.example.mehranm3.OnClickCustom;
import com.example.mehranm3.database.entity.HistoryEntity;
import com.example.mehranm3.database.entity.UserModel;
import com.example.mehranm3.databinding.EnterDongsBinding;
import com.example.mehranm3.databinding.GroupItemBinding;
import com.example.mehranm3.databinding.ItemSelectDongBinding;

import java.util.List;

public class AdapterDongs extends RecyclerView.Adapter<AdapterDongs.ViewHolder> {
    private List<UserModel> list;
    private OnClickCustom<UserModel> onClickCustom;
    private OnTotalChange onTotalChange;
    private DongTypes dongType = DongTypes.PERCENT;
    private RecyclerView recyclerView;
    private double total;
    private int part = 10;

    public interface OnTotalChange {
        void onChange(double total);
    }

    public AdapterDongs(List<UserModel> list, RecyclerView recyclerView, OnClickCustom<UserModel> onClickCustom, OnTotalChange onTotalChange) {
        this.list = list;
        this.recyclerView = recyclerView;
        this.onClickCustom = onClickCustom;
        this.onTotalChange = onTotalChange;
    }

    public void setTotal(double total) {
        this.total = total;
        notifyDataSetChanged();
    }

    public void setPart(int part) {
        this.part = part;
        notifyDataSetChanged();
    }

    public void setDongType(DongTypes dongType) {
        this.dongType = dongType;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterDongs.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSelectDongBinding binding = ItemSelectDongBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDongs.ViewHolder holder, int position) {
        UserModel userModel = list.get(position);
        holder.binding.setModel(userModel);

        holder.binding.dong.clearTextChangedListeners();
        if (onClickCustom != null)
            holder.itemView.setOnClickListener(v -> onClickCustom.onClick(userModel));

        TextWatcher textWatcher = new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (holder.binding.dong.getText().toString().trim().isEmpty()) {
                    holder.binding.dong.setText("0");
                    holder.binding.dong.setSelection(1);
                    return;
                }
                if (!holder.binding.dong.getText().toString().trim().startsWith("0.") && holder.binding.dong.getText().toString().trim().length() > 1 && holder.binding.dong.getText().toString().trim().startsWith("0")) {
                    holder.binding.dong.setText(holder.binding.dong.getText().toString().trim().substring(1, holder.binding.dong.getText().toString().trim().length()));
                    holder.binding.dong.setSelection(1);
                    return;
                }
                if (dongType == DongTypes.DONG) {
                    userModel.setDong(Double.parseDouble(holder.binding.dong.getText().toString().trim()));
                } else if (dongType == DongTypes.PERCENT) {
                    if (Double.parseDouble(holder.binding.dong.getText().toString().trim()) > 100) {
                        holder.binding.dong.setText("100");
                        holder.binding.dong.setSelection(3);
                        return;
                    }
                    userModel.setDong(Double.parseDouble(holder.binding.dong.getText().toString().trim()) * total / 100);
                } else if (dongType == DongTypes.PART) {
                    if (Double.parseDouble(holder.binding.dong.getText().toString().trim()) > part) {
                        holder.binding.dong.setText(String.valueOf(part));
                        holder.binding.dong.setSelection(holder.binding.dong.length());
                        return;
                    }
                    userModel.setDong(Double.parseDouble(holder.binding.dong.getText().toString().trim()) * total / part);
                }

                double totalUser = 0;

                for (int i = 0; i < list.size(); i++) {
                    totalUser += list.get(i).getDong();
                }

                onTotalChange.onChange(totalUser);

            }
        };


        holder.binding.checkBox.setChecked(userModel.isSelected());
        holder.binding.dong.setText("0");

        if (dongType == DongTypes.DONG) {
            holder.binding.dong.setText(String.valueOf(userModel.getDong()));
            holder.binding.textInput.setHint("Enter Price");
        } else if (dongType == DongTypes.PERCENT) {
            holder.binding.dong.setText(String.format("%.2f", userModel.getDong() * 100 / total));
            holder.binding.textInput.setHint("Enter Percent");
        } else if (dongType == DongTypes.PART) {

            holder.binding.dong.setText(String.format("%.2f", userModel.getDong() * part / total));
            holder.binding.textInput.setHint("Enter Part");
        }
        holder.binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            userModel.setSelected(isChecked);
            if (!isChecked)
                holder.binding.dong.setText("0");
        });

        holder.binding.dong.addTextChangedListener(textWatcher);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSelectDongBinding binding;

        public ViewHolder(@NonNull ItemSelectDongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
