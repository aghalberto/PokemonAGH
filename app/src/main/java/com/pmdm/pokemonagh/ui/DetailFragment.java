package com.pmdm.pokemonagh.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pmdm.pokemonagh.R;
import com.pmdm.pokemonagh.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding fragmentDetailBinding;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
        //fragmentDetailBinding = FragmentDetailBinding.bind(this, container.getRootView(), R.layout.fragment_detail);
        //View root = fragmentDetailBinding.getRoot();
        //return root;
    }


}
