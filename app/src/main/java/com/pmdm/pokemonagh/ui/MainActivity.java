package com.pmdm.pokemonagh.ui;

import android.app.LocaleManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pmdm.pokemonagh.R;
import com.pmdm.pokemonagh.firebase.FirestoneApi;
import com.pmdm.pokemonagh.firebase.LoginActivity;
import com.pmdm.pokemonagh.model.Pokemon;
import com.pmdm.pokemonagh.retrofit.PokemonApiClient;
import com.pmdm.pokemonagh.retrofit.PokemonApiInterface;
import com.pmdm.pokemonagh.retrofit.PokemonList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /**
     * Login Activity (Firebase)
     */
    private LoginActivity loginActivity;

    /**
     * API Firestone
     */
    private FirestoneApi firestoneApi = new FirestoneApi();

    /**
     * Interfaz Retrofit
     */
    private PokemonApiInterface pkmInterface = PokemonApiClient.getClient().create(PokemonApiInterface.class);


    /**
     * Pokemon Capturados (BD Firebase) declarados aquí para usar entre fragments
     */
    private ArrayList<Pokemon> pkmCapturados;
    public ArrayList<Pokemon> getPkmCapturados() {
        return pkmCapturados;
    }

    public void setPkmCapturados(ArrayList<Pokemon> pkmCapturados) {
        this.pkmCapturados = pkmCapturados;
    }

    /**
     * Pokemons de la pokedex (Retrofit) para usar entre fragments
     */
    private ArrayList<Pokemon> pkmPokedex;
    public ArrayList<Pokemon> getPkmPokedex() {
        return pkmPokedex;
    }

    public void setPkmPokedex(ArrayList<Pokemon> pkmPokedex) {
        this.pkmPokedex = pkmPokedex;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Preferencias
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //Idioma
        configurarIdioma(sharedPreferences);

        //Pokemons capturados
        setPkmCapturados(firestoneApi.getPokemonCapturados());

        //Pokemons de la pokedex
        //Obtenemos el límite de las preferencias
        String limite = sharedPreferences.getString("edit_text_limit", "100");//"100"; //sharedPreferences.getString("edit_text_limit", "100");
        pkmPokedex = getPokedex(sharedPreferences);
        setPkmPokedex(pkmPokedex);

    } //Fin onCreate

    public ArrayList<Pokemon> getPokemonCapturados(){
        return firestoneApi.getPokemonCapturados();
    }
    /**
     * Llamada a la API para obtener la Pokedex
     * @return La Pokedex
     */
    boolean estaCapturado = false;
    public ArrayList<Pokemon> getPokedex(SharedPreferences sharedPreferences) {

        ArrayList<Pokemon> pokedex = new ArrayList<>();
        //Si no añadimos un PKM al inicio, peta
        pokedex.add(new Pokemon("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"));

        final boolean ocultar_capturados = sharedPreferences.getBoolean("switch_hide_catched", true);
        String limite = sharedPreferences.getString("edit_text_limit", "100");


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
                        //Consultamos si el pokemon está en la BBDD
                        estaCapturado = isPokemonCapturado(r.name);
                        p.setCapturado(estaCapturado);
                        //Añadimos el pokemon al array local, si la opción es esa.
                        if (ocultar_capturados == false) pokedex.add(p);
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

    /**
     * Recorre el ArrayList de los capturados para ver si está capturado el name
     * @param name
     * @return
     */
    public boolean isPokemonCapturado(String name) {
        boolean capturado = false;
        for (Pokemon p: getPokemonCapturados()){
            if (p.getNombre().equals(name)) capturado = true;
            //¿Salir del bucle?
        }
        return capturado;

    }

    /**
     * Configura el idioma según los ajustes
     */
    private void configurarIdioma(SharedPreferences sharedPreferences) {
        Boolean spanish = sharedPreferences.getBoolean("switch_language", true);
        if (spanish) {
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("es-ES");
            AppCompatDelegate.setApplicationLocales(appLocale);
        } else {
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("en-US");
            AppCompatDelegate.setApplicationLocales(appLocale);
        }
    }

    /*
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        //Idioma
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean spanish = sharedPreferences.getBoolean("switch_language", true);
        if (spanish) {
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("es-ES");
            AppCompatDelegate.setApplicationLocales(appLocale);
        } else {
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("en-US");
            AppCompatDelegate.setApplicationLocales(appLocale);
        }
        super.onConfigurationChanged(newConfig);
    }
    */

    @Override
    protected void onDestroy() {

        FirebaseAuth mAuth;

        super.onDestroy();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        Boolean closeSession = sharedPreferences.getBoolean("check_box_close", false);
        if (closeSession) {
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null)
                FirebaseAuth.getInstance().signOut();
        }
    } //Fin onDestroy

} //Fin MainActivity