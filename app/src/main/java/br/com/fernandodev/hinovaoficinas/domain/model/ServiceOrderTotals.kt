package br.com.fernandodev.hinovaoficinas.domain.model

import java.math.BigDecimal

data class ServiceOrderTotals(
    val items: BigDecimal,
    val payments: BigDecimal
)
