package com.mobbile.paul.salesmonitor.ui.salesrep

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.mobbile.paul.salesmonitor.R
import com.mobbile.paul.salesmonitor.model.Sales_Rep_Child_Model
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.sales_rep_adapter_layout.view.*


class SalesRepAdapter(private var mItems: List<Sales_Rep_Child_Model>, private var contexts: Context,
                      val clickListener: (Sales_Rep_Child_Model) -> Unit
):
    RecyclerView.Adapter<SalesRepAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context)
            .inflate(R.layout.sales_rep_adapter_layout, p0, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = mItems[p1]
        p0.bind(item,clickListener)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    companion object {
        private val TAG = "ModulesActivity"
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: Sales_Rep_Child_Model, clickListener: (Sales_Rep_Child_Model) -> Unit) {

            val letter: String? = item.repname.substring(0, 1)
            val generator = ColorGenerator.MATERIAL
            val drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor())
            containerView.imageView.setImageDrawable(drawable)
            containerView.ed_code.text = item.rep_edcode
            containerView.tv_name.text = item.repname
            containerView.setOnClickListener {
                clickListener(item)
            }

        }
    }
}