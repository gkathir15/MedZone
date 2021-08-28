package com.kani.medzone.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kani.medzone.Constants
import com.kani.medzone.R
import com.kani.medzone.ui.adapter.DocumentAdapter
import com.kani.medzone.vm.AdminViewModel
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.fragment_add_investigation.*
import kotlinx.android.synthetic.main.fragment_add_investigation.recycler

class AdminActivity : AppCompatActivity() {
    private val model by viewModels<AdminViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val ref = model.firestore.collection(Constants.users)

        userRecycler.layoutManager = LinearLayoutManager(this)

        ref.get().addOnCompleteListener {
            val docs = it.result?.documents
            userRecycler.adapter = docs?.let { it1 -> DocumentAdapter(it1) }

        }
    }
}