package com.mobbile.paul.salesmonitor.ui.salesrep

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.salesmonitor.BaseActivity
import com.mobbile.paul.salesmonitor.R
import com.mobbile.paul.salesmonitor.model.Sales_Rep_Child_Model
import com.mobbile.paul.salesmonitor.model.Sales_Rep_Parent_Model
import com.mobbile.paul.salesmonitor.ui.outlet.Outlets
import com.mobbile.paul.salesmonitor.util.Util
import kotlinx.android.synthetic.main.activity_submit_questionnaire.*
import javax.inject.Inject

class SalesRep : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: SalesRepViewModel

    private lateinit var mAdapter: SalesRepAdapter

    private var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_questionnaire)
        vmodel = ViewModelProviders.of(this, modelFactory)[SalesRepViewModel::class.java]
        preferences = getSharedPreferences(Util.prefencesData, Context.MODE_PRIVATE)
        initViews()
    }

    private fun initViews() {
        val region = preferences!!.getInt("pre_employee_region",0)
        val depot = preferences!!.getInt("pre_employee_depot",0)
        replist_recycler.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        replist_recycler.layoutManager = layoutManager
        vmodel.getRepDetails(region, depot).observe(this, Observer<Sales_Rep_Parent_Model> {
            showProgressBar(false)
            if(it==null || it.all_sales_rep.isEmpty()) {
                Toast.makeText(applicationContext, "No Rep assign to this depot", Toast.LENGTH_SHORT).show()
            }else{
                showProgressBar(false)
                val list: List<Sales_Rep_Child_Model> = it.all_sales_rep
                mAdapter = SalesRepAdapter(list, this,::modulesAdapterItems)
                mAdapter.notifyDataSetChanged()
                replist_recycler.adapter = mAdapter
            }
        })
    }

    private fun modulesAdapterItems(item : Sales_Rep_Child_Model) {
        val intent: Intent?
        intent = Intent(this, Outlets::class.java)
        intent.putExtra("rep_id",item.rep_id)
        startActivity(intent)
    }
}
