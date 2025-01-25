package com.pmdm.pokemonagh.ui;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pmdm.pokemonagh.model.Pokemon;

import java.util.ArrayList;

public class CapturadosRecyclerViewAdapter extends RecyclerView.Adapter<PokedexViewHolder>{

    private final ArrayList<Pokemon> pokemons;

    private final Context context;

    int selectedPosition = -1;

    public CapturadosRecyclerViewAdapter(ArrayList<Pokemon> pokemons, Context context) {
        this.pokemons = pokemons;
        this.context = context;
    }

    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }


}
