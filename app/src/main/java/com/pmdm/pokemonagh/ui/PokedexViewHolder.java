package com.pmdm.pokemonagh.ui;

import androidx.recyclerview.widget.RecyclerView;

import com.pmdm.pokemonagh.model.Pokemon;
import com.pmdm.pokemonagh.databinding.PokedexCardviewBinding;
import com.squareup.picasso.Picasso;

public class PokedexViewHolder extends RecyclerView.ViewHolder{
    //private final
    PokedexCardviewBinding binding;


    public PokedexViewHolder(PokedexCardviewBinding binding) {
            //super(itemView); //binding.getRoot()
            super(binding.getRoot());
            this.binding = binding;
    }

    /**
     * Pasa los atributos del objeto al CardView
     * @param pk
     */
    public void bind (Pokemon pk){
        String strNum = Integer.toString(pk.getNumero());
        String imagen;

        if (pk.isCapturado()) imagen = pk.getImagenFrontal();
        else imagen = pk.getImagenEspalda();
        //Binding
        binding.nombre.setText(pk.getNombre().toUpperCase());
        Picasso.get()
                .load(imagen)
                .into(binding.imagen);
        //binding.executePendingBinding();
    }




}
