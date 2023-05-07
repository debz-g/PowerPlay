package dev.redfox.powerplay.networking

import dev.redfox.powerplay.models.BeerModel
import retrofit2.Response
import javax.inject.Inject

class BeerRepository @Inject constructor(private val beerApiInterface: BeerApiInterface) {

    suspend fun getAllBeers(): Response<List<BeerModel>>{
        return beerApiInterface.getAllBeers()
    }
}