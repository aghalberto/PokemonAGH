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
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding fragmentDetailBinding;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false);
        return fragmentDetailBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        // Obtener datos del argumento que inicia este fragmento
        if (getArguments() != null) {

            String name = getArguments().getString("textViewNombre");
            String imagen = getArguments().getString("detail_imagen");
            String weight = getArguments().getString("weight");
            String height = getArguments().getString("height");

            // Asignar los datos a los componentes
            Picasso.get()
                    .load(imagen)
                    .into(fragmentDetailBinding.detailImagen);
            fragmentDetailBinding.textViewNombre.setText(name.toUpperCase());
            fragmentDetailBinding.weight.setText(weight);
            fragmentDetailBinding.height.setText(height);
        }
    } //fin onViewCreated
}
