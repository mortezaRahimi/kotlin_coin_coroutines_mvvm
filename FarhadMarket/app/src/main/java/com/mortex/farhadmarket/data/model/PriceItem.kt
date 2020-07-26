package com.mortex.farhadmarket.data.model

data class PriceItem(
    val createdAt: String,
    val criterionCurrency: CriterionCurrency,
    val criterionCurrencySymbol: String,
    val currency: Currency,
    val currencySymbol: String,
    val id: Int,
    val swing: String,
    val ticker: String
)