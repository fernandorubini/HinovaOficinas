package br.com.fernandodev.hinovaoficinas.domain.model

data class Oficina(
    val id: Long,
    val nome: String,
    val descricao: String,
    val descricaoCurta: String,
    val endereco: String,
    val telefone: String,
    val email: String,
    val rating: Int,
    val fotoBase64: String? = null,
    val ativo: Boolean = true
)
