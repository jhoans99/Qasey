package com.ml.qasey.model.enums

sealed class CreateCaseState {
    data class CreateCaseSuccess(val id: String): CreateCaseState()
    data object ERROR: CreateCaseState()
}