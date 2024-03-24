package com.example.mehranm3.ui.group;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends FragmentStateAdapter {

    List<Fragment> list = new ArrayList<>();

    public GroupAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public GroupAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public GroupAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    public void addFragment(Fragment fragment) {
        list.add(fragment);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
