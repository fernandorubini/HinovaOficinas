package br.com.fernandodev.hinovaoficinas.data.session

data class LoginUser(
    val Id: String,
    val Nome: String,
    val Codigo_mobile: String,
    val Cpf: String,
    val Email: String,
    val Situacao: String,
    val Telefone: String
)
