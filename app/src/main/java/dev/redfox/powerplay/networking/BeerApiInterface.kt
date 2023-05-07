package dev.redfox.powerplay.networking

import dev.redfox.powerplay.models.BeerModel
import retrofit2.Response
import retrofit2.http.GET

interface BeerApiInterface {

    @GET("beers")
    suspend fun getAllBeers(): Response<List<BeerModel>>
}