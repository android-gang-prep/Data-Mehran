package com.example.mehranm3.ui.group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mehranm3.databinding.GroupFragmentBinding;
import com.example.mehranm3.ui.friends.FriendsFragment;
import com.example.mehranm3.ui.history.AddHistory;
import com.example.mehranm3.ui.history.GroupCalculateFragment;
import com.example.mehranm3.ui.history.HistoryFragment;
import com.google.android.material.tabs.TabLayoutMediator;

public class GroupFragment extends Fragment {

    GroupFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GroupFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GroupAdapter adapter = new GroupAdapter(getChildFragmentManager(), getLifecycle());
        Bundle args = new Bundle();
        args.putLong("id", getArguments().getLong("id"));
        GroupCalculateFragment groupCalculateFragment = new GroupCalculateFragment();
        groupCalculateFragment.setArguments(args);
        adapter.addFragment(groupCalculateFragment);

        HistoryFragment historyFragment = new HistoryFragment();
        historyFragment.setArguments(args);
        adapter.addFragment(historyFragment);

        args.putInt("type", 3);
        args.putLongArray("selectedIds", new long[]{});
        FriendsFragment friends = new FriendsFragment();
        friends.setArguments(args);
        adapter.addFragment(friends);
        binding.viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.viewPager.setAdapter(adapter);

        String[] names = new String[]{"Calculate","History","Friends"};
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> tab.setText(names[position])).attach();

    }
}
