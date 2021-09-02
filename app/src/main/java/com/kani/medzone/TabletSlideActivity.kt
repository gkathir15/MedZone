package com.kani.medzone

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.kani.medzone.db.TabletEntry
import com.kani.medzone.ui.adapter.TabletsNotificationAdapter
import com.kani.medzone.vm.ActivityViewModel
import kotlinx.android.synthetic.main.activity_tablet_slide.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class TabletSlideActivity : AppCompatActivity(),ItemClickListener {
    private val model by viewModels<ActivityViewModel>()
    private  var tabs:ArrayList<TabletEntry>? = null
    val data = HashMap<String,Any>()
    val tabletEntryMap = HashMap<String,Any>()
    val skipped = ArrayList<String>()
    val taken = ArrayList<String>()
    var isWritten:Boolean = false
    var  sharedPreferences:SharedPreferences?=null
    var tabletsNotificationAdapter:TabletsNotificationAdapter?=null
    var duration:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablet_slide)
        sharedPreferences =  getSharedPreferences("medzone", MODE_PRIVATE)
        duration = intent.getStringExtra(Constants.DURATION)
        GlobalScope.launch {
            model.fetchTabletEntry()
            launch(Dispatchers.Main){
             tabs = model.tabEntryList.value?.filter {
                 val time = Calendar.getInstance()
                 time.timeInMillis = it.date!!
                 (it.status == 0 &&
                         (time.get(Calendar.DATE)==Calendar.getInstance().get(Calendar.DATE)&&
                                 time.get(Calendar.MONTH)==(Calendar.getInstance().get(Calendar.MONTH)+1))&&(
                         (duration.equals(Constants.BREAKFAST,true) && it.tablet.morning == 1) ||
                                 (duration.equals( Constants.LUNCH,true) && it.tablet.noon == 1) ||
                                 (duration.equals( Constants.EVENING,true)&& it.tablet.evening == 1) ||
                                 (duration.equals(Constants.DINNER,true) && it.tablet.night == 1)
                         ))
             } as ArrayList<TabletEntry>?

                tabs = tabs?.toSet()?.toList() as ArrayList<TabletEntry>


            tabletPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                tabletsNotificationAdapter=  TabletsNotificationAdapter(tabs as ArrayList<TabletEntry>, this@TabletSlideActivity)
            tabletPager2.adapter = tabletsNotificationAdapter
        }}

        doneBtn.setOnClickListener {
            writeToFirebase()
            isWritten = true
            finish()
        }
    }

    private fun writeToFirebase() {

            if(skipped.isNotEmpty()){
            tabletEntryMap[Constants.SKIP] = skipped}
            if(taken.isNotEmpty()){
            tabletEntryMap[Constants.TAKE] = taken}

       val durationMap = hashMapOf<String,Any>()
        durationMap[duration!!] = tabletEntryMap
        val dateMap = hashMapOf<String,Any>()
        dateMap[
                DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US)
                    .format(Date(System.currentTimeMillis())).toString()] = durationMap
        val tabmap = hashMapOf<String,Any>()
        tabmap.put(Constants.TABLETS,dateMap)
            model.firestore.collection(Constants.users)
                .document(sharedPreferences?.getString(Constants.PHONE_NUMBER,"")!!).set(tabmap,
                    SetOptions.merge())
        }


    override fun onStop() {
        super.onStop()
        if(!isWritten)
        {
            writeToFirebase()
        }
    }

    override fun expandImageClicked(url: ByteArray?) {
    }

    override fun takeTabletsClicked(btnType: String) {
    }

    override fun takeTablet(tab: TabletEntry, pos:Int) {
        tab.also {
            it.status=1
            it.tablet.available = it.tablet.available?.minus(1)
            tabs?.removeAt(pos)
            if(tabs?.size==0)
            {
                doneBtn.visibility = View.VISIBLE
                tabletPager2.visibility = View.GONE
            }

            tabletsNotificationAdapter?.notifyItemRemoved(pos)
            tab.tablet.name?.let { it1 -> taken.add(it1) }
        }
        GlobalScope.launch {
            model.editTablet(tab.tablet)
            model.editTabletEntry(tab)
        }
    }

    override fun skipTablet(tab: TabletEntry, pos:Int) {
        tab.also {
            it.status=2
            tabs?.removeAt(pos)
            if(tabs?.size==0)
            {
                doneBtn.visibility = View.VISIBLE
                tabletPager2.visibility = View.GONE
            }
            tabletsNotificationAdapter?.notifyItemRemoved(pos)
            tab.tablet.name?.let { it1 -> skipped.add(it1) }
        }
        GlobalScope.launch {
            model.editTabletEntry(tab)
        }
    }

    override fun personSelected(snap: DocumentSnapshot) {
        TODO("Not yet implemented")
    }
}