package com.ml.qasey.data.datasource

import com.ml.qasey.data.datasource.network.PusNotificationService
import com.ml.qasey.model.request.NotificationData
import com.ml.qasey.model.request.PushNotificationRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDataSource @Inject constructor(
    private val apiService: PusNotificationService
) {

    // Cache para evitar notificaciones duplicadas
    private val sentNotificationsCache = mutableSetOf<String>()

    suspend fun sendPushNotification(
        idSend: String, 
        title: String, 
        message: String,
        data: Map<String, String>? = null,
        preventDuplicates: Boolean = true
    ): Boolean {
        
        // Crear clave única para esta notificación
        val notificationKey = "${idSend}_${title}_${message}"
        
        // Verificar si ya se envió esta notificación (si está habilitada la prevención)
        if (preventDuplicates && sentNotificationsCache.contains(notificationKey)) {
            return false // No enviar notificación duplicada
        }

        try {
            val notification = PushNotificationRequest(
                to = idSend,
                notification = NotificationData(
                    title = title,
                    body = message,
                    tag = "qasey_single", // Tag para notificaciones únicas
                    clickAction = "FLUTTER_NOTIFICATION_CLICK"
                ),
                data = data,
                collapseKey = "qasey_notification", // Colapsar notificaciones similares
                priority = "high"
            )
            
            val response = apiService.sendNotification(notification)
            
            if (response.isSuccessful) {
                // Agregar al cache solo si el envío fue exitoso
                if (preventDuplicates) {
                    sentNotificationsCache.add(notificationKey)
                    
                    // Limpiar cache si se vuelve muy grande (mantener solo las últimas 50)
                    if (sentNotificationsCache.size > 50) {
                        val toRemove = sentNotificationsCache.take(sentNotificationsCache.size - 50)
                        sentNotificationsCache.removeAll(toRemove.toSet())
                    }
                }
                return true
            }
            
        } catch (e: Exception) {
            // Log del error si es necesario
            e.printStackTrace()
        }
        
        return false
    }

    /**
     * Envía una notificación única (reemplaza cualquier notificación anterior)
     */
    suspend fun sendUniqueNotification(
        idSend: String,
        title: String,
        message: String,
        data: Map<String, String>? = null
    ): Boolean {
        // Limpiar cache para este usuario para forzar notificación única
        sentNotificationsCache.removeAll { it.startsWith("${idSend}_") }
        
        return sendPushNotification(
            idSend = idSend,
            title = title,
            message = message,
            data = data,
            preventDuplicates = false
        )
    }

    /**
     * Limpia el cache de notificaciones enviadas
     */
    fun clearNotificationCache() {
        sentNotificationsCache.clear()
    }
}