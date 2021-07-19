package com.diegolima.codehero.data.network.listpage

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.diegolima.codehero.constants.NEXT
import com.diegolima.codehero.constants.STARTER
import com.diegolima.codehero.data.network.MarvelApi.RetrofitServices
import io.reactivex.disposables.CompositeDisposable
import com.diegolima.codehero.data.network.MarvelApi.model.Character

class PagingDataSource(
    private val retrofitServices: RetrofitServices,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Character>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        val numberOfItems = params.requestedLoadSize
        val requestedPage = STARTER
        val adjacentPage = NEXT
        createObservable(requestedPage, adjacentPage, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + NEXT, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(
        requestedPage: Int,
        adjacentPage: Int,
        requestedLoadSize: Int,
        initialCallback: LoadInitialCallback<Int, Character>?,
        callback: LoadCallback<Int, Character>?
    ) {
        compositeDisposable.add(
            retrofitServices.allCharacters(requestedPage * requestedLoadSize)
                .subscribe {
                    initialCallback?.onResult(it.data.results, null, adjacentPage)
                    callback?.onResult(it.data.results, adjacentPage)
                    it.data.results[0].name
                    it.data.results[0].description
                }
        )
    }
}