package com.pmdm.pokemonagh.model;

import java.util.ArrayList;

public class Pokemon {

    private int numero;
    private String nombre;
    private String url;
    private String imagenFrontal;
    private String imagenEspalda;
    private int altura;
    private int peso;
    private boolean capturado = false;

    private ArrayList<TiposPokemon> tiposPokemon;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImagenFrontal() {
        return imagenFrontal;
    }

    public void setImagenFrontal(String imagenFrontal) {
        this.imagenFrontal = imagenFrontal;
    }

    public String getImagenEspalda() {
        return imagenEspalda;
    }

    public void setImagenEspalda(String imagenEspalda) {
        this.imagenEspalda = imagenEspalda;
    }

    public ArrayList<TiposPokemon> getTiposPokemon() {
        return tiposPokemon;
    }

    public void setTiposPokemon(ArrayList<TiposPokemon> tiposPokemon) {
        this.tiposPokemon = tiposPokemon;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public boolean isCapturado() {
        return capturado;
    }

    public void setCapturado(boolean capturado) {
        this.capturado = capturado;
    }

    /**
     * Para el constructor solo necesitamos el nombre y la url
     * @param nombre
     * @param url
     */
    public Pokemon(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
        this.numero = obtenerNumero(url);
        this.imagenFrontal = obtenerImagenFrontal(url);
        this.imagenEspalda = obtenerImagenEspalda(url);
        this.capturado = false;
    }

    /**
     * Obtiene el número del pokemon desde la URL
     * @param url de la API del Pokemon
     * @return EL número del Pokemon
     */
    public int obtenerNumero(String url) {
        if (url != null) {
            String[] words = url.split("/");
            String last = "1", pre = "0";
            for (String word : words) {
                pre = last;
                last = word;
            }
            try {
                return Integer.parseInt(last);
            } catch (NumberFormatException e) {
                return 1;
            }
        } else return 0;

    } //Fin obtenerNumero

    /**
     * Obtiene la imagen frontal.
     * @param url
     * @return
     */
    public String obtenerImagenFrontal(String url) {
        String[] words = url.split("/");
        String last="1", pre = "0";
        for (String word : words){
            pre = last;
            last = word;
        }
        try {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
                    + last
                    + ".png";
        } catch (Exception e) {
            return url;
        }
    } //Fin obtenerImagenFrontal

    /**
     * Obtiene la imagen de la espalda
     * @param url URL del Pokemon
     * @return url de la imagen de la espalda
     */
    public String obtenerImagenEspalda(String url) {
        String[] words = url.split("/");
        String last="1", pre = "0";
        for (String word : words){
            pre = last;
            last = word;
        }
        try {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/"
                    + last
                    + ".png";
        } catch (Exception e) {
            return url;
        }
    } //Fin obtenerImagenEspalda

}
