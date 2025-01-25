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

    private LoginActivity loginActivity;
     ArrayList<Pokemon> pkmCapturados;
     ArrayList<Pokemon> pkmPokedex;

    public ArrayList<Pokemon> getPkmCapturados() {
        return pkmCapturados;
    }

    public void setPkmCapturados(ArrayList<Pokemon> pkmCapturados) {
        this.pkmCapturados = pkmCapturados;
    }

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

    } //Fin onCreate

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

    /**
     * Consulta los Pokemon de la BBDD
     * @return
     */
    private ArrayList<Pokemon> getCapturados(FirestoneApi db) {
        ArrayList<Pokemon> capturadosDB = new ArrayList<>();

        if (db == null)
            db = new FirestoneApi();

        capturadosDB = db.getPokemonCapturados();
        return capturadosDB;
    }



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