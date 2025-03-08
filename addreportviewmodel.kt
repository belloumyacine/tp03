package com.example.imatah.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imatah.domain.usecase.AddReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReportViewModel @Inject constructor(
    private val addReportUseCase: AddReportUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddReportState())
    val state: StateFlow<AddReportState> = _state

    fun onEvent(event: AddReportEvent) {
        when (event) {
            is AddReportEvent.TitleChanged -> {
                _state.value = _state.value.copy(title = event.title)
            }
            is AddReportEvent.DescriptionChanged -> {
                _state.value = _state.value.copy(description = event.description)
            }
            is AddReportEvent.CategorySelected -> {
                _state.value = _state.value.copy(category = event.category)
            }
            is AddReportEvent.CoordinatesChanged -> {
                _state.value = _state.value.copy(coordinates = Pair(event.latitude, event.longitude))
            }
            is AddReportEvent.ImageUrlChanged -> {
                _state.value = _state.value.copy(imageUrl = event.url)
            }
            is AddReportEvent.StatusChanged -> {
                _state.value = _state.value.copy(status = event.status)
            }
            AddReportEvent.Submit -> {
                submitReport()
            }
        }
    }

    private fun submitReport() {
        val currentState = _state.value
        if (validateInput(currentState)) {
            _state.value = currentState.copy(isLoading = true)
            viewModelScope.launch {
                try {
                    addReportUseCase(
                        title = currentState.title,
                        description = currentState.description,
                        category = currentState.category,
                        coordinates = currentState.coordinates,
                        imageUrl = currentState.imageUrl,
                        status = currentState.status
                    )
                    _state.value = currentState.copy(isSuccess = true, isLoading = false)
                } catch (e: Exception) {
                    _state.value = currentState.copy(error = e.message, isLoading = false)
                }
            }
        } else {
            _state.value = currentState.copy(error = "Please fill all fields correctly")
        }
    }

    private fun validateInput(state: AddReportState): Boolean {
        return state.title.isNotBlank() &&
                state.description.isNotBlank() &&
                state.category.isNotBlank() &&
                state.coordinates.first != 0.0 &&
                state.coordinates.second != 0.0 &&
                state.imageUrl.isNotBlank() &&
                state.status.isNotBlank()
    }
}
