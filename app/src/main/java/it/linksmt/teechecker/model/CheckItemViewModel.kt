package it.linksmt.teechecker.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import it.linksmt.teechecker.util.CheckItemsResult

class CheckItemViewModel : ViewModel() {
    val result : MutableLiveData<CheckItemsResult> = MutableLiveData()
}