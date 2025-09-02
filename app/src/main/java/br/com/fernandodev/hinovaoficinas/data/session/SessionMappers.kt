package br.com.fernandodev.hinovaoficinas.data.session

import br.com.fernandodev.hinovaoficinas.domain.model.User

fun LoginUser.toDomain(): User =
    User(
        id = Id,
        name = Nome,
        code = Codigo_mobile,
        cpf = Cpf,
        email = Email,
        status = Situacao,
        phone = Telefone
    )
