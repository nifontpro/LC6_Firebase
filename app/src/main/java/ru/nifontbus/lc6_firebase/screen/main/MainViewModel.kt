package ru.nifontbus.lc6_firebase.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.nifontbus.lc6_firebase.model.Bio
import ru.nifontbus.lc6_firebase.model.Resource
import ru.nifontbus.lc6_firebase.repository.FirebaseRepo
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FirebaseRepo
) : ViewModel() {
    val bioList = repository.bioList

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message.asSharedFlow()

    fun addBio(bio: Bio) = viewModelScope.launch {
        when (val result = repository.addBio(bio)) {
            is Resource.Success -> {
                sendMessage(result.message)
            }
            is Resource.Error -> sendMessage(result.message)
        }
    }

    fun deleteBio(id: String) = viewModelScope.launch {
        when (val result = repository.deleteBio(id = id)) {
            is Resource.Success -> {
                sendMessage(result.message)
            }
            is Resource.Error -> sendMessage(result.message)
        }
    }

    private suspend fun sendMessage(msg: String) {
        _message.emit(msg)
    }
}