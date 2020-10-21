package br.com.bandtec.scrolleclienterest_turmaa

import java.math.BigDecimal
//classe com os mesmos nomes e atributos que tem no JSON que vem da api pra "espelhar"
data class Filme(
    val id: Int?,
    val nome: String,
    val custoProducao: BigDecimal,
    val classico : Boolean,
    val ano: Int
)