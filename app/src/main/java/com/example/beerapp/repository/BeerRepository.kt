package com.example.beerapp.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.beerapp.model.Beer
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory.*

class BeerRepository {
    private val baseUrl = "https://anbo-restbeer.azurewebsites.net/api/"
    private val beerService: BeerService

    val beers: MutableState<List<Beer>> = mutableStateOf(listOf())
    val isLoadingBeers = mutableStateOf(false)
    val errorMessage = mutableStateOf("")
    val singleBeer: MutableState<Beer?> = mutableStateOf(null)

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(create())
            .build()
        beerService = build.create(BeerService::class.java)
        getBeers()
    }
    fun getBeers(user: FirebaseUser? = null) {
        isLoadingBeers.value = true
        beerService.getAllBeers().enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                isLoadingBeers.value = false
                if (response.isSuccessful){
                    val beerList: List<Beer>? = response.body()
                    val userEmail: String = user?.email.toString()
                    val filteredBeers = beerList?.filter {it.user == userEmail}
                    beers.value = filteredBeers?: emptyList()
                    errorMessage.value = ""
                }else{
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                isLoadingBeers.value = false
                val message = t.message ?: "No conn"
                errorMessage.value = message
            }
        })
    }
    fun getBeerById(id: Int){
        beerService.getBeerById(id).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful){
                    val beer: Beer? = response.body()
                    singleBeer.value = beer
                    errorMessage.value = ""
                }else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                }

            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                isLoadingBeers.value = false
                val message = t.message?: "No conn"
                errorMessage.value = message
            }
        })
    }
    fun add(beer: Beer) {
        beerService.createBeer(beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    getBeers()
                    errorMessage.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                }
            }
            override fun onFailure(call: Call<Beer>, t: Throwable){
                val message = t.message ?: "No conn"
                errorMessage.value = message
            }
        })
    }
    fun deleteBeer(id: Int) {
    beerService.deleteBeer(id).enqueue(object : Callback<Beer> {
        override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
            if (response.isSuccessful) {
                errorMessage.value = ""
            } else {
                val message = response.code().toString() + " " + response.message()
                errorMessage.value = message
            }
        }

        override fun onFailure(call: Call<Beer>, t: Throwable) {
            val message = t.message ?: "No conn"
            errorMessage.value = message
        }
    })
    }
    fun updateBeer(id: Int, beer: Beer) {
        beerService.updateBeer(id, beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    errorMessage.value = ""
                } else {
                    val  message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                }
                }
            override fun onFailure(call: Call<Beer>, t: Throwable) {
                val message = t.message ?: "No conn"
                errorMessage.value = message
            }
        })
    }
}