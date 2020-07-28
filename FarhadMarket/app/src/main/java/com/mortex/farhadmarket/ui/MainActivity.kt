package com.mortex.farhadmarket.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.mortex.farhadmarket.R
import com.mortex.farhadmarket.data.model.Currency
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_choose_currency.*
import kotlinx.android.synthetic.main.list_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.round
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity(), OnItemClickListener {

    val viewModel: MainViewModel by viewModel()
    private lateinit var currencyAdapter: CurrencyAdapter
    private var currencies: ArrayList<Currency> = ArrayList()
    private lateinit var dialog: MaterialDialog
    private var toPay = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDialog()


        viewModel.allPrices.observe(this, Observer {
            for (i in it) {
                currencies.add(i.currency)
            }

        })

        viewModel.getData()

        result.text = "0.005"

        btn_add.setOnClickListener {
            pay_value.text = increment(pay_value.text.toString())
        }

        btn_add_get.setOnClickListener {
            get_value.text = increment(get_value.text.toString())
        }

        btn_minus.setOnClickListener {
            pay_value.text = decrement(pay_value.text.toString())
        }


        btn_minus_get.setOnClickListener {
            get_value.text = decrement(get_value.text.toString())
        }


        selected_currency_to_pay.setOnClickListener {
            toPay = true
            if (currencies.size > 0)
                dialog.show()
        }

        selected_currency_to_get.setOnClickListener {
            toPay = false
            if (currencies.size > 0)
                dialog.show()

        }

    }

    private fun initDialog() {
        currencyAdapter = CurrencyAdapter(this)
        dialog = MaterialDialog(this)
        dialog.customView(R.layout.dialog_choose_currency)


        val rv = dialog.getCustomView().findViewById<RecyclerView>(R.id.currency_rv)
        rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = currencyAdapter
            currencyAdapter.updateData(currencies)
        }
    }

    private fun increment(value: String): String {
        var maz = 1
        for (m in 1..value.length - 2) {
            maz *= 10
        }

        val final: Double = (value.toDouble() + (1 / maz.toDouble()))

        return (round(final * maz) / maz).toString()
    }

    private fun decrement(value: String): String {
        var maz = 1
        for (m in 1..value.length - 2) {
            maz *= 10
        }

        val final: Double = (value.toDouble() - (1 / maz.toDouble()))

        return if (round(final * maz) / maz > 0)
            (round(final * maz) / maz).toString()
        else
            "0.0"
    }

    override fun onItemClicked(currency: Currency) {
        if (toPay)
            selected_currency_to_pay.text = currency.name
        else
            selected_currency_to_get.text = currency.name
        dialog.dismiss()
    }


}
