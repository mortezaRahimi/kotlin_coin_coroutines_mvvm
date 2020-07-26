package com.mortex.farhadmarket.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mortex.farhadmarket.data.model.PriceItem
import com.mortex.farhadmarket.data.source.SwapRepo
import kotlinx.coroutines.launch
import com.mortex.farhadmarket.common.Result

class MainViewModel(private val repo: SwapRepo) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _allPrices = MutableLiveData<List<PriceItem>>()
    val allPrices : LiveData<List<PriceItem>> = _allPrices

    fun getData() {
        viewModelScope.launch{


            _loading.postValue(true)
            when (val res = repo.getPrices()) {

                is Result.Success -> {
                    _allPrices.value = res.data
                    _loading.postValue(false)
                }

                is Result.GenericError -> {
                    _loading.postValue(false)
                }

                is Result.NetworkError -> {
                    _loading.postValue(false)
                }
            }
        }
    }

}