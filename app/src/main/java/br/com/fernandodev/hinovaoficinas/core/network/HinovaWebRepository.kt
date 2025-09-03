package br.com.fernandodev.hinovaoficinas.core.network

import android.util.Log
import br.com.fernandodev.hinovaoficinas.data.net.models.toDomain
import br.com.fernandodev.hinovaoficinas.domain.model.Oficina
import retrofit2.HttpException

class HinovaWebRepository(private val api: HinovaApi) {

    private suspend fun tryUrl(relativeUrl: String): List<Oficina>? {
        return try {
            Log.d("OficinasURL", "GET $relativeUrl")
            val dto = api.getOficinasRaw(relativeUrl)
            val items = dto.lista.orEmpty().map { it.toDomain() }
            Log.d("OficinasURL", "OK ${items.size} itens")
            items
        } catch (e: HttpException) {
            Log.w("OficinasURL", "GET $relativeUrl -> HTTP ${e.code()}")
            null
        } catch (e: Throwable) {
            Log.e("OficinasURL", "GET $relativeUrl -> ${e.javaClass.simpleName}: ${e.message}")
            throw e
        }
    }


    suspend fun getOficinas(
        codigoAssociacao: Int,
        cpfAssociado: String? = null
    ): Result<List<Oficina>> = runCatching {
        val paths = listOf("Api/Oficina", "Api/Oficina/", "api/Oficina", "api/Oficina/")
        val cpfCandidates = buildList {
            if (!cpfAssociado.isNullOrBlank()) add(cpfAssociado)
            add("\"\"")
            add("")
            add("%22%22")
        }

        for (cpf in cpfCandidates) {
            try {
                Log.d("OficinasFix", "Api/Oficina ? codigoAssociacao=$codigoAssociacao & cpf=$cpf")
                return@runCatching api.getOficinas(codigoAssociacao, cpf).lista
                    .orEmpty().map { it.toDomain() }
            } catch (e: HttpException) {
                if (e.code() != 404) throw e
                Log.w("OficinasFix", "Api/Oficina -> 404")
            }

            try {
                Log.d("OficinasFix", "Api/Oficina/ ? codigoAssociacao=$codigoAssociacao & cpf=$cpf")
                return@runCatching api.getOficinasSlash(codigoAssociacao, cpf).lista
                    .orEmpty().map { it.toDomain() }
            } catch (e: HttpException) {
                if (e.code() != 404) throw e
                Log.w("OficinasFix", "Api/Oficina/ -> 404")
            }

            try {
                Log.d("OficinasFix", "api/Oficina ? codigoAssociacao=$codigoAssociacao & cpf=$cpf")
                return@runCatching api.getOficinasLower(codigoAssociacao, cpf).lista
                    .orEmpty().map { it.toDomain() }
            } catch (e: HttpException) {
                if (e.code() != 404) throw e
                Log.w("OficinasFix", "api/Oficina -> 404")
            }

            try {
                Log.d("OficinasFix", "api/Oficina/ ? codigoAssociacao=$codigoAssociacao & cpf=$cpf")
                return@runCatching api.getOficinasLowerSlash(codigoAssociacao, cpf).lista
                    .orEmpty().map { it.toDomain() }
            } catch (e: HttpException) {
                if (e.code() != 404) throw e
                Log.w("OficinasFix", "api/Oficina/ -> 404")
            }
        }

        for (p in paths) {
            for (cpf in cpfCandidates) {
                val url = "$p?codigoAssociacao=$codigoAssociacao&cpfAssociado=$cpf"
                val ok = tryUrl(url)
                if (ok != null) return@runCatching ok
            }
        }

        throw HttpException(
            retrofit2.Response.error<Any>(
                404,
                okhttp3.ResponseBody.create(null, ByteArray(0))
            )
        )
    }.onFailure {
        Log.e("HinovaWebRepository", "Falha ao buscar oficinas", it)
    }

    suspend fun enviarIndicacao(entrada: br.com.fernandodev.hinovaoficinas.data.net.models.EntradaIndicacaoDto): Result<String> =
        runCatching {
            val resp = api.postIndicacao(entrada)
            resp.sucesso ?: resp.retornoErro?.retornoErro ?: "Indicação enviada com sucesso!"
        }
}
