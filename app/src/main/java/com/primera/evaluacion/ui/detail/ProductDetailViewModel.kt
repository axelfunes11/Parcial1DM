package com.primera.evaluacion.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.primera.evaluacion.data.remote.ProductRetrofitClient
import com.primera.evaluacion.model.ProductDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {

    private val apiService = ProductRetrofitClient.apiService

    private val _state = MutableStateFlow(ProductDetailState())
    val state: StateFlow<ProductDetailState> = _state

    fun cargarProducto(id: String) {

        viewModelScope.launch {

            _state.value = _state.value.copy(
                cargando = true,
                error = ""
            )

            try {

                val response = apiService.getProductById(id)

                if (response.isSuccessful && response.body() != null) {

                    _state.value = _state.value.copy(
                        producto = response.body()!!,
                        cargando = false
                    )

                } else {

                    _state.value = _state.value.copy(
                        cargando = false,
                        error = "Error al cargar el producto"
                    )
                }

            } catch (e: Exception) {

                _state.value = _state.value.copy(
                    cargando = false,
                    error = "Error de conexión: ${e.message}"
                )
            }
        }
    }
}