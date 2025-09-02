package br.com.fernandodev.hinovaoficinas.ui.customer

import androidx.lifecycle.ViewModel
import br.com.fernandodev.hinovaoficinas.data.repository.CustomerRepository

class CustomersViewModel(
    private val repo: CustomerRepository
) : ViewModel() {

    // Adapte os métodos conforme sua UI precisar.
    // Exemplo de “pass-through”:
    // fun search(query: String) = repo.search(query)
}
