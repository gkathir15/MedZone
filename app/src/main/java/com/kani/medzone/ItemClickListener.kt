package com.kani.medzone

/**Created by Guru kathir.J on 22,May,2021 **/
interface ItemClickListener {

    fun expandImageClicked(url: ByteArray?)

    fun takeTabletsClicked(btnType:String)
}