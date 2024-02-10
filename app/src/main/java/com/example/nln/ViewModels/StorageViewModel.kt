package com.example.nln.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nln.Services.firebaseStorageService
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class StorageViewModel : ViewModel() {

    private val _storageState = mutableStateOf(RecipeStateStorage())

    val storageState: State<RecipeStateStorage> = _storageState

    suspend fun addStorage(requestFile: RequestBody, token: String) {
        viewModelScope.launch {
            _storageState.value = _storageState.value.copy(
                loading = true,
                error = false
            )
            try {
                firebaseStorageService.uploadImage(
                    token,
                    token,
                    requestFile
                )
                _storageState.value = _storageState.value.copy(
                    loading = false,
                    error = false
                )
                Log.d("33333331", storageState.value.loading.toString())
            } catch (e: Exception) {
                _storageState.value = _storageState.value.copy(
                    loading = false,
                    error = true
                )
            }
        }
        return
    }


    data class RecipeStateStorage(
        var loading: Boolean = true,
        val error: Boolean= false
    )
}