package com.ml.qasey.model.request

import com.google.gson.annotations.SerializedName

data class PushNotificationRequest(
    @SerializedName("to")
    val to: String,
    @SerializedName("notification")
    val notification: NotificationData,
    @SerializedName("data")
    val data: Map<String, String>? = null,
    @SerializedName("collapse_key")
    val collapseKey: String? = "qasey_notification", // Clave para colapsar notificaciones similares
    @SerializedName("priority")
    val priority: String = "high"
)

data class NotificationData(
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("tag")
    val tag: String? = "qasey_single", // Tag para identificar notificaciones únicas
    @SerializedName("sound")
    val sound: String = "default",
    @SerializedName("click_action")
    val clickAction: String? = null
)
