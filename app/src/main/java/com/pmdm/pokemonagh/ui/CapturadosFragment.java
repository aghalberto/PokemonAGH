package com.pmdm.pokemonagh.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pmdm.pokemonagh.R;
import com.pmdm.pokemonagh.databinding.FragmentCapturadosBinding;
import com.pmdm.pokemonagh.databinding.FragmentPokedexBinding;
import com.pmdm.pokemonagh.firebase.FirestoneApi;
import com.pmdm.pokemonagh.model.Pokemon;

import java.util.ArrayList;

import kotlinx.serialization.descriptors.PrimitiveKind;

public class CapturadosFragment extends Fragment {

    private FragmentCapturadosBinding binding;
    private ArrayList<Pokemon> capturados;

    public ArrayList<Pokemon> getCapturados() {
        return capturados;
    }

    public void setCapturados(ArrayList<Pokemon> capturados) {
        this.capturados = capturados;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCapturadosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final RecyclerView recyclerView = binding.recyclerCapturados;

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirestoneApi firestoneApi = new FirestoneApi();
                firestoneApi.consultaCapturados();
            }
        });
        //return super.onCreateView(inflater, container, savedInstanceState);
        return  root;
    }



    /**
     * Constructor
     */
    public CapturadosFragment() {
        super(R.layout.fragment_capturados);
        FirestoneApi firestoneApi = new FirestoneApi();
        this.capturados = firestoneApi.getPokemonCapturados();
    }

    public void addPokemon(Pokemon p) {
        if (capturados == null) capturados = new ArrayList<Pokemon>();
        capturados.add(p);
    }
}
