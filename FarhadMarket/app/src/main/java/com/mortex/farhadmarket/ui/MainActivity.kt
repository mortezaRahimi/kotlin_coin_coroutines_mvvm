package com.mortex.farhadmarket.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mortex.farhadmarket.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.round
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.allPrices.observe(this, Observer {
            Toast.makeText(this, it.size.toString(), Toast.LENGTH_LONG).show()
        })

        viewModel.getData()
        result.text = "0.005"

        btn_revert_cost.setOnClickListener {
            result.text = increment(result.text.toString())
        }
    }

    private fun increment(value: String): String {
        var maz = 1
        for (m in 1..value.length - 2) {
            maz *= 10
        }

        var final: Double = (value.toDouble() + (1 / maz.toDouble()))


        return (round(final * maz) / maz).toString()
    }

    fun Double.length() = when (this) {
        0.0 -> 1
        else -> log10(abs(toDouble())).toInt() + 1
    }


}