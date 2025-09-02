package br.com.fernandodev.hinovaoficinas.data.net.models

import com.google.gson.annotations.SerializedName
import br.com.fernandodev.hinovaoficinas.domain.model.Oficina

data class OficinasResponseDto(
    @SerializedName("ListaOficinas") val lista: List<OficinaDto>? = null,
    @SerializedName("RetornoErro")  val retornoErro: RetornoErroDto? = null,
    @SerializedName("Token")        val token: String? = null
)

data class RetornoErroDto(
    @SerializedName("retornoErro") val retornoErro: String?
)

data class OficinaDto(
    @SerializedName("Id")               val id: Long,
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

fun OficinaDto.toDomain(): Oficina =
    Oficina(
        id = id,
        nome = nome.orEmpty(),
        descricaoCurta = descricaoCurta.orEmpty(),
        descricao = descricao.orEmpty(),
        endereco = endereco.orEmpty(),
        telefone = (telefone1 ?: telefone2).orEmpty(),
        email = email.orEmpty(),
        rating = (avaliacaoUsuario ?: 0),
        fotoBase64 = foto
    )
