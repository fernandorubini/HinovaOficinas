package br.com.fernandodev.hinovaoficinas.core.database

enum class ItemKind { PART, LABOR, SERVICE }

enum class ServiceOrderStatus { OPEN, IN_PROGRESS, WAITING_PARTS, READY, DELIVERED, CANCELED }

enum class PaymentMethod { PIX, CARD, CASH, OTHER }
