package com.example.controlward

data class DisasterModel(
    val id: String,
    val userId: String,
    val text: String,
    val image: String,
    val location: List<Double>,
    val category: String,
    val accuracy: String
)

data class PostRequest(
    val userId: String,
    val text: String,
    val image: String,
    val location: List<Double>
)