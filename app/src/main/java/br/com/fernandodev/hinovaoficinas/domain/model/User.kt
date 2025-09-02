package br.com.fernandodev.hinovaoficinas.domain.model

data class User(
    val id: String,
    val name: String,
    val code: String,
    val cpf: String,
    val email: String,
    val status: String,
    val phone: String
)
