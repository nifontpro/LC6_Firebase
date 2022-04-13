package ru.nifontbus.lc6_firebase.screen.add_bio

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: FirebaseRepo
) : ViewModel() {

    val day: MutableState<Int?> = mutableStateOf(LocalDate.now().dayOfMonth)
    val month: MutableState<Int?> = mutableStateOf(LocalDate.now().month.value)
    val year: MutableState<Int?> = mutableStateOf(LocalDate.now().year)

    val hour: MutableState<Int?> = mutableStateOf(LocalDateTime.now().hour)
    val minute: MutableState<Int?> = mutableStateOf(LocalDateTime.now().minute)

    val sys: MutableState<Int?> = mutableStateOf(null)
    val dia: MutableState<Int?> = mutableStateOf(null)
    val pulse: MutableState<Int?> = mutableStateOf(null)

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message.asSharedFlow()

    fun addBio() = viewModelScope.launch {

        val dn = day.value?.let { add0(it) } ?: ""
        val dm = month.value?.let { add0(it) } ?: ""

        val bio = Bio(
            date = "$dn${day.value}.$dm${month.value}.${year.value}",
            time = "${hour.value}:${minute.value}",
            sys = sys.value,
            dia = dia.value,
            pulse = pulse.value
        )
        when (val result = repository.addBio(bio)) {
            is Resource.Success -> {
                sys.value = null
                dia.value = null
                pulse.value = null
                sendMessage(result.message)
            }
            is Resource.Error -> sendMessage(result.message)
        }
    }

    private suspend fun sendMessage(msg: String) {
        _message.emit(msg)
    }

    fun isEnabledSave() =
        day.value != null && month.value != null && year.value != null &&
                hour.value != null && minute.value != null &&
                (sys.value != null || dia.value != null || pulse.value != null)

    private fun add0(num: Int) = if (num < 10) "0" else ""
}