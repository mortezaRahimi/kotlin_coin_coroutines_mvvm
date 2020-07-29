package com.mortex.farhadmarket.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.mortex.farhadmarket.R
import com.mortex.farhadmarket.data.model.Currency
import com.mortex.farhadmarket.data.model.PriceItem
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.internal.notify
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.round


class MainActivity : AppCompatActivity(), OnItemClickListener {

    val viewModel: MainViewModel by viewModel()
    private lateinit var currencyAdapter: CurrencyAdapter
    private var currencies: ArrayList<PriceItem> = ArrayList()

    private val repeatUpdateHandler: Handler = Handler()
    var mAutoIncrement = false
    var mAutoDecrement = false
    var mValue = 0
    private val dialog: MaterialDialog by lazy { MaterialDialog(this) }
    private var toPay = true


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDialog()


        viewModel.allPrices.observe(this, Observer {
            for (i in it) {
                currencies.add(i)
            }

        })

        viewModel.getData()

        result.text = "0.005"

        btn_add.setOnLongClickListener {
            mAutoIncrement = true
            repeatUpdateHandler.post(rptUpdater(pay_value))
            return@setOnLongClickListener false
        }

        btn_add.setOnTouchListener { _, event ->
            if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)
                && mAutoIncrement
            ) {
                mAutoIncrement = false
            }
            false
        }

        btn_add_get.setOnLongClickListener {
            mAutoIncrement = true
            repeatUpdateHandler.post(rptUpdater(get_value))
            return@setOnLongClickListener false
        }

        btn_add_get.setOnTouchListener { _, event ->
            if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)
                && mAutoIncrement
            ) {
                mAutoIncrement = false
            }
            false
        }


        btn_minus.setOnLongClickListener {
            mAutoDecrement = true
            repeatUpdateHandler.post(rptUpdater(pay_value))
            return@setOnLongClickListener false
        }

        btn_minus.setOnTouchListener { _, event ->
            if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)
                && mAutoDecrement
            ) {
                mAutoDecrement = false
            }
            false
        }



        btn_minus_get.setOnLongClickListener {
            mAutoDecrement = true
            repeatUpdateHandler.post(rptUpdater(get_value))
            return@setOnLongClickListener false
        }

        btn_minus_get.setOnTouchListener { _, event ->
            if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)
                && mAutoDecrement
            ) {
                mAutoDecrement = false
            }
            false
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


    fun rptUpdater(et: EditText): Runnable {
        return Runnable {
            if (mAutoIncrement) {
                et.setText(increment(et.text.toString()))
                repeatUpdateHandler.postDelayed(rptUpdater(et), 50)
            } else if (mAutoDecrement) {
                et.setText(decrement(et.text.toString()))
                repeatUpdateHandler.postDelayed(rptUpdater(et), 50)
            }
        }
    }

    private fun initDialog() {
        currencyAdapter = CurrencyAdapter(this)
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

    override fun onItemClicked(currency: PriceItem) {
        if (toPay) {
            selected_currency_to_pay.text = currency.currency.name
            source_balance_value.text = currency.currency.name
            source_before_value.text = powerCompute(10, -currency.currency.normalizationScale)
        } else {
            selected_currency_to_get.text = currency.currency.name
            des_balance_value.text = currency.currency.name
            des_before_value.text = powerCompute(10, -currency.currency.normalizationScale)
        }


        dialog.dismiss()
    }


    fun powerCompute(base: Int, power: Int): String {

        var r = power
        var result: Long = 1

        while (r != 0) {
            result *= base.toLong()
            r--
        }

        return result.toString()
    }
}
