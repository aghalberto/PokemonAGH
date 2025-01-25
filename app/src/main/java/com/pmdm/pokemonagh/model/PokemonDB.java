package com.pmdm.pokemonagh.model;

/**
 * Clase Pokemon pero con los atributos de la Firestore DB
 */
public class PokemonDB {
    private int order;
    private String name;
    private String url;
    private String back;
    private String front;
    private int height;
    private int weight;

    public PokemonDB(int order, String name, String url, String back, String front, int height, int weight) {
        this.order = order;
        this.name = name;
        this.url = url;
        this.back = back;
        this.front = front;
        this.height = height;
        this.weight = weight;
    }
}
