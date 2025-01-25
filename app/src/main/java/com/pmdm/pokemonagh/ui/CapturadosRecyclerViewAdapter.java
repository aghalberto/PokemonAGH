package com.pmdm.pokemonagh.ui;

import static android.widget.Toast.LENGTH_LONG;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pmdm.pokemonagh.R;
import com.pmdm.pokemonagh.databinding.CapturadosCardviewBinding;
import com.pmdm.pokemonagh.firebase.FirestoneApi;
import com.pmdm.pokemonagh.model.Pokemon;

import java.util.ArrayList;

public class CapturadosRecyclerViewAdapter extends RecyclerView.Adapter<CapturadosViewHolder>{

    private final ArrayList<Pokemon> pokemons;

    private final Context context;

    int selectedPosition = -1;

    public CapturadosRecyclerViewAdapter(ArrayList<Pokemon> pokemons, Context context) {
        this.pokemons = pokemons;
        this.context = context;
    }

    @NonNull
    @Override
    public CapturadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CapturadosCardviewBinding binding = CapturadosCardviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );

        return new CapturadosViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CapturadosViewHolder holder, int position) {
        Pokemon pkm = this.pokemons.get(position);
        holder.bind(pkm);
        CapturadosCardviewBinding cardviewBinding = holder.binding;

        //holder.itemView.setOnClickListener(view -> itemClicked(elPokemon));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context.getApplicationContext(), "ITEM", LENGTH_LONG).show();
                goToDetail(pkm, v);
            }
        });

        cardviewBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean borrado = borrarPokemon(pkm);
                if (borrado) {
                    pokemons.remove(pkm);
                    Toast.makeText(context.getApplicationContext(), R.string.deleted, LENGTH_LONG).show();
                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                }

            }
        });

    }

    /**
     * Navega al fragmento detalle con el pokemon seleccionado
     * @param p El Pokemon
     * @param view La Vista
     */
    private void goToDetail(Pokemon p, View view) {
        ((MainActivity) context).personajeClicked(p, view);
    }

    /**
     * Borra el pokemon
     * @param pkm
     * @return
     */
    private boolean borrarPokemon(Pokemon pkm) {
        boolean borrado = false;
        FirestoneApi firestoneApi = new FirestoneApi();
        borrado = firestoneApi.borrarPokemon(pkm);
        return borrado;

    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }


}
