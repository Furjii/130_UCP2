package com.example.ucp_2.ui.theme

import androidx.lifecycle.ViewModel
import com.example.ucp_2.data.DataUiState
import com.example.ucp_2.data.FormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormViewModel : ViewModel() {
    private val _stateUI = MutableStateFlow((DataUiState()))
    private val _nameSTATE = MutableStateFlow(FormState())
    val stateUI: StateFlow<DataUiState> = _stateUI.asStateFlow()
    val nameST: StateFlow<FormState> = _nameSTATE.asStateFlow()

    fun setNama(list: MutableList<String>) {
        _nameSTATE.update { stateSaatIni ->
            stateSaatIni.copy(
                nama = list[0],
                nim = list[1],
                konsentrasi = list[2],
            )
        }
    }


    fun setDosen(dosenPilihan: String) {
        _stateUI.update { stateSaatIni -> stateSaatIni.copy(Dosen1 = dosenPilihan) }

    }


    fun resetForm() {
        _stateUI.value = DataUiState()
    }
}
