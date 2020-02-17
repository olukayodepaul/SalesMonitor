package com.mobbile.paul.salesmonitor.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.button.MaterialButton
import com.mobbile.paul.salesmonitor.BaseActivity
import com.mobbile.paul.salesmonitor.R
import com.mobbile.paul.salesmonitor.model.PostToServerResponse
import com.mobbile.paul.salesmonitor.model.PostToServer
import com.mobbile.paul.salesmonitor.ui.salesrep.SalesRep
import com.mobbile.paul.salesmonitor.util.Util.prefencesData
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {


    private lateinit var pagerAdapter: PaginationAdpter

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: MainViewModel

    private var preferences: SharedPreferences? = null

    var outletClass: String = ""
    var groupButtonSeenOutlet: RadioButton? = null
    var groupButtonNotSeenOutlet: RadioButton? = null
    var customerBuyingFrom: RadioButton? = null
    var custSatisfaction: RadioButton? = null
    var smsToken: RadioButton? = null
    var totalVolume: String = ""
    var remarks: String = ""

    var repid:Int = 0
    var urno:Int = 0

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vmodel = ViewModelProviders.of(this, modelFactory)[MainViewModel::class.java]
        preferences = getSharedPreferences(prefencesData, Context.MODE_PRIVATE)
        repid = intent.getIntExtra("rep_id",0)
        urno = intent.getIntExtra("urno",0)

        pagerAdapter = PaginationAdpter()
        pager.offscreenPageLimit = 3
        pager.adapter = pagerAdapter
        pager.beginFakeDrag()
        fab_2.visibility = View.GONE
        base_progress_bar.visibility = View.GONE

        backbtn.setOnClickListener {
            onBackPressed()
        }

        outlet_seen.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio.text) {
                "Yes" -> {
                    outlet_not_seen.visibility = View.GONE
                    outlet_not_seen_group.visibility = View.GONE
                    product_question.visibility = View.VISIBLE
                    outlet_class.visibility = View.VISIBLE
                }
                else -> {
                    outlet_not_seen.visibility = View.VISIBLE
                    outlet_not_seen_group.visibility = View.VISIBLE
                    product_question.visibility = View.GONE
                    outlet_class.visibility = View.GONE
                    fab.setImageResource(R.drawable.ic_submit)
                }
            }
        }

        pager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageScrollStateChanged(i: Int) {}
            @SuppressLint("RestrictedApi")
            override fun onPageSelected(i: Int) {
                when (i) {
                    0 -> {
                        fab.setImageResource(R.drawable.ic_chevron_right)
                        fab_2.visibility = View.GONE
                        fab.setOnClickListener {
                            cacheInLocalDb(i)
                        }
                    }
                    1 -> {
                        fab.setImageResource(R.drawable.ic_chevron_right)
                        fab_2.visibility = View.VISIBLE
                        fab.setOnClickListener {
                            cacheInLocalDb(i)
                        }
                    }
                    2 -> {
                        fab.setImageResource(R.drawable.ic_submit)
                        fab_2.visibility = View.VISIBLE
                        fab.setOnClickListener {
                            cacheInLocalDb(i)
                        }
                    }
                }
            }
        })

        fab.setOnClickListener {
            if (pager != null) {
                cacheInLocalDb(0)
            }
        }

        fab_2.setOnClickListener {
            if (pager != null) {
                pager.setCurrentItem(pager.currentItem - 1, true)
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun cacheInLocalDb(l1: Int) {

        when (l1) {
            0 -> {
                //do the next here
                outletClass = outlet_class.selectedItem.toString()
                groupButtonSeenOutlet = findViewById(outlet_seen.checkedRadioButtonId) ?: null
                groupButtonNotSeenOutlet = findViewById(outlet_not_seen_group.checkedRadioButtonId) ?: null

                if (outletClass == "Select Outlet Class" || groupButtonSeenOutlet == null) {
                    Toast.makeText(
                        applicationContext,
                        "Please Confirm Outlet and select outlet seen",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (groupButtonSeenOutlet!!.text == "No" && groupButtonNotSeenOutlet == null) {
                    Toast.makeText(
                        applicationContext,
                        "Please select, If Outlet not found",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    pagination()
                }
            }

            1 -> {

                customerBuyingFrom = findViewById(customer_buying_from.checkedRadioButtonId) ?: null
                custSatisfaction = findViewById(cust_satn.checkedRadioButtonId) ?: null
                smsToken = findViewById(sms_token.checkedRadioButtonId) ?: null

                if (customerBuyingFrom == null || custSatisfaction == null || smsToken == null) {
                    Toast.makeText(
                        applicationContext,
                        "Please select all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    pagination()
                }
            }

            2 -> {

                base_progress_bar.visibility = View.VISIBLE
                fab.visibility = View.GONE
                totalVolume = t_volume.text.toString()

                if (totalVolume.isNullOrEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        "Please enter volume purchase",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    callQuestionnaireApi()
                }
            }
        }
    }

    private fun pagination() {
        if (pager != null) {
            pager.setCurrentItem(pager.currentItem + 1, true)
        }
    }

    companion object {
        var TAG = "compafdndfnf"
    }

    private fun callQuestionnaireApi() {

        var outlets = ""

        outletClass = outlet_class.selectedItem.toString()
        groupButtonSeenOutlet = findViewById(outlet_seen.checkedRadioButtonId) ?: null
        groupButtonNotSeenOutlet = findViewById(outlet_not_seen_group.checkedRadioButtonId) ?: null
        customerBuyingFrom = findViewById(customer_buying_from.checkedRadioButtonId) ?: null
        custSatisfaction = findViewById(cust_satn.checkedRadioButtonId) ?: null
        smsToken = findViewById(sms_token.checkedRadioButtonId) ?: null
        totalVolume = t_volume.text.toString()
        remarks = remark.text.toString()
        val supervisorId = preferences!!.getInt("pre_employee_id",0)

        if(groupButtonSeenOutlet!!.text == "No"){
            outlets = (groupButtonNotSeenOutlet!!.text as String?).toString()
        }

        val allData = PostToServer()
        allData.outletclass = outletClass
        allData.groupbuttonseenoutlet = (groupButtonSeenOutlet!!.text as String?).toString()
        allData.groupButtonnotseenoutlet = outlets
        allData.customeruyingfrom = (customerBuyingFrom!!.text as String?).toString()
        allData.custsatisfaction = (custSatisfaction!!.text as String?).toString()
        allData.smstoken = (smsToken!!.text as String?).toString()
        allData.totalvolume = totalVolume
        allData.remarks = remarks
        allData.supervisorid = supervisorId
        allData.urno = urno
        allData.repid = repid

        vmodel.moveQuestionareToServer(allData).observe(this, pushDataToServer)
    }

    @SuppressLint("RestrictedApi")
    private val pushDataToServer = Observer<PostToServerResponse> {
        if (it.status == 200) {
            base_progress_bar.visibility = View.GONE
            fab.visibility = View.VISIBLE
            customeSuccessDialog()
        } else {
            base_progress_bar.visibility = View.GONE
            fab.visibility = View.VISIBLE
            Toast.makeText(applicationContext, "Please select all the fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun customeSuccessDialog() {
        val dialog = MaterialDialog(this)
            .cancelOnTouchOutside(false)
            .cancelable(false)
            .customView(R.layout.materialdialogs)
        dialog.findViewById<MaterialButton>(R.id.submit_button).setOnClickListener {
            val intent = Intent(this, SalesRep::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            dialog.dismiss()
        }
        dialog.show()
    }
}

