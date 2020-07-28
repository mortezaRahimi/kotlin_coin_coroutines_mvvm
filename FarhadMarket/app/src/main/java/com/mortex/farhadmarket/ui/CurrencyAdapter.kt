package com.mortex.farhadmarket.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.mortex.farhadmarket.R
import com.mortex.farhadmarket.data.model.Currency
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.properties.Delegates

class CurrencyAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<CurrencyAdapter.CatViewHolder>() {

    // Our data list is going to be notified when we assign a new list of data to it
    private var list: List<Currency> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return CatViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        // Verify if position exists in list
        if (position != RecyclerView.NO_POSITION) {
            val item: Currency = list[position]
            holder.bind(item, listener)
        }
    }

    // Update recyclerView's data
    fun updateData(newCatsList: List<Currency>) {
        list = newCatsList
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(currency: Currency, clickListener: OnItemClickListener) {
            itemView.drop_list_title.text = currency.name
            itemView.setOnClickListener {
                clickListener.onItemClicked(currency)
            }
        }
    }

}

interface OnItemClickListener {
    fun onItemClicked(currency: Currency)
}
