package com.ivandjr.listcompose.ui.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface ViewEvent

interface ViewState

interface ViewSideEffect

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState, Effect : ViewSideEffect> :
    ViewModel() {

    abstract fun setInitialState(): UiState
    abstract fun onEvents(event: Event)

    private val initialState: UiState by lazy { setInitialState() }

    private val _viewState: MutableState<UiState> = mutableStateOf(initialState)
    val viewState: State<UiState> = _viewState

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() = viewModelScope.launch {
        _event.collect {
            onEvents(it)
        }
    }

    fun setEvent(event: Event) = viewModelScope.launch { _event.emit(event) }

    private fun mainSetState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        mainSetState(reducer)
    }

    protected suspend fun CoroutineScope.setState(
        reducer: UiState.() -> UiState,
    ) = withContext(Dispatchers.Main) {
        mainSetState(reducer)
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}
