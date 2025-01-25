package com.pmdm.pokemonagh.firebase;


import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pmdm.pokemonagh.model.Pokemon;
import com.pmdm.pokemonagh.model.TiposPokemon;
import com.pmdm.pokemonagh.retrofit.PokemonApiClient;
import com.pmdm.pokemonagh.retrofit.PokemonApiInterface;
import com.pmdm.pokemonagh.retrofit.PokemonData;
import com.pmdm.pokemonagh.retrofit.PokemonList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirestoneApi {

    public boolean isRetorno() {
        return retorno;
    }

    public void setRetorno(boolean retorno) {
        this.retorno = retorno;
    }

    private boolean retorno;
    private FirebaseFirestore db;
    public FirebaseFirestore getDb() {
        return db;
    }

    private PokemonApiInterface pkmInterface = PokemonApiClient.getClient().create(PokemonApiInterface.class);

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    /**
     * Constructor
     * @param db Base de datos
     */
    public FirestoneApi(FirebaseFirestore db) {
        this.db = FirebaseFirestore.getInstance();
        this.retorno = false;
    }

    public FirestoneApi() {
        this.db = FirebaseFirestore.getInstance();
        this.retorno = false;
    }

    /**
     * Agrega un pokemon a la colección
     * @param p el Pokemon
     * @return true si lo hace, false si no lo hace
     */
    public boolean addPokemon(Pokemon p){
        //Obtenemos y modificamos los datos del pokemon

        Call<PokemonData> call = pkmInterface.doGetPokemonByName(p.getNombre());
        call.enqueue(new Callback<PokemonData>() {
            @Override
            public void onResponse(Call<PokemonData> call, Response<PokemonData> response) {

                PokemonData pkmData = response.body();
                Integer height = pkmData.height;
                Integer weight = pkmData.weight;
                p.setAltura(height);
                p.setPeso(weight);
            }
            @Override
            public void onFailure(Call<PokemonData> call, Throwable t) {
                call.cancel();
            }
        });

        //Creamos el pokemon mapeado
        Map<String, Object> pokemon = new HashMap<>();
        pokemon = mapearPokemon(p);

        //Por defecto devolveremos true
        setRetorno(true);

        /*
        //Primero añadimos los tipos
        for (TypeData t: p.getTypes()){
            Map<String, Object> tipo_mapeado = mapearTipo(t);
            db.collection("type").document(t.getType_nombre()).set(tipo_mapeado);
        }
        */
        //Ahora añadimos los pokemons
        db.collection("capturados").document(p.getNombre()).set(pokemon)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       setRetorno(true);
                   }
               })
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        setRetorno(true);
                    }
               })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        setRetorno(false);
                    }
                });

        //Añadimos

        return isRetorno();

    } //Fin de addPokemon

    /**
     * Mapea un objeto TypeData (typo pokemon) a un Map
     * @param t El Tipo de Pokemon
     * @return
     */
    private Map<String, Object> mapearTipo(TiposPokemon t) {
        Map<String, Object> tipo = new HashMap<>();
        tipo.put("slot", t.getSlot());
        tipo.put("name", t.getNombre());
        tipo.put("url", t.getUrl());
        return tipo;
    }

    /**
     * Consulta los pokemon capturados
     */

    public void consultaCapturados(){
        ArrayList<Pokemon> capturados = new ArrayList<Pokemon>();


        DocumentReference docRef = db.collection("capturados").document("squirtle");
        docRef.get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        })
        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
              @Override
              public void onSuccess(DocumentSnapshot documentSnapshot) {
                  if (documentSnapshot.exists()){

                  }
              }
          }
        );
/*
        db.collection("capturados")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            setRetorno(true);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Pokemon p = document.toObject(Pokemon.class);
                                //capturados.add(p);
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                            setRetorno(false);
                        }
                    }
                });
                */

    }

    boolean estaCapturado = false;
    public boolean pokemonEstaCapturado(String nombrePokemon){

        DocumentReference docRef = db.collection("capturados").document(nombrePokemon);
        docRef.get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        estaCapturado = false;
                    }
                })
                .addOnCompleteListener((new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()){
                            estaCapturado = true;
                        } else {
                            estaCapturado = false;
                        }
                    } else estaCapturado =false;
                }
        }));
        return estaCapturado;
    } //Indica si un pokemon está capturado

    /**
     * Obtiene los pokemon de la colección "capturados"
     * @return
     */
    public ArrayList<Pokemon> getPokemonCapturados(){

        ArrayList<Pokemon> capturados = new ArrayList<Pokemon>();
        capturados.add(new Pokemon("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"));

        db.collection("capturados")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        setRetorno(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        setRetorno(false);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            setRetorno(true);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Pokemon p = document.toObject(Pokemon.class);
                                //capturados.add(p);
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            setRetorno(false);
                            //Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return capturados;
    } //Fin getPokemonCapturados

    /**
     * Mapea un Pokemon a un objeto Map
     * @param p el Pokemon
     * @return el Mapeo
     */
    public Map<String, Object> mapearPokemon(Pokemon p) {
        Map<String, Object> pokemonDB = new HashMap<>();

        pokemonDB.put("order", p.getNumero());
        pokemonDB.put("name", p.getNombre());
        pokemonDB.put("url", p.getUrl());
        pokemonDB.put("front", p.getImagenFrontal());
        pokemonDB.put("back", p.getImagenEspalda());
        pokemonDB.put("weight", p.getPeso());
        pokemonDB.put("height", p.getAltura());
        //pokemon.put("types", p.obtener_tipos());
        return pokemonDB;

    } //Fin de mapearPokemon



}
