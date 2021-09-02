package com.kani.medzone.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.kani.medzone.Constants
import com.kani.medzone.ItemClickListener
import com.kani.medzone.databinding.ActivityDetailAdminBinding
import com.kani.medzone.db.TabletEntry
import com.kani.medzone.ui.adapter.AdminDetailAdapter

class DetailAdminActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var binding: ActivityDetailAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//val ref =
//        Firebase.firestore.collection(Constants.users).get().addOnCompleteListener {
//            val docs = it.result?.documents
//            userRecycler.adapter = docs?.let { it1 -> DocumentAdapter(it1, this) }
//
//        }



        binding = ActivityDetailAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dateList = (Constants.usermap?.get(Constants.TABLETS) as HashMap<String, Any>)


        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = AdminDetailAdapter(dateList)






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

    }


}