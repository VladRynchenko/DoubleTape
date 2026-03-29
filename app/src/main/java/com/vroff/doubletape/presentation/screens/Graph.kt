package com.vroff.doubletape.presentation.screens

import kotlinx.serialization.Serializable

@Serializable
sealed class Graph {
    @Serializable
    data object Main : Graph()

    @Serializable
    data object Search : Graph()
}
