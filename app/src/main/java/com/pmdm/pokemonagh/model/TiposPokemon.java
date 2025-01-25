package com.pmdm.pokemonagh.model;

public class TiposPokemon {

    private int slot;
    private String nombre;
    private String url;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
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

    public TiposPokemon(int slot, String nombre, String url) {
        this.slot = slot;
        this.nombre = nombre;
        this.url = url;
    }

}
