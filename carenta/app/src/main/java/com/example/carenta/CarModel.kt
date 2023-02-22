package com.example.carenta


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CarModel : ViewModel() {
    private val _carList = MutableLiveData<List<Car>>()
    val carList: LiveData<List<Car>> = _carList

    init {
        viewModelScope.launch {
            _carList.value = getcar()
        }
    }

    private suspend fun getcar(): List<Car>? {
        return withContext(Dispatchers.IO) {
            val response = RetrofitService.endpoint.getCar()
            if (response.isSuccessful) {
                val car = response.body()
                return@withContext car
            } else {
                return@withContext null
            }
        }
    }
}

