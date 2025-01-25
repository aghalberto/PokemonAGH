package com.pmdm.pokemonagh.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonData {
    @SerializedName("order")
    public Integer order;
    @SerializedName("height")
    public Integer height;
    @SerializedName("weight")
    public Integer weight;

    //El sprite (foto) en lugar de serializarlo lo conseguimos con el order
    @SerializedName("types")
    public List<PokemonTypes> pokemonTypes = null;

    public class PokemonTypes {

        @SerializedName("slot")
        public Integer slot;
        @SerializedName("type")
        public PokemonType pokemonType;

    }

    public class PokemonType{
        @SerializedName("name")
        public String type_name;

        @SerializedName("url")
        public String type_url;

    }
}
