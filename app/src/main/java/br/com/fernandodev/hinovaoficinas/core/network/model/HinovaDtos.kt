// app/src/main/java/br/com/fernandodev/hinovaoficinas/core/network/model/HinovaDtos.kt
package br.com.fernandodev.hinovaoficinas.core.network.model

import com.google.gson.annotations.SerializedName

data class OficinasResponse(
    @SerializedName("ListaOficinas") val listaOficinas: List<OficinaDto>?,
    @SerializedName("RetornoErro")  val retornoErro: ApiErrorWrapper?,
    @SerializedName("Token")        val token: String?
)

data class ApiErrorWrapper(
    @SerializedName("retornoErro") val message: String?
)

data class OficinaDto(
    @SerializedName("Id")               val id: Int? = null,
    @SerializedName("Nome")             val nome: String? = null,
    @SerializedName("Descricao")        val descricao: String? = null,
    @SerializedName("DescricaoCurta")   val descricaoCurta: String? = null,
    @SerializedName("Endereco")         val endereco: String? = null,
    @SerializedName("Latitude")         val latitude: String? = null,
    @SerializedName("Longitude")        val longitude: String? = null,
    @SerializedName("Foto")             val foto: String? = null,
    @SerializedName("AvaliacaoUsuario") val avaliacaoUsuario: Int? = null,
    @SerializedName("CodigoAssociacao") val codigoAssociacao: Int? = null,
    @SerializedName("Email")            val email: String? = null,
    @SerializedName("Telefone1")        val telefone1: String? = null,
    @SerializedName("Telefone2")        val telefone2: String? = null,
    @SerializedName("Ativo")            val ativo: Boolean? = null
)
