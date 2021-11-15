package com.ruwa.planets.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ruwa.planets.data.model.Planet
import com.ruwa.planets.data.model.PlanetResults
import com.ruwa.planets.data.repository.MainRepository
import com.ruwa.planets.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val planets = MutableLiveData<Resource<PlanetResults>>()
    private val compositeDisposable = CompositeDisposable()
    private var pageNumber = 1
    private val allPlanets: ArrayList<Planet> = ArrayList()

    init {
        fetchPlanets(pageNumber)
    }

    /**
     * Calls the methods defined in the repository class that get planets data from web services.
     * Need to provide the [page] number that will be concatenated as a query parameter in the service url.
     */
    private fun fetchPlanets(page: Int) {
        planets.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getPlanets(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { planetList ->
                        mainRepository.getPlanetImages(planetList)
                        fetchPlanetsFromNextPage(planetList)
                    }, { throwable ->
                        planets.postValue(throwable.message?.let { Resource.error(it, null) })
                        pageNumber = 1
                    })
        )

    }

    /**
     * Gets planets data from the next page in the API by calling the fetchPlanets() method with page number.
     * [planetResults] data for each page is saved locally.
     * Data will be posted to the UI once the planetResults data from all the pages are fetched.
     */
    private fun fetchPlanetsFromNextPage(planetResults: PlanetResults) {
        allPlanets.addAll(planetResults.results)

        if (!planetResults.nextPage.isNullOrEmpty()) {
            fetchPlanets(++pageNumber)
        } else {
            val planetResultsAll = PlanetResults(allPlanets, "")
            planets.postValue(Resource.success(planetResultsAll))
        }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    /**
     * Method to call from the UI observer to retrieve fetched planets.
     */
    fun getPlanets(): LiveData<Resource<PlanetResults>> {
        return planets
    }


}