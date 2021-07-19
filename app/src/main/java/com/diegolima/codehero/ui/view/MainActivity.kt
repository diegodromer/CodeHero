package com.diegolima.codehero.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegolima.codehero.R
import com.diegolima.codehero.constants.*
import com.diegolima.codehero.ui.adapter.DataAdapter
import com.diegolima.codehero.viewmodel.DataViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_characters.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener,
    BottomNavigationView.OnNavigationItemReselectedListener {

    lateinit var searchView: SearchView

    private var actionConnectivity: Boolean = true
    private var actionConnectivityInitial: Boolean = false

    private val viewModel: DataViewModel by lazy {
        ViewModelProviders.of(this).get(DataViewModel::class.java)
    }

    private val dataAdapter: DataAdapter by lazy {
        DataAdapter()
    }

    private var recyclerStage: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CodeHero)
        setContentView(R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.setHasFixedSize(true)

        getUserData()
    }

    private fun getUserData() {
        var adapter = dataAdapter
        recycler_view.adapter = adapter
        adapter.setOnItemClickListener(object : DataAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, DetailsHeroActivity::class.java)
                startActivity(intent)
            }
        })
        subscribeList()
        setOnListener()
    }

    override fun onStart() {
        super.onStart()
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                ACCESS_NETWORK_STATE,
                Snackbar.LENGTH_SHORT
            )
                .setTextColor(resources.getColor(R.color.white))
                .setBackgroundTint(resources.getColor(R.color.marvel_dark))
                .show()
            if (!actionConnectivityInitial) {
                actionConnectivity = false
                nav_bottom.visibility = View.INVISIBLE
            }
        }

        if (actionConnectivity) {
            nav_bottom.visibility = View.VISIBLE
            actionConnectivity = true
            actionConnectivityInitial = true
        }
    }

    private fun setOnListener() {
        search_view.setOnClickListener(this)
        button_nav_left.setOnClickListener(this)
        button_nav_right.setOnClickListener(this)
        text_nav_one.setOnClickListener(this)
        text_nav_two.setOnClickListener(this)
        text_nav_three.setOnClickListener(this)
        nav_bottom.setOnClickListener(this)
    }

    private fun searchHero() {
        try {
        } catch (e: Exception) {
            Log.e(MONITOR, "Erro ao carregar pesquisa: $e")
        }
    }

    override fun onNavigationItemReselected(item: MenuItem) {
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STAGE, recycler_view.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerStage = savedInstanceState.getParcelable(KEY_STAGE)
    }

    @SuppressLint("CheckResult")
    private fun subscribeList() {
        viewModel.listObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                dataAdapter.submitList(it)
                if (recyclerStage != null) {
                    recycler_view.layoutManager?.onRestoreInstanceState(recyclerStage)
                    recyclerStage = null
                }
            }
    }

    override fun onClick(v: View?) {
        val textNavOne: TextView = findViewById(R.id.text_nav_one)
        val textNavTwo: TextView = findViewById<TextView>(R.id.text_nav_two)
        val textnavThree: TextView = findViewById<TextView>(R.id.text_nav_three)
        if (v != null) {
            when (v.id) {
                R.id.search_view -> {
                    searchHero()
                }
                R.id.text_nav_one -> {
                    textNavOne.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_circle_red))
                    textNavOne.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.white
                        )
                    )

                    textNavTwo.setBackgroundDrawable(resources.getDrawable(R.color.white))
                    textNavTwo.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.marvel_primary
                        )
                    )

                    textnavThree.setBackgroundDrawable(resources.getDrawable(R.color.white))
                    textnavThree.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.marvel_primary
                        )
                    )
                }
                R.id.text_nav_two -> {
                    textNavTwo.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_circle_red))
                    textNavTwo.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.white
                        )
                    )

                    textNavOne.setBackgroundDrawable(resources.getDrawable(R.color.white))
                    textNavOne.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.marvel_primary
                        )
                    )

                    textnavThree.setBackgroundDrawable(resources.getDrawable(R.color.white))
                    textnavThree.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.marvel_primary
                        )
                    )
                }
                R.id.text_nav_three -> {
                    textnavThree.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_circle_red))
                    textnavThree.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.white
                        )
                    )

                    textNavOne.setBackgroundDrawable(resources.getDrawable(R.color.white))
                    textNavOne.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.marvel_primary
                        )
                    )

                    textNavTwo.setBackgroundDrawable(resources.getDrawable(R.color.white))
                    textNavTwo.setTextColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.marvel_primary
                        )
                    )
                }
            }
        }
    }

}

