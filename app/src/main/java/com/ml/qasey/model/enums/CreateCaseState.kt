package com.ml.qasey.model.enums

sealed class CreateCaseState {
    data object CREATE_CASE_SUCCESS: CreateCaseState()
    data object ERROR: CreateCaseState()
}