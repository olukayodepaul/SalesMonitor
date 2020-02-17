package com.mobbile.paul.salesmonitor.ui.outlet


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobbile.paul.salesmonitor.BaseActivity
import com.mobbile.paul.salesmonitor.R
import com.mobbile.paul.salesmonitor.model.Outlets_Child_Model
import com.mobbile.paul.salesmonitor.model.Outlets_Parent_Model
import com.mobbile.paul.salesmonitor.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_outlets.*
import kotlinx.android.synthetic.main.activity_outlets.backbtn
import javax.inject.Inject

class Outlets : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: OutletViewModel

    private lateinit var mAdapter: OutletAdapter

    var repid:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outlets)
        vmodel = ViewModelProviders.of(this, modelFactory)[OutletViewModel::class.java]
        repid = intent.getIntExtra("rep_id",0)
        initViews()
    }

    private fun initViews() {

        backbtn.setOnClickListener {
            onBackPressed()
        }

        customerlist_recycler.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        customerlist_recycler.layoutManager = layoutManager
        vmodel.getAllCustomersDetails(repid).observe(this, Observer<Outlets_Parent_Model> {
            showProgressBar(false)
            if(it==null || it.all_outlets.isEmpty()){
                Toast.makeText(applicationContext, "No Customers assign to this sales rep.", Toast.LENGTH_SHORT).show()
            }else{
                showProgressBar(false)
                val list: List<Outlets_Child_Model> = it.all_outlets
                mAdapter = OutletAdapter(list, this,::modulesAdapterItems)
                mAdapter.notifyDataSetChanged()
                customerlist_recycler.adapter = mAdapter
            }
        })
    }

    private fun modulesAdapterItems(item : Outlets_Child_Model) {
        val intent: Intent?
        intent = Intent(this, MainActivity::class.java)
        intent.putExtra("urno",item.urno)
        intent.putExtra("rep_id",repid)
        startActivity(intent)
    }
}
