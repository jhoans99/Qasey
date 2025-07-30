package com.ml.qasey.model.response

import com.google.firebase.firestore.PropertyName

data class User(
    val Rol: String? = null,
    val lastNames: String? = null,
    val names: String? = null,
    @get:PropertyName("isEnabled") @set:PropertyName("isEnabled")
    var isEnabled: Boolean? = null,
    val id: String? = null,
    @get:PropertyName("TokenId") @set:PropertyName("TokenId")
    var tokenNotification: String? = null
) {
    constructor(): this(null,null,null, null, null, null)
}