package br.com.fernandodev.hinovaoficinas.core.network.model

import com.google.gson.annotations.SerializedName

data class IndicationResponse(
    @SerializedName("RetornoErro") val error: ApiErrorWrapper?,
    @SerializedName("Sucesso")     val success: String?
)
