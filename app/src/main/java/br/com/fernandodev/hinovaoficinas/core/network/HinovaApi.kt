// app/src/main/java/br/com/fernandodev/hinovaoficinas/core/network/HinovaApi.kt
package br.com.fernandodev.hinovaoficinas.core.network

import br.com.fernandodev.hinovaoficinas.data.net.models.EntradaIndicacaoDto
import br.com.fernandodev.hinovaoficinas.data.net.models.IndicacaoResponseDto
import br.com.fernandodev.hinovaoficinas.data.net.models.OficinasResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HinovaApi {

    // Maiúsculo
    @GET("Api/Oficina")
    suspend fun getOficinas(
        @Query("codigoAssociacao") codigoAssociacao: Int,
        @Query("cpfAssociado") cpfAssociado: String
    ): OficinasResponseDto

    @GET("Api/Oficina/")
    suspend fun getOficinasSlash(
        @Query("codigoAssociacao") codigoAssociacao: Int,
        @Query("cpfAssociado") cpfAssociado: String
    ): OficinasResponseDto

    // Minúsculo (fallback extra)
    @GET("api/Oficina")
    suspend fun getOficinasLower(
        @Query("codigoAssociacao") codigoAssociacao: Int,
        @Query("cpfAssociado") cpfAssociado: String
    ): OficinasResponseDto

    @GET("api/Oficina/")
    suspend fun getOficinasLowerSlash(
        @Query("codigoAssociacao") codigoAssociacao: Int,
        @Query("cpfAssociado") cpfAssociado: String
    ): OficinasResponseDto

    // Indicação
    @POST("Api/Indicacao")
    suspend fun postIndicacao(
        @Body body: EntradaIndicacaoDto
    ): IndicacaoResponseDto
}
