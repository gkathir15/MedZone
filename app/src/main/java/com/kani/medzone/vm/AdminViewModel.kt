package com.kani.medzone.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**Created by Guru kathir.J on 23,August,2021 **/
class AdminViewModel (val app: Application) : AndroidViewModel(app) {
    val firestore = Firebase.firestore
}