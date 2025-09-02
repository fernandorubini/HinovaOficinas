package br.com.fernandodev.hinovaoficinas.core.network.model

/** Modelo de domínio/UI */
data class Oficina(
    val id: Int,
    val nome: String,
    val descricao: String,
    val descricaoCurta: String?,
    val endereco: String?,
    val latitude: Double?,
    val longitude: Double?,
    val fotoBase64: String?,
    val avaliacaoUsuario: Int?,
    val codigoAssociacao: Int?,
    val email: String?,
    val telefone1: String?,
    val telefone2: String?,
    val ativo: Boolean?
)

/** Mapper único (use SEMPRE este nome) */
fun OficinaDto.asModel(): Oficina = Oficina(
    id = id ?: 0,
    nome = nome.orEmpty(),
    descricao = descricao.orEmpty(),
    descricaoCurta = descricaoCurta,
    endereco = endereco,
    latitude = latitude?.toDoubleOrNull(),
    longitude = longitude?.toDoubleOrNull(),
    fotoBase64 = foto,
    avaliacaoUsuario = avaliacaoUsuario,
    codigoAssociacao = codigoAssociacao,
    email = email,
    telefone1 = telefone1,
    telefone2 = telefone2,
    ativo = ativo
)
