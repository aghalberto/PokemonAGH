package com.pmdm.pokemonagh.ui;

import androidx.recyclerview.widget.RecyclerView;

import com.pmdm.pokemonagh.databinding.CapturadosCardviewBinding;
import com.pmdm.pokemonagh.model.Pokemon;
import com.squareup.picasso.Picasso;

public class CapturadosViewHolder extends RecyclerView.ViewHolder{
    //private final
    CapturadosCardviewBinding binding;


    public CapturadosViewHolder(CapturadosCardviewBinding binding) {
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
        //Binding
        binding.nombre.setText(pk.getNombre().toUpperCase());
        Picasso.get()
                .load(pk.getImagenFrontal())
                .into(binding.imagen);
        //binding.executePendingBinding();
    }




}
