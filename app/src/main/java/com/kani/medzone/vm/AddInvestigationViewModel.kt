package com.kani.medzone.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kani.medzone.db.Reading

/**Created by Guru kathir.J on 31,May,2021 **/
class AddInvestigationViewModel: ViewModel() {

    val investigationList = MutableLiveData<ArrayList<Reading>>()
}