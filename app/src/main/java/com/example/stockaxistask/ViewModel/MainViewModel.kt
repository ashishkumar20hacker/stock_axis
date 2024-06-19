package com.example.stockaxistask.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockaxistask.DataSource.ApiHandler
import com.example.stockaxistask.DataSource.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"

    fun getLM(onResponseSorted: (list: MutableList<Data>?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ApiHandler.getOrders("LM", object : ApiHandler.ApiResponseInterface {
                    override fun onSuccess(root: MutableList<Data>?) {
                        onResponseSorted(root)
                    }

                    override fun onError() {
                        Log.e(TAG, "Failed to fetch product details")
                        onResponseSorted(null)
                    }

                })
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch product details ${e.localizedMessage}")
                onResponseSorted(null)
            }
        }
    }

    fun getEML(onResponseSorted: (list: MutableList<Data>?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                ApiHandler.getOrders("EML", object : ApiHandler.ApiResponseInterface {
                    override fun onSuccess(root: MutableList<Data>?) {
                        onResponseSorted(root)
                    }

                    override fun onError() {
                        Log.e(TAG, "Failed to fetch product details")
                        onResponseSorted(null)
                    }

                })
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch product details ${e.localizedMessage}")
                onResponseSorted(null)
            }
        }
    }
}