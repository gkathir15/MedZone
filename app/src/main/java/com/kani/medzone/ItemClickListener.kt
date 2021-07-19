package com.kani.medzone

import com.kani.medzone.db.TabletEntry

/**Created by Guru kathir.J on 22,May,2021 **/
abstract interface ItemClickListener {

    fun expandImageClicked(url: ByteArray?)

    fun takeTabletsClicked(btnType:String)

    fun takeTablet(tab:TabletEntry)
    fun skipTablet(tab:TabletEntry)
}