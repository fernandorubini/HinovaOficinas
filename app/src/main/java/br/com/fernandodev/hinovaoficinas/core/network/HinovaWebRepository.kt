// app/src/main/java/br/com/fernandodev/hinovaoficinas/core/network/HinovaWebRepository.kt
package br.com.fernandodev.hinovaoficinas.core.network

import android.util.Log
import br.com.fernandodev.hinovaoficinas.data.net.models.EntradaIndicacaoDto
import br.com.fernandodev.hinovaoficinas.data.net.models.toDomain
import br.com.fernandodev.hinovaoficinas.domain.model.Oficina
import retrofit2.HttpException

class HinovaWebRepository(private val api: HinovaApi) {

    /** Busca Oficinas testando variações de rota e do parâmetro cpfAssociado */
    suspend fun getOficinas(
        codigoAssociacao: Int,
        cpfAssociado: String? = null
    ): Result<List<Oficina>> = runCatching {
        val cpfCandidates = if (cpfAssociado.isNullOrBlank()) listOf("", "\"\"") else listOf(cpfAssociado)
        val callers: List<suspend (Int, String) -> br.com.fernandodev.hinovaoficinas.data.net.models.OficinasResponseDto> =
            listOf(
                { c, p -> api.getOficinas(c, p) },
                { c, p -> api.getOficinasSlash(c, p) },
                { c, p -> api.getOficinasLower(c, p) },
                { c, p -> api.getOficinasLowerSlash(c, p) },
            )

        var lastError: Throwable? = null
        for (p in cpfCandidates) {
            for ((idx, call) in callers.withIndex()) {
                try {
                    Log.d("HinovaWebRepository", "Tentando Oficinas [rota=$idx, cpf='$p']")
                    val dto = call(codigoAssociacao, p)
                    val items = dto.lista?.map { it.toDomain() } ?: emptyList()
                    return@runCatching items
                } catch (e: HttpException) {
                    lastError = e
                    Log.w("HinovaWebRepository", "HTTP ${e.code()} [rota=$idx, cpf='$p']")
                } catch (t: Throwable) {
                    lastError = t
                    Log.w("HinovaWebRepository", "Erro: ${t.message} [rota=$idx, cpf='$p']")
                }
            }
        }
        throw lastError ?: IllegalStateException("Não foi possível consultar Oficinas.")
    }.onFailure { Log.e("HinovaWebRepository", "Falha ao buscar oficinas", it) }

    /** Wrapper para o POST /Api/Indicacao  */
    suspend fun enviarIndicacao(body: EntradaIndicacaoDto): Result<String> = runCatching {
        val resp = api.postIndicacao(body)
        // mensagem padrão se vier null
        resp.sucesso ?: resp.retornoErro?.retornoErro ?: "Indicação enviada com sucesso!"
    }
}
