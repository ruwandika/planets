package com.ruwa.planets.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruwa.planets.R
import com.ruwa.planets.data.api.ApiHelper
import com.ruwa.planets.data.api.ApiServiceImpl
import com.ruwa.planets.data.model.Planet
import com.ruwa.planets.data.model.PlanetResults
import com.ruwa.planets.databinding.ActivityMainBinding
import com.ruwa.planets.ui.base.ViewModelFactory
import com.ruwa.planets.ui.main.adapter.PlanetsAdapter
import com.ruwa.planets.ui.main.viewmodel.MainViewModel
import com.ruwa.planets.utils.Status

const val EXTRA_PLANET_INFO = "planet.info"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupObserver()

    }

    /**
     * Setup the main ViewModel class that helps to manage UI related data
     */
    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    /**
     * Setup the observer that can receive planets data once it is available.
     * And also this helps UI to handle possible failures in retrieving data
     */
    private fun setupObserver() {
        mainViewModel.getPlanets().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.errorLayout.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { planets -> setupUI(planets) }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.errorLayout.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorLayout.visibility = View.VISIBLE
                    retryOption(it.message)
                }
            }
        })
    }

    /**
     * Sends the planets list [response] to PlanetsAdapter which will construct the UI with selected
     * planet details
     */
    private fun setupUI(response: PlanetResults) {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter =
                PlanetsAdapter(
                    response.results,
                    PlanetsAdapter.OnPlanetClickListener { planet -> goToDetailsScreen(planet) })
        }
    }

    /**
     * Navigates to the details screen of the selected [planet]
     */
    private fun goToDetailsScreen(planet: Planet) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_PLANET_INFO, planet)
        startActivity(intent)
    }

    /**
     * Enables retry option with the possible failure [message].
     */
    private fun retryOption(message: String?) {
        binding.errorReason.text = message ?: resources.getString(R.string.retry_description)
        binding.buttonRetry.setOnClickListener {
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }
}