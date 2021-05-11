package com.kani.medzone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.kani.medzone.ui.TabletListFragment
import com.kani.medzone.ui.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private var pageAdapter: ViewPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pageAdapter = ViewPagerAdapter(supportFragmentManager)


        pageAdapter?.addFragment(AppConstants.TABLETS1, TabletListFragment())
        pageAdapter?.addFragment(AppConstants.TABLETS2, TabletListFragment())
        pageAdapter?.addFragment(AppConstants.TABLETS, TabletListFragment())


        pager.adapter = pageAdapter

        getAllPermissions()

    }

    private fun getAllPermissions()
    {
        ActivityCompat.requestPermissions(this, arrayOf(
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
        ),557)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

       // if()
    }


}