package com.kani.medzone

import android.content.Intent
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**Created by Guru kathir.J on 29,August,2021 **/
class FCMMessengingService: FirebaseMessagingService() {
    override fun handleIntent(intent: Intent) {
        super.handleIntent(intent)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
}