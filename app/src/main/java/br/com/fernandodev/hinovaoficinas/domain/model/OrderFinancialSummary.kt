package br.com.fernandodev.hinovaoficinas.domain.model


import java.math.BigDecimal

data class OrderFinancialSummary(
    val itemsTotal: BigDecimal,
    val discount: BigDecimal,
    val paymentsTotal: BigDecimal,
    val totalDue: BigDecimal,
    val outstanding: BigDecimal
)
