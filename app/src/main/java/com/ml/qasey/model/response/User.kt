package com.ml.qasey.model.response

data class User(
    val Rol: String? = null,
    val lastNames: String? = null,
    val names: String? = null
) {
    constructor(): this(null,null,null)
}