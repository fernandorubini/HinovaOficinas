package br.com.fernandodev.hinovaoficinas.ui.vehicle

import androidx.lifecycle.ViewModel
import br.com.fernandodev.hinovaoficinas.data.repository.VehicleRepository

class VehiclesViewModel(
    private val repo: VehicleRepository
) : ViewModel() {
    // adapte aqui conforme a API do seu repositório
    // ex.: fun list() = repo.listAll()
}
