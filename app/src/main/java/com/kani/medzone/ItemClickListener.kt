package com.kani.medzone

import com.google.firebase.firestore.DocumentSnapshot
import com.kani.medzone.db.TabletEntry

/**Created by Guru kathir.J on 22,May,2021 **/
abstract interface ItemClickListener {

    fun expandImageClicked(url: ByteArray?)

    fun takeTabletsClicked(btnType:String)

    fun takeTablet(tab:TabletEntry,pos:Int)
    fun skipTablet(tab:TabletEntry,pos:Int)

    fun personSelected(snap:DocumentSnapshot)
}