package br.com.fernandodev.hinovaoficinas.ui.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import br.com.fernandodev.hinovaoficinas.core.network.model.Oficina

@Parcelize
data class OficinaUi(
    val id: Int,
    val nome: String,
    val descricao: String?,
    val endereco: String?,
    val telefone1: String?,
    val telefone2: String?,
    val email: String?,
    val fotoBase64: String?
) : Parcelable

fun Oficina.toUi(): OficinaUi = OficinaUi(
    id = id,
    nome = nome,
    descricao = descricao,
    endereco = endereco,
    telefone1 = telefone1,
    telefone2 = telefone2,
    email = email,
    fotoBase64 = fotoBase64
)
