package com.kani.medzone.db


import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("message")
    var message: Message?
) {
    data class Message(
        @SerializedName("notification")
        var notification: Notification?,
        @SerializedName("topic")
        var topic: String?
    ) {
        data class Notification(
            @SerializedName("body")
            var body: String?,
            @SerializedName("title")
            var title: String?
        )
    }
}