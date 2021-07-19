package com.diegolima.codehero.viewmodel

import android.os.Build
import android.view.View
import android.widget.ScrollView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.diegolima.codehero.data.network.MarvelApi.RetrofitServices
import com.diegolima.codehero.data.network.MarvelApi.model.Character
import com.diegolima.codehero.data.network.listpage.Factory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@RequiresApi(Build.VERSION_CODES.M)
class DataViewModel : ViewModel() {

    var listObservable: Observable<PagedList<Character>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 4

    private val factory: Factory = Factory(compositeDisposable, RetrofitServices.getService())

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(4)
            .setInitialLoadSizeHint(pageSize)
            .setPrefetchDistance(ScrollView.SCROLL_INDICATOR_BOTTOM)
            .setEnablePlaceholders(false)
            .build()

        listObservable = RxPagedListBuilder(factory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
