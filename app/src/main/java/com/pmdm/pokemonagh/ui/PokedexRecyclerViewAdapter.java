package com.pmdm.pokemonagh.ui;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;


import static androidx.core.content.ContextCompat.getColor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pmdm.pokemonagh.R;
import com.pmdm.pokemonagh.databinding.PokedexCardviewBinding;
import com.pmdm.pokemonagh.firebase.FirestoneApi;
import com.pmdm.pokemonagh.model.Pokemon;
import com.pmdm.pokemonagh.retrofit.PokemonApiClient;
import com.pmdm.pokemonagh.retrofit.PokemonApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PokedexRecyclerViewAdapter  extends RecyclerView.Adapter<PokedexViewHolder>{

    private final ArrayList<Pokemon> pokemons;
    private final Context context;


    int selectedPosition = -1;

    /**
     * Constructor.
     * @param pokemons
     * @param context
     */
    public PokedexRecyclerViewAdapter(ArrayList<Pokemon> pokemons, Context context) {
        this.pokemons =  pokemons;
        this.context = context;
    }

    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PokedexCardviewBinding binding = PokedexCardviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );

        return new PokedexViewHolder(binding);
    } //Fin onCreateViewHolder

    @Override
    public void onBindViewHolder(@NonNull PokedexViewHolder holder, int position) {
        Pokemon elPokemon = this.pokemons.get(position);
        holder.bind(elPokemon);
        PokedexCardviewBinding cardviewBinding = holder.binding;

        //holder.itemView.setOnClickListener(view -> itemClicked(elPokemon));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (capturarPokemon(elPokemon)) {
                    Toast.makeText(context.getApplicationContext(), R.string.catched, LENGTH_LONG).show();
                    elPokemon.setCapturado(true);
                    //Ponemos la imagen de espalda
                    Picasso.get()
                            .load(elPokemon.getImagenFrontal())
                            .into(cardviewBinding.imagen);
                    cardviewBinding.cardviewPokemon.setCardBackgroundColor(R.color.white);
                    notifyDataSetChanged();
                    notifyItemChanged(holder.getAdapterPosition());
                }
                else {
                    Toast.makeText(context.getApplicationContext(), R.string.catch_error, LENGTH_LONG).show();
                }
            }
        });

        //super.onBindViewHolder(holder, position, payloads);
    } //Fin onBindViewHolder

    private boolean capturarPokemon(Pokemon p){
        Boolean capturado = false;
        FirestoneApi firestoneApi = new FirestoneApi();
        capturado =  firestoneApi.addPokemon(p);
        CapturadosFragment capturadosFragment = new CapturadosFragment();
        capturadosFragment.addPokemon(p);
        return capturado;
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }


}
