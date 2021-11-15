package com.ruwa.planets.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ruwa.planets.R
import com.ruwa.planets.data.model.Planet
import com.ruwa.planets.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()
    }

    /**
     * Shows the planet details retrieved from the previous screen
     */
    private fun updateUI() {

        val planetInfo: Planet? = intent.getParcelableExtra(EXTRA_PLANET_INFO)
        binding.textViewPlanetName.text = planetInfo?.name
        binding.textViewOrbitalPeriod.text = planetInfo?.orbitalPeriod
        binding.textViewGravity.text = planetInfo?.gravity
        Glide.with(this).load(planetInfo?.planetImageUrl)
            .placeholder(R.drawable.image_not_available)
            .into(binding.imageView)

    }


}