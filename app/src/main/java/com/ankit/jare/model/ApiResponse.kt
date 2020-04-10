package com.ankit.jare.model

data class ListResponse(
        val title: String,
        val rows: List<rows>
)

data class rows(
        val title: String,
        val description: String,
        val imageHref: String
)