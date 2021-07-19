package com.diegolima.codehero.data.network.listpage

import androidx.paging.DataSource
import com.diegolima.codehero.data.network.MarvelApi.RetrofitServices
import io.reactivex.disposables.CompositeDisposable
import com.diegolima.codehero.data.network.MarvelApi.model.Character

class Factory(
    private val compositeDisposable: CompositeDisposable,
    private val retrofitServices: RetrofitServices
) : DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {

        return PagingDataSource(retrofitServices, compositeDisposable)

    }
}