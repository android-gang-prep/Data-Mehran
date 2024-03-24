package com.example.mehranm3.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mehranm3.R;
import com.example.mehranm3.database.entity.UserDongEntity;
import com.example.mehranm3.databinding.FragmentHistoryBinding;
import com.example.mehranm3.models.DebtModel;
import com.example.mehranm3.models.HistoryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GroupCalculateFragment extends Fragment {
    private FragmentHistoryBinding binding;
    AddHistoryViewModel viewModel;
    List<DebtModel> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddHistoryViewModel.class);
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    AdapterDebt adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        adapter = new AdapterDebt(list);

        binding.rec.setAdapter(adapter);
        binding.rec.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.init(getArguments().getLong("id"));
        viewModel.getGroup().observe(getViewLifecycleOwner(), h -> {
            list.clear();
            List<DebtModel> list2 = new ArrayList<>();
            for (int j = 0; j < h.getHistory().size(); j++) {
                HistoryModel historyModels = h.getHistory().get(j);
                for (int i = 0; i < historyModels.getUsers().size(); i++)
                    if (historyModels.getUsers().get(i).user.getId() != historyModels.user_p.getId()) {
                        DebtModel debtModel = getDebt(list2, historyModels.getUsers().get(i).user.getId(), historyModels.user_p.getId(), historyModels.historyEntity.getUnit());
                        if (debtModel == null)
                            list2.add(new DebtModel(historyModels.getUsers().get(i).user.getName(), historyModels.getUsers().get(i).user.getId(), historyModels.user_p.getName(), historyModels.user_p.getId(), historyModels.getUsers().get(i).userDong.getDong(), historyModels.historyEntity.getUnit()));
                        else {
                            debtModel.dong += historyModels.getUsers().get(i).userDong.getDong();
                        }
                    }
            }

            for (int i = 0; i < list2.size(); i++) {
                DebtModel debtModel=list2.get(i);
                for (int j = 0; j < list2.size(); j++) {
                    DebtModel debtModel2=list2.get(j);
                    if (debtModel.getIdDebtor() == debtModel2.getIdCreditor() && debtModel.getIdCreditor() == debtModel2.getIdDebtor() && debtModel.unit.equals(debtModel2.unit)) {
                        if (debtModel.dong>debtModel2.dong){
                            list2.remove(debtModel2);
                            debtModel.dong-=debtModel2.dong;
                        }else if (debtModel.dong<debtModel2.dong){
                            list2.remove(debtModel);
                            debtModel2.dong-=debtModel.dong;
                        }else if (debtModel.dong==debtModel2.dong){
                            list2.remove(debtModel);
                            list2.remove(debtModel2);
                        }
                    }
                }
            }



            list.addAll(list2);
            adapter.notifyDataSetChanged();
        });

        binding.add.setOnClickListener(v -> {
            try {
                Bundle bundle = new Bundle();
                bundle.putLong("id", getArguments().getLong("id"));
                Navigation.findNavController(v).navigate(R.id.action_groupFragment_to_addHistory, bundle);
            } catch (Exception e) {
            }
        });
    }


    private DebtModel getDebt(List<DebtModel> list, long id, long id2, String unit) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdDebtor() == id && list.get(i).getIdCreditor() == id2 && list.get(i).unit.equals(unit))
                return list.get(i);
        }
        return null;
    }
}
