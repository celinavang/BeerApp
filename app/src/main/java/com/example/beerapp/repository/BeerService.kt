package com.example.beerapp.repository

import com.example.beerapp.model.Beer
import retrofit2.Call

import retrofit2.http.*

// The methods in this interface are attributed with the specific (collection related) part of the URL
// The base URL is found in the class BooksRepository

// Add to AndroidManifest.xml
//   <uses-permission android:name="android.permission.INTERNET" />

// Add to gradle file: retrofit etc

interface BeerService {
    @GET("beers")
    fun getAllBeers(): Call<List<Beer>>

    @GET("beers/{id}")
    fun getBeerById(@Path("id")id: Int): Call<Beer>

    @POST("beers")
    fun createBeer(@Body beer: Beer): Call<Beer>

    @DELETE("beers/{id}")
    fun deleteBeer(@Path("id")id: Int): Call<Beer>

    @PUT("beers/{id}")
    fun updateBeer(@Path("id") id: Int, @Body beer: Beer): Call<Beer>
}