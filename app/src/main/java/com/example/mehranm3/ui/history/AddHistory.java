package com.example.mehranm3.ui.history;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mehranm3.DongTypes;
import com.example.mehranm3.database.entity.HistoryEntity;
import com.example.mehranm3.database.entity.UserDongEntity;
import com.example.mehranm3.database.entity.UserModel;
import com.example.mehranm3.databinding.DialogAddHistoryBinding;
import com.example.mehranm3.databinding.DialogAddUserBinding;
import com.example.mehranm3.databinding.EnterDongsBinding;
import com.example.mehranm3.databinding.FragmentFriendsBinding;
import com.example.mehranm3.ui.friends.AdapterFriends;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddHistory extends Fragment {

    DialogAddHistoryBinding binding;
    AddHistoryViewModel viewModel;
    private UserModel userModel;
    AdapterFriends adapterFriends;
    AdapterDongs adapterDongs;
    long time;
    List<UserModel> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddHistoryViewModel.class);
        binding = DialogAddHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    String[] units = new String[]{"TOMAN", "DOLLAR", "EURO"};

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = new ArrayList<>();

        adapterFriends = new AdapterFriends(list, false, item -> {
            userModel = item;
            binding.userPayed.setText(userModel.getName());
            dialog.dismiss();
        });

        viewModel.init(getArguments().getLong("id"));
        viewModel.getGroup().observe(getViewLifecycleOwner(), groupsModel -> {
            list.clear();
            list.addAll(groupsModel.getUsers());

            adapterFriends.notifyDataSetChanged();

            if (getArguments().getBoolean("edit"))
                loadHistory();

        });


        binding.selectF.setOnClickListener(v -> selectFriend());

        binding.unit.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, units));

        time = System.currentTimeMillis();
        binding.date.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(time)));

        binding.selectDate.setOnClickListener(v -> {
            Calendar newCalendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(getContext(), (view1, year, month, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                time = calendar.getTimeInMillis();
                binding.date.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(time)));
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

        binding.enterDongs.setOnClickListener(v -> selectDongs());

        binding.divide.setOnCheckedChangeListener((buttonView, isChecked) -> binding.enterDongLa.setVisibility(isChecked ? View.GONE : View.VISIBLE));

        binding.save.setOnClickListener(v -> save());
    }

    private void save() {
        if (userModel == null) {
            Toast.makeText(getContext(), "Please select a payer", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.cost.getText().toString().trim().isEmpty() || Double.parseDouble(binding.cost.getText().toString().trim()) < 1) {
            Toast.makeText(getContext(), "Please enter the cost", Toast.LENGTH_SHORT).show();
            return;
        }

        if (binding.divide.isChecked()) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setDong(Double.parseDouble(binding.cost.getText().toString().trim()) / list.size());
                list.get(i).setSelected(true);
                totalUser = Double.parseDouble(binding.cost.getText().toString().trim());

            }
        }

        if (totalUser > Double.parseDouble(binding.cost.getText().toString().trim())) {
            Toast.makeText(getContext(), "Entered values are greater than the total value", Toast.LENGTH_SHORT).show();
            return;
        }

        if (totalUser < Double.parseDouble(binding.cost.getText().toString().trim())) {
            Toast.makeText(getContext(), "The entered values are less than the total value", Toast.LENGTH_SHORT).show();
            return;
        }

        HistoryEntity historyEntity = new HistoryEntity(getArguments().getLong("id"), Double.parseDouble(binding.cost.getText().toString().trim()), time, units[binding.unit.getSelectedItemPosition()], userModel.getId());
        List<UserDongEntity> userDongEntities = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            boolean add = list.get(i).isSelected() && list.get(i).getDong() != 0;
            if (add)
                userDongEntities.add(new UserDongEntity(list.get(i).getId(), 0, list.get(i).getDong()));
        }

        try {
            if (getArguments().getBoolean("edit")) {
                historyEntity.setId(getArguments().getLong("history_id"));
                viewModel.updateHistory(historyEntity, userDongEntities);
            } else
                viewModel.addHistory(historyEntity, userDongEntities);
            Navigation.findNavController(getView()).popBackStack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHistory() {

        viewModel.initHistory(getArguments().getLong("history_id"));
        viewModel.getHistory().observe(getViewLifecycleOwner(), groupsModel -> {
            totalUser = groupsModel.historyEntity.getTotal();
            binding.dongs.setText("Total: " + totalUser + "/" + totalUser);
            binding.cost.setText(String.valueOf(totalUser));
            binding.date.setText(groupsModel.getTime());
            userModel = groupsModel.user_p;
            binding.userPayed.setText(userModel.getName());

            first = false;
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setSelected(false);
            }

            boolean div = true;
            double lastDong = groupsModel.getUsers().get(0).getUserDong().getDong();
            for (int i = 0; i < groupsModel.getUsers().size(); i++) {
                UserDongEntity userDong = groupsModel.getUsers().get(i).userDong;
                getUser(userDong.getUser_id()).setDong(userDong.getDong());
                getUser(userDong.getUser_id()).setSelected(true);
                if (lastDong != userDong.getDong()) div = false;
            }

            binding.divide.setChecked(div);
            switch (groupsModel.historyEntity.getUnit()) {
                case "TOMAN":
                    binding.unit.setSelection(0);
                    break;
                case "DOLLAR":
                    binding.unit.setSelection(1);
                    break;
                case "EURO":
                    binding.unit.setSelection(2);
                    break;
            }

        });
    }

    private UserModel getUser(long id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) return list.get(i);
        }
        return null;
    }

    Dialog dialog;

    private void selectFriend() {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        FragmentFriendsBinding dialogBinding = FragmentFriendsBinding.inflate(getLayoutInflater());
        dialogBinding.rec.setAdapter(adapterFriends);
        dialogBinding.add.setVisibility(View.GONE);
        dialogBinding.rec.setLayoutManager(new LinearLayoutManager(getContext()));
        builder.setView(dialogBinding.getRoot());
        dialog = builder.create();
        dialog.show();
    }

    private DongTypes[] dongTypes = new DongTypes[]{DongTypes.PERCENT, DongTypes.DONG, DongTypes.PART};
    double totalUser;

    private boolean first = true;
    private int dType = 0;

    private void selectDongs() {

        if (binding.cost.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Please enter the cost first", Toast.LENGTH_SHORT).show();
            return;
        }

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        EnterDongsBinding dialogBinding = EnterDongsBinding.inflate(getLayoutInflater());

        if (first) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setDong(Double.parseDouble(binding.cost.getText().toString().trim()) / list.size());
                list.get(i).setSelected(true);
            }
            totalUser = Double.parseDouble(binding.cost.getText().toString().trim());
        }

        first = false;

        dialogBinding.total.setText("Total: " + totalUser + "/" + Double.parseDouble(binding.cost.getText().toString().trim()));
        binding.dongs.setText("Total: " + totalUser + "/" + Double.parseDouble(binding.cost.getText().toString().trim()));

        adapterDongs = new AdapterDongs(list, dialogBinding.rec, item -> {

        }, total -> {
            totalUser = total;
            dialogBinding.total.setText("Total: " + totalUser + "/" + Double.parseDouble(binding.cost.getText().toString().trim()));
            binding.dongs.setText("Total: " + totalUser + "/" + Double.parseDouble(binding.cost.getText().toString().trim()));
        });
        adapterDongs.setTotal(Double.parseDouble(binding.cost.getText().toString().trim()));
        adapterDongs.setDongType(dongTypes[dType]);

        dialogBinding.rec.setLayoutManager(new LinearLayoutManager(getContext()));
        dialogBinding.rec.setAdapter(adapterDongs);
        adapterDongs.notifyDataSetChanged();
        String[] types = new String[]{"PERCENT", "PRICE", "PART"};
        dialogBinding.type.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, types));
        dialogBinding.type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dType = position;
                adapterDongs.setDongType(dongTypes[position]);
                dialogBinding.textInput.setVisibility(dongTypes[position] == DongTypes.PART ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialogBinding.type.setSelection(dType);
        dialogBinding.part.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dialogBinding.part.removeTextChangedListener(this);
                if (dialogBinding.part.getText().toString().trim().isEmpty() || Integer.parseInt(dialogBinding.part.getText().toString().trim()) < 1)
                    dialogBinding.part.setText("10");

                adapterDongs.setPart(Integer.parseInt(dialogBinding.part.getText().toString().trim()));

                dialogBinding.part.addTextChangedListener(this);

            }
        });
        builder.setView(dialogBinding.getRoot());
        builder.setNegativeButton("Close", null);
        dialog = builder.create();
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


}
