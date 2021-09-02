package com.kani.medzone.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.messaging.FirebaseMessaging
import com.kani.medzone.Constants
import com.kani.medzone.ItemClickListener
import com.kani.medzone.R
import com.kani.medzone.db.TabletEntry
import com.kani.medzone.ui.adapter.DocumentAdapter
import com.kani.medzone.vm.AdminViewModel
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity(),ItemClickListener {
    private val model by viewModels<AdminViewModel>()
    var sharedPref:SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        sharedPref = getSharedPreferences("medzone", MODE_PRIVATE)
        if(!sharedPref?.getBoolean(Constants.ISGCMIDSENT,false)!!)
        {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    val toeknmap = hashMapOf<String,String>()
                    token?.let {
                    toeknmap[Constants.GCMIDS] = it

                         model.firestore.collection(Constants.GCMIDS).add(toeknmap) }
                }
            }
        }


        val ref = model.firestore.collection(Constants.users)

        userRecycler.layoutManager = LinearLayoutManager(this)

        ref.get().addOnCompleteListener {
            val docs = it.result?.documents
            userRecycler.adapter = docs?.let { it1 -> DocumentAdapter(it1,this) }



        }
    }

    override fun expandImageClicked(url: ByteArray?) {
        TODO("Not yet implemented")
    }

    override fun takeTabletsClicked(btnType: String) {
        TODO("Not yet implemented")
    }

    override fun takeTablet(tab: TabletEntry, pos: Int) {
        TODO("Not yet implemented")
    }

    override fun skipTablet(tab: TabletEntry, pos: Int) {
        TODO("Not yet implemented")
    }

    override fun personSelected(snap: DocumentSnapshot) {
        Constants.usermap = snap.data
        startActivity(Intent(this,DetailAdminActivity::class.java))

    }
}