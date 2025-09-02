// File: app/src/main/java/br/com/fernandodev/hinovaoficinas/data/net/models/IndicationModels.kt
package br.com.fernandodev.hinovaoficinas.data.net.models

import com.google.gson.annotations.SerializedName

/** Envelope exigido pelo endpoint /Api/Indicacao */
data class EntradaIndicacaoDto(
    @SerializedName("Indicacao") val indicacao: IndicacaoDto,
    @SerializedName("Remetente") val remetente: String,
    @SerializedName("Copias")    val copias: List<String> = emptyList()
)

/** Objeto interno "Indicacao" do payload */
data class IndicacaoDto(
    @SerializedName("CodigoAssociacao")      val codigoAssociacao: Int,
    @SerializedName("DataCriacao")           val dataCriacao: String?,      // yyyy-MM-dd
    @SerializedName("CpfAssociado")          val cpfAssociado: String?,
    @SerializedName("EmailAssociado")        val emailAssociado: String?,
    @SerializedName("NomeAssociado")         val nomeAssociado: String?,
    @SerializedName("TelefoneAssociado")     val telefoneAssociado: String?,
    @SerializedName("PlacaVeiculoAssociado") val placaVeiculoAssociado: String?,
    @SerializedName("NomeAmigo")             val nomeAmigo: String,
    @SerializedName("TelefoneAmigo")         val telefoneAmigo: String,
    @SerializedName("EmailAmigo")            val emailAmigo: String,
    @SerializedName("Observacao")            val observacao: String?
)

/** Resposta do endpoint */
data class IndicacaoResponseDto(
    @SerializedName("RetornoErro") val retornoErro: RetornoErroDto? = null, // usa o já definido em OficinasResponseDto.kt
    @SerializedName("Sucesso")     val sucesso: String? = null
)

/** Mensagem final conveniente */
fun IndicacaoResponseDto.toMessage(): String =
    sucesso ?: retornoErro?.retornoErro ?: "Indicação enviada com sucesso!"
