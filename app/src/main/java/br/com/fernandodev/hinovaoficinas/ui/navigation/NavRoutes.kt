package br.com.fernandodev.hinovaoficinas.ui.navigation

object NavRoutes {
    const val OS_LIST = "os_list"
    const val OS_DETAIL = "os_detail"
    const val ARG_OS_ID = "os_id"

    const val OS_DETAIL_ROUTE_PATTERN = "$OS_DETAIL/{$ARG_OS_ID}"

    fun osDetail(id: Long) = "$OS_DETAIL/$id"
}
