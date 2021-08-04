package com.kani.medzone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.kani.medzone.db.TabletEntry
import com.kani.medzone.ui.adapter.TabletsNotificationAdapter
import com.kani.medzone.vm.ActivityViewModel
import kotlinx.android.synthetic.main.activity_tablet_slide.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TabletSlideActivity : AppCompatActivity(),ItemClickListener {
    private val model by viewModels<ActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablet_slide)
        val duration = intent.getStringExtra(Constants.DURATION)
        GlobalScope.launch {
            model.fetchTabletEntry()
        }
        val tabs = model.tabEntryList.value?.filter {
            (it.status==0&&(
                    ( (duration==Constants.BREAKFAST)&& it.tablet.morning==1)||
                            ((duration==Constants.LUNCH)&& it.tablet.noon==1)||
                            ((duration==Constants.EVENING)&& it.tablet.evening==1)||
                            ((duration==Constants.DINNER)&& it.tablet.night==1)
                    ))
        }


        tabletPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        tabletPager2.adapter = TabletsNotificationAdapter(tabs as ArrayList<TabletEntry>,this)

    }

    override fun expandImageClicked(url: ByteArray?) {
    }

    override fun takeTabletsClicked(btnType: String) {
    }

    override fun takeTablet(tab: TabletEntry) {
        tab.also {
            it.status=1
            it.tablet.available = it.tablet.available?.minus(1)
        }
        GlobalScope.launch {
            model.editTablet(tab.tablet)
            model.editTabletEntry(tab)
        }
    }

    override fun skipTablet(tab: TabletEntry) {
        tab.also {
            it.status=2
        }
        GlobalScope.launch {
            model.editTabletEntry(tab)
        }
    }
}