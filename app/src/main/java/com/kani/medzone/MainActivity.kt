package com.kani.medzone

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.kani.medzone.ui.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var pageAdapter: ViewPagerAdapter? = null
    private var  sharedPreferences:SharedPreferences?=null
    private val datastore: DataStore<Preferences> by preferencesDataStore(name = "MedZone_datastore")
    val TAG = MainActivity::class.java.canonicalName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pageAdapter = ViewPagerAdapter(supportFragmentManager)
        pageAdapter?.addFragment(getString(R.string.title_home),DashboardFragment())
        pageAdapter?.addFragment(getString(R.string.tablet), TabletListFragment())
        pageAdapter?.addFragment(getString(R.string.investigation),InvestigationFragment())
        pageAdapter?.addFragment(getString(R.string.calendar),CalendarFragment())
        pager.adapter = pageAdapter
        tab_layout.setupWithViewPager(pager)

        getAllPermissions()


        sharedPreferences = this.getSharedPreferences("medzone", MODE_PRIVATE)

        showTabsView()

        if(sharedPreferences?.getBoolean(Constants.ISLoggedIN,false) == true&&sharedPreferences?.getBoolean(Constants.isAlarmSET,false)==true)
        {
            startService(Intent(this,MedService::class.java).also {
                it.putExtra(Constants.callFOR,Constants.setNewAlarm)
            })
        }



    }


    override fun onResume() {
        super.onResume()
        if(sharedPreferences?.getBoolean(Constants.ISLoggedIN,false) == true&& BiometricManager.from(this)
                .canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS)
            {
              //  createBiometricPrompt()
            }
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

     fun showTabsView()
    { if(sharedPreferences?.getBoolean(Constants.ISLoggedIN,false) == false){

        pager.visibility = GONE
        tab_layout.visibility = GONE
        root.visibility = VISIBLE
     addFrag(SignUpFragment())

    }else{
        pager.visibility = VISIBLE
        tab_layout.visibility = VISIBLE
        root.visibility = GONE
    }


    }

    fun addFrag(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().add(R.id.root,fragment).commit()
    }
    fun removeFrag(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }

    fun getPreferences(): SharedPreferences? {
        return sharedPreferences
    }
    fun getDataStore(): DataStore<Preferences> {
        return datastore
    }


    private fun createBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.d(TAG, "$errorCode :: $errString")

                    finish() // Because in this app, the negative button allows the user to enter an account password. This is completely optional and your app doesn’t have to do it.

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d(TAG, "Authentication failed for an unknown reason")
                finish()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d(TAG, "Authentication was successful")
                // Proceed with viewing the private encrypted message.

            }
        }

        val biometricPrompt = BiometricPrompt(this, executor, callback)

        val info = BiometricPrompt.PromptInfo.Builder()
        info.setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        info.setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        info.setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
        info.setConfirmationRequired(false)
        info.setTitle("Authenticate")
        info.setSubtitle("Your information is safe with us")
        biometricPrompt.authenticate(info.build())

    }

    fun showNoticeDialog(url:String) {
        // Create an instance of the dialog fragment and show it
        val dialog = ImageDialog()
        dialog.also {
            it.arguments = bundleOf(Pair("URL",url))
        }
        dialog.show(supportFragmentManager, "ImageFragment")
    }


}