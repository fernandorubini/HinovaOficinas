package br.com.fernandodev.hinovaoficinas.data.net.models

import com.google.gson.annotations.SerializedName

data class EntradaIndicacaoDto(
    @SerializedName("Indicacao") val indicacao: IndicacaoDto,
    @SerializedName("Remetente") val remetente: String,
    @SerializedName("Copias") val copias: List<String> = emptyList()
)

data class IndicacaoDto(
    @SerializedName("CodigoAssociacao") val codigoAssociacao: Int,
    @SerializedName("DataCriacao") val dataCriacao: String?,      // yyyy-MM-dd
    @SerializedName("CpfAssociado") val cpfAssociado: String?,
    @SerializedName("EmailAssociado") val emailAssociado: String?,
    @SerializedName("NomeAssociado") val nomeAssociado: String?,
    @SerializedName("TelefoneAssociado") val telefoneAssociado: String?,
    @SerializedName("PlacaVeiculoAssociado") val placaVeiculoAssociado: String?,
    @SerializedName("NomeAmigo") val nomeAmigo: String,
    @SerializedName("TelefoneAmigo") val telefoneAmigo: String,
    @SerializedName("EmailAmigo") val emailAmigo: String,
    @SerializedName("Observacao") val observacao: String?
)

data class IndicacaoResponseDto(
    @SerializedName("RetornoErro") val retornoErro: RetornoErroDto? = null, // usa o já definido em OficinasResponseDto.kt
    @SerializedName("Sucesso") val sucesso: String? = null
)

fun IndicacaoResponseDto.toMessage(): String =
    sucesso ?: retornoErro?.retornoErro ?: "Indicação enviada com sucesso!"
