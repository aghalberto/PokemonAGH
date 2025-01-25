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
        this.pokedex = pokedex;
    }

    //private PokemonApiInterface pkmInterface = PokemonApiClient.getClient().create(PokemonApiInterface.class);
    //private FirestoneApi firestoneApi = new FirestoneApi();

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

        //pokedex = ((MainActivity)getActivity()).getPkmPokedex();
        pokedex = ((MainActivity)getActivity()).getPokedex(sharedPreferences);

        //Intentamos obtener los pokemon capturados
        pkCapturados = ((MainActivity)getActivity()).getPkmCapturados();

        boolean capturado = false;

        /*
        for (Pokemon pPokedex : pokedex){
            capturado = firestoneApi.pokemonEstaCapturado(pPokedex.getNombre());
            if (capturado) pPokedex.setCapturado(true);
        }
        */


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
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String limite = sharedPreferences.getString("edit_text_limit", "50");
        pokedex = ((MainActivity)getActivity()).getPokedex(sharedPreferences);
        //pokedex.clear(); pokedex = getPokedex("10");

    }

    /**
     * Constructor.
     */
    public PokedexFragment(){
        super(R.layout.fragment_pokedex);
        //this.pokedex = ((MainActivity)getActivity()).getPkmPokedex();
    } //Fin Constructor


}
