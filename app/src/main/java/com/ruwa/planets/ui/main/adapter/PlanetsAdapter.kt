package com.ruwa.planets.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ruwa.planets.R
import com.ruwa.planets.data.model.Planet
import com.ruwa.planets.databinding.PlanetItemBinding

class PlanetsAdapter(
    private val planets: List<Planet>,
    private val clickListener: OnPlanetClickListener
) :
    RecyclerView.Adapter<PlanetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val itemBinding =
            PlanetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanetViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        holder.itemView.setOnClickListener { clickListener.onPlanetClick(planets[position]) }
        return holder.bind(planets[position])
    }

    override fun getItemCount(): Int {
        return planets.size
    }

    class OnPlanetClickListener(private val clickListener: (planet: Planet) -> Unit) {
        fun onPlanetClick(planet: Planet) = clickListener(planet)
    }

}

class PlanetViewHolder(private val itemBinding: PlanetItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(planet: Planet) {
        itemBinding.planetName.text = planet.name
        itemBinding.planetClimate.text = planet.climate
        Glide.with(itemBinding.root.context).load(planet.planetImageUrl)
            .placeholder(R.drawable.image_not_available)
            .into(itemBinding.planetImage)
    }

}