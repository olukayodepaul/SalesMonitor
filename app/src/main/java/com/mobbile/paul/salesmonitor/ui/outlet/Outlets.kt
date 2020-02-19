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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class Outlets : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: OutletViewModel

    private lateinit var mAdapter: OutletAdapter

    var repid:Int = 0
    var route:String = ""
    var repname:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outlets)
        vmodel = ViewModelProviders.of(this, modelFactory)[OutletViewModel::class.java]
        repid = intent.getIntExtra("rep_id",0)
        route = intent.getStringExtra("route")!!
        repname = intent.getStringExtra("repname")!!
        initViews()
    }

    private fun initViews() {

        titles_r.text = "$repname ($route)"

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
        intent.putExtra("repname",repname)
        intent.putExtra("route",route)
        intent.putExtra("start_time", SimpleDateFormat("HH:mm:ss").format(Date()))
        startActivity(intent)
    }
}
