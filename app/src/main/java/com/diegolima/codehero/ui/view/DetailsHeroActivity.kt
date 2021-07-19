package com.diegolima.codehero.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.diegolima.codehero.R
import kotlinx.android.synthetic.main.activity_details_hero.*

class DetailsHeroActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_hero)

        setOnListener()

    }

    private fun setOnListener() {
        button_close.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.button_close -> {
                    val intent = Intent(this@DetailsHeroActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}