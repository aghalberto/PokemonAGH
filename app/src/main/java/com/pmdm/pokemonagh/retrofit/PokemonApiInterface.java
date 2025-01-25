package com.pmdm.pokemonagh.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonApiInterface {

        @GET("/api/v2/pokemon?")
        Call<PokemonList> doGetPokedex(@Query("offset") String offset, @Query("limit") String limit);

        @GET("/api/v2/pokemon/{order}/")
        Call<PokemonData> doGetPokemonByID(@Path("order") String order);

        @GET("/api/v2/pokemon/{name}/")
        Call<PokemonData> doGetPokemonByName(@Path("name") String order);

}
