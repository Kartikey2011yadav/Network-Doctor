package com.example.network_doctor.ui.speedtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network_doctor.domain.model.TestStage
import com.example.network_doctor.domain.repository.SpeedTestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class SpeedTestViewModel @Inject constructor(
    private val repository: SpeedTestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private var testJob: Job? = null

    fun toggleTest() {
        if (_uiState.value.inProgress) {
            cancelTest()
        } else {
            startTest()
        }
    }

    private fun cancelTest() {
        testJob?.cancel()
        _uiState.update { 
            it.copy(
                inProgress = false, 
                speed = "0.0", 
                arcValue = 0f, 
                stageName = "CANCELLED"
            ) 
        }
    }

    fun startTest() {
        testJob?.cancel() // Cancel any existing test
        
        _uiState.update { 
            UiState(
                inProgress = true, 
                speed = "0.0", 
                arcValue = 0f, 
                graphData = emptyList(),
                stageName = "PREPARING"
            ) 
        }

        testJob = repository.startSpeedTest()
            .onEach { result ->
                _uiState.update { state ->
                    val currentState = state.copy(
                        ping = if (result.pingMs > 0) "${result.pingMs.toInt()} ms" else state.ping,
                        // Update max speed only if current speed is higher
                         maxSpeed = if (result.speedMbps > (state.maxSpeed.replace(" mbps", "").toFloatOrNull() ?: 0f)) "%.1f mbps".format(result.speedMbps) else state.maxSpeed
                    )

                    when (result.stage) {
                        TestStage.PING -> {
                            currentState.copy(stageName = "PING", speed = "PING...")
                        }
                        TestStage.DOWNLOAD -> {
                            val normalizedArc = (result.speedMbps / 100f).coerceIn(0f, 1f)
                            // Add new point to graph, keep last 50 points
                            val newGraphData = (state.graphData + normalizedArc).takeLast(50)
                            
                            currentState.copy(
                                stageName = "DOWNLOAD",
                                speed = "%.1f".format(result.speedMbps),
                                downloadSpeed = "%.1f".format(result.speedMbps), // Live update
                                arcValue = normalizedArc,
                                graphData = newGraphData
                            )
                        }
                        TestStage.UPLOAD -> {
                            val normalizedArc = (result.speedMbps / 100f).coerceIn(0f, 1f)
                             val newGraphData = (state.graphData + normalizedArc).takeLast(50)

                            currentState.copy(
                                stageName = "UPLOAD",
                                speed = "%.1f".format(result.speedMbps),
                                uploadSpeed = "%.1f".format(result.speedMbps), // Live update
                                arcValue = normalizedArc,
                                graphData = newGraphData
                            )
                        }
                        TestStage.FINISHED -> {
                             currentState.copy(
                                 inProgress = false,
                                 arcValue = 0f,
                                 speed = "DONE",
                                 stageName = "COMPLETED",
                                 error = result.error
                             )
                        }
                        else -> currentState
                    }
                }
            }
            .catch { e ->
                _uiState.update { it.copy(inProgress = false, error = e.message, stageName = "ERROR") }
            }
            .launchIn(viewModelScope)
    }
}
