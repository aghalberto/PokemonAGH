package com.pmdm.pokemonagh.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Clase para manejar el listado de pokemon desde la API
 */
public class PokemonList {
    @SerializedName("count")
    public Integer count;
    @SerializedName("next")
    public String next;
    @SerializedName("previous")
    public String previous;

    @SerializedName("results")
    public List<Results> results = null;

    public class Results {

        @SerializedName("name")
        public String name;
        @SerializedName("url")
        public String url;

    }

    public PokemonList(Integer count, String next, String previous, List<Results> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }
}
