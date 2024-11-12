package com.example.beerapp.model

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.beerapp.repository.BeerRepository
import com.google.firebase.auth.FirebaseUser

class BeerViewModel: ViewModel() {
    private val repo = BeerRepository()
    val beers: State<List<Beer>> = repo.beers
    val singleBeer: State<Beer?> = repo.singleBeer
    val errorMessage: State<String> = repo.errorMessage
    var isLoadingBooks: State<Boolean> = repo.isLoadingBeers

    fun getBeers(user: FirebaseUser? = null){
        repo.getBeers(user)
    }
    fun getBeerById(id: Int){
        repo.getBeerById(id)
    }
    fun add(beer: Beer){
        repo.add(beer)
    }
    fun delete(id: Int){
        repo.deleteBeer(id)
    }
    fun update(id: Int, beer: Beer){
        repo.updateBeer(id, beer)
    }
}