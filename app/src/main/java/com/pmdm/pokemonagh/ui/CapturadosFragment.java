package com.pmdm.pokemonagh.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    private CapturadosRecyclerViewAdapter adapter;

    public ArrayList<Pokemon> getCapturados() {
        return capturados;
    }

    public void setCapturados(ArrayList<Pokemon> capturados) {
        this.capturados = capturados;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //FirestoneApi firestoneApi = new FirestoneApi();
        binding = FragmentCapturadosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final RecyclerView recyclerView = binding.recyclerCapturados;

        //Vamos a crear 3 o 4 pokemon
        //addPokemon(new Pokemon ("squirtel", "https://pokeapi.co/api/v2/pokemon/7/"));
        addPokemon(new Pokemon("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"));

        //capturados = consultaCapturados(firestoneApi);
        ((MainActivity)getActivity()).setPkmCapturados(capturados);

        adapter = new CapturadosRecyclerViewAdapter(capturados, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        binding.recyclerCapturados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetail();
            }
        });

        //return super.onCreateView(inflater, container, savedInstanceState);
        return root;

    }

    /**
     * Constructor
     */
    public CapturadosFragment() {
        super(R.layout.fragment_capturados);
        //Si no lo llamo así y lo pongo con una sola línea, no va
        FirestoneApi firestoneApi = new FirestoneApi();
        this.capturados = firestoneApi.getPokemonCapturados();
        //capturados = consultaCapturados();
    }

    /**
     *
     */
    public void goToDetail() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_detail, DetailFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // Name can be null
                .commit();
    } //Fin de goToDetail

    public ArrayList<Pokemon> consultaCapturados(FirestoneApi firestoneApi) {
        return (firestoneApi.getPokemonCapturados());
    }

    public void addPokemon(Pokemon p) {
        if (capturados == null) capturados = new ArrayList<Pokemon>();
        capturados.add(p);
    }
}
