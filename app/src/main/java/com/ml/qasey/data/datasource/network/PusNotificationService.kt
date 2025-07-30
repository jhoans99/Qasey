package com.ml.qasey.data.datasource.network

import com.ml.qasey.model.request.PushNotificationRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PusNotificationService {

    @Headers(
        "Content-Type: application/json",
        "Authorization: key=TU_SERVER_KEY"
    )
    @POST("send")
     suspend fun sendNotification(
        @Body request: PushNotificationRequest
    ): Response<ResponseBody>
}