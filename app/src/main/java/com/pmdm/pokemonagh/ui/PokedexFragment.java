package com.pmdm.pokemonagh.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pmdm.pokemonagh.R;

import com.pmdm.pokemonagh.databinding.FragmentPokedexBinding;
import com.pmdm.pokemonagh.firebase.FirestoneApi;
import com.pmdm.pokemonagh.model.Pokemon;
import com.pmdm.pokemonagh.retrofit.PokemonApiClient;
import com.pmdm.pokemonagh.retrofit.PokemonApiInterface;
import com.pmdm.pokemonagh.retrofit.PokemonList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexFragment extends Fragment {

    private FragmentPokedexBinding binding;
    private PokedexRecyclerViewAdapter adapter;
    private ArrayList<Pokemon> pokedex;
    private ArrayList<Pokemon> pkCapturados = new ArrayList<Pokemon>();

    public void setPokedex(ArrayList<Pokemon> pokedex) {
        this.pokedex = getPokedex("10");//pokedex;
    }

    private PokemonApiInterface pkmInterface = PokemonApiClient.getClient().create(PokemonApiInterface.class);
    private FirestoneApi firestoneApi = new FirestoneApi();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentPokedexBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final RecyclerView recyclerView = binding.recyclerPokedex;

        //Consultamos la preferencia de límite
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String limite = sharedPreferences.getString("edit_text_limit", "50");

        //llamamos ApiPokemon para obtener la pokedex, y se la pasamos al MainActivity
        pokedex = getPokedex(limite);
        ((MainActivity)getActivity()).setPkmPokedex(pokedex);

        //Intentamos obtener los pokemon capturados
        pkCapturados = ((MainActivity)getActivity()).getPkmCapturados();

        boolean capturado = false;

        for (Pokemon pPokedex : pokedex){
            capturado = firestoneApi.pokemonEstaCapturado(pPokedex.getNombre());
            if (capturado) pPokedex.setCapturado(true);
        }

        if (pokedex != null && pokedex.size() > 0) {
            adapter = new PokedexRecyclerViewAdapter(pokedex, getActivity());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
        }

        return root;
    }//fin onCreateView

    @Override
    public void onStart() {
        super.onStart();
        //pokedex.clear(); pokedex = getPokedex("10");

    }

    /**
     * Constructor.
     */
    public PokedexFragment(){
        super(R.layout.fragment_pokedex);
    } //Fin Constructor

    /**
     * Llamada a la API para obtener la Pokedex
     * @return
     */
    private ArrayList<Pokemon> getPokedex(String limite) {
        ArrayList<Pokemon> pokedex = new ArrayList<>();
        //Si no añadimos un PKM al inicio, peta
        pokedex.add(new Pokemon("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"));

        Call<PokemonList> call = pkmInterface.doGetPokedex("0", limite);
        call.enqueue(new Callback<PokemonList>() {
            @Override
            public void onResponse(Call<PokemonList> call, Response<PokemonList> response) {

                PokemonList pkmList = response.body();
                Integer count = pkmList.count;
                //String next = pkmList.next;
                //String previous = pkmList.previous;
                List<PokemonList.Results> results = pkmList.results;
                pokedex.clear();
                for(PokemonList.Results r: results){
                    if ( r.name != null && r.url != null) {
                        Pokemon p = new Pokemon(r.name, r.url);
                        //Añadimos el pokemon al array local
                        pokedex.add(p);
                    }
                } //fin for
            }
            @Override
            public void onFailure(Call<PokemonList> call, Throwable t) {
                call.cancel();
            }
        });

        //Devolvemos la pokedex
        return pokedex;
    } //Fin de getPokedex



}
