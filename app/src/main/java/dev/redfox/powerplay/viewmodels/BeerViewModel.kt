package dev.redfox.powerplay.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.redfox.powerplay.models.BeerModel
import dev.redfox.powerplay.networking.BeerRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(private val beerRepository: BeerRepository): ViewModel() {

    private val _response = MutableLiveData<Response<List<BeerModel>>>()
    val response: MutableLiveData<Response<List<BeerModel>>> get() = _response

    fun getAllBeers() {
        viewModelScope.launch {
            val dResponse = beerRepository.getAllBeers()
            _response.value = dResponse
        }
    }

}
