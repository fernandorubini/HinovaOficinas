package br.com.fernandodev.hinovaoficinas.core.network

import br.com.fernandodev.hinovaoficinas.data.net.models.EntradaIndicacaoDto
import br.com.fernandodev.hinovaoficinas.data.net.models.IndicacaoResponseDto
import br.com.fernandodev.hinovaoficinas.data.net.models.OficinasResponseDto
import retrofit2.http.*

interface HinovaApi {

    @GET("Api/Oficina")
    suspend fun getOficinas(
        @Query("codigoAssociacao") codigoAssociacao: Int,
        @Query(value = "cpfAssociado", encoded = true) cpfAssociado: String
    ): OficinasResponseDto

    @GET("Api/Oficina/")
    suspend fun getOficinasSlash(
        @Query("codigoAssociacao") codigoAssociacao: Int,
        @Query(value = "cpfAssociado", encoded = true) cpfAssociado: String
    ): OficinasResponseDto

    @GET("api/Oficina")
    suspend fun getOficinasLower(
        @Query("codigoAssociacao") codigoAssociacao: Int,
        @Query(value = "cpfAssociado", encoded = true) cpfAssociado: String
    ): OficinasResponseDto

    @GET("api/Oficina/")
    suspend fun getOficinasLowerSlash(
        @Query("codigoAssociacao") codigoAssociacao: Int,
        @Query(value = "cpfAssociado", encoded = true) cpfAssociado: String
    ): OficinasResponseDto

    @GET
    suspend fun getOficinasRaw(@Url relativeUrl: String): OficinasResponseDto

    @POST("Api/Indicacao")
    suspend fun postIndicacao(
        @Body body: EntradaIndicacaoDto
    ): IndicacaoResponseDto
}
