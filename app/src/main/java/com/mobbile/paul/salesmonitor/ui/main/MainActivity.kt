package com.mobbile.paul.salesmonitor.ui.main

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import com.mobbile.paul.salesmonitor.R
import com.mobbile.paul.salesmonitor.model.PostToServerResponse
import com.mobbile.paul.salesmonitor.model.PostToServer
import com.mobbile.paul.salesmonitor.ui.salesrep.SalesRep
import com.mobbile.paul.salesmonitor.util.Util.prefencesData
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), DatePickerDialog.OnDateSetListener {


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
    var confirmPhone: RadioButton? = null
    var outletPurchaseOnVisit: RadioButton? = null

    var repid: Int = 0
    var urno: Int = 0
    var buttonControl: Int = 0

    var route: String = ""
    var repname: String = ""
    var start_time: String = ""

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vmodel = ViewModelProviders.of(this, modelFactory)[MainViewModel::class.java]
        preferences = getSharedPreferences(prefencesData, Context.MODE_PRIVATE)
        repid = intent.getIntExtra("rep_id", 0)
        urno = intent.getIntExtra("urno", 0)
        route = intent.getStringExtra("route")!!
        repname = intent.getStringExtra("repname")!!
        start_time = intent.getStringExtra("start_time")!!

        pagerAdapter = PaginationAdpter()
        pager.offscreenPageLimit = 3
        pager.adapter = pagerAdapter
        pager.beginFakeDrag()
        fab_2.visibility = View.GONE
        base_progress_bar.visibility = View.GONE
        titles_m.text = "$repname ($route)"

        backbtn.setOnClickListener {
            onBackPressed()
        }

        t_volume.setOnClickListener {
            showDatePicker()
        }

        outlet_seen.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio.text) {
                "Yes" -> {
                    outlet_not_seen.visibility = View.GONE
                    outlet_not_seen_group.visibility = View.GONE
                    product_question.visibility = View.VISIBLE
                    outlet_class.visibility = View.VISIBLE
                    fab.setImageResource(R.drawable.ic_chevron_right)
                    product_seen_if_cus_r_m_no_remark.visibility = View.GONE
                    _remark_one.visibility = View.GONE
                    buttonControl = 1
                }
                else -> {
                    outlet_not_seen.visibility = View.VISIBLE
                    outlet_not_seen_group.visibility = View.VISIBLE
                    product_question.visibility = View.GONE
                    outlet_class.visibility = View.GONE
                    fab.setImageResource(R.drawable.ic_submit)
                    product_seen_if_cus_r_m_no_remark.visibility = View.VISIBLE
                    _remark_one.visibility = View.VISIBLE
                    buttonControl = 2
                }
            }
        }

        confirm_phone.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio.text) {
                "Incorrect Phone Number" -> {
                    enter_correct_phone_number.visibility = View.VISIBLE
                    enter_correct_phone_number_new.visibility = View.VISIBLE
                }
                else -> {
                    enter_correct_phone_number.visibility = View.GONE
                    enter_correct_phone_number_new.visibility = View.GONE
                }
            }
        }

        customer_buying_from.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio.text) {
                "Open market (WholeSeller)" -> {
                    if_whole_seller.visibility = View.VISIBLE
                    if_whole_seller_re.visibility = View.VISIBLE
                }
                else -> {
                    if_whole_seller.visibility = View.GONE
                    if_whole_seller_re.visibility = View.GONE
                }
            }
        }

        cust_satn.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio.text) {
                "No" -> {
                    visit_satis_1.visibility = View.VISIBLE
                    visit_satis_2.visibility = View.VISIBLE
                }
                else -> {
                    visit_satis_1.visibility = View.GONE
                    visit_satis_2.visibility = View.GONE
                }
            }
        }

        outlet_puchase1.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio.text) {
                "Yes" -> {
                    last_date_purchase.visibility = View.VISIBLE
                    last_date_purchase_values.visibility = View.VISIBLE
                    product_seen_if_cus_r.visibility = View.VISIBLE
                    t_volume.visibility = View.VISIBLE
                    product_seen_if_cus_r_q.visibility = View.VISIBLE
                    t_volume_m.visibility = View.VISIBLE
                    purchase_reasons.visibility = View.GONE
                    purchase_reasons_values.visibility = View.GONE
                }
                else -> {
                    last_date_purchase.visibility = View.GONE
                    last_date_purchase_values.visibility = View.GONE
                    product_seen_if_cus_r.visibility = View.GONE
                    t_volume.visibility = View.GONE
                    product_seen_if_cus_r_q.visibility = View.GONE
                    t_volume_m.visibility = View.GONE
                    purchase_reasons.visibility = View.VISIBLE
                    purchase_reasons_values.visibility = View.VISIBLE
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

                groupButtonSeenOutlet = findViewById(outlet_seen.checkedRadioButtonId) ?: null
                groupButtonNotSeenOutlet =
                    findViewById(outlet_not_seen_group.checkedRadioButtonId) ?: null
                outletClass = outlet_class.selectedItem.toString()

                if (groupButtonSeenOutlet == null) {
                    Toast.makeText(
                        applicationContext,
                        "Please check Outlet Seen",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (groupButtonSeenOutlet!!.text.toString() == "No" && groupButtonNotSeenOutlet == null) {
                    Toast.makeText(
                        applicationContext,
                        "Please check, if outlet not found and enter remark",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (groupButtonSeenOutlet!!.text.toString() == "Yes" && outletClass == "Select Outlet Class") {
                    Toast.makeText(
                        applicationContext,
                        "Please select confirmation of outlet classification",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (buttonControl == 1) {
                        pagination()
                    } else {
                        callQuestionnareWithOutletSeen()
                    }
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
                callQuestionnaireApi()
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

    @SuppressLint("RestrictedApi")
    private fun callQuestionnareWithOutletSeen() {
        base_progress_bar.visibility = View.VISIBLE
        fab.visibility = View.GONE
        groupButtonSeenOutlet = findViewById(outlet_seen.checkedRadioButtonId) ?: null
        groupButtonNotSeenOutlet = findViewById(outlet_not_seen_group.checkedRadioButtonId) ?: null
        val supervisorId = preferences!!.getInt("pre_employee_id", 0)

        val allData = PostToServer()
        allData.outletclass = ""
        allData.groupbuttonseenoutlet = (groupButtonSeenOutlet!!.text as String?).toString()
        allData.groupButtonnotseenoutlet = (groupButtonNotSeenOutlet!!.text as String?).toString()
        allData.customeruyingfrom = ""
        allData.custsatisfaction = ""
        allData.smstoken = ""
        allData.totalvolume = ""
        allData.remarks = _remark_one.text.toString()
        allData.supervisorid = supervisorId
        allData.urno = urno
        allData.repid = repid
        allData.if_incorrect_phone = ""
        allData.if_open_market = ""
        allData.if_customer_not_satisfy_reason = ""
        allData.outlet_purchase_yes_qty_purchase = ""
        allData.outlet_purchase_yes_last_puchase_before_visit = ""
        allData.outlet_purchase_yes_qty_purchase_before_visit = ""
        allData.outlet_purchase_no_reason = ""
        allData.starting_time = start_time
        allData.confirmphone = ""
        allData.visitpurchase = ""
        vmodel.moveQuestionareToServer(allData).observe(this, pushDataToServer)

    }

    @SuppressLint("RestrictedApi")
    private fun callQuestionnaireApi() {

        base_progress_bar.visibility = View.VISIBLE
        fab.visibility = View.GONE

        var outlets = ""
        outletClass = outlet_class.selectedItem.toString()
        groupButtonSeenOutlet = findViewById(outlet_seen.checkedRadioButtonId) ?: null
        groupButtonNotSeenOutlet = findViewById(outlet_not_seen_group.checkedRadioButtonId) ?: null
        customerBuyingFrom = findViewById(customer_buying_from.checkedRadioButtonId) ?: null
        custSatisfaction = findViewById(cust_satn.checkedRadioButtonId) ?: null
        smsToken = findViewById(sms_token.checkedRadioButtonId) ?: null
        confirmPhone = findViewById(confirm_phone.checkedRadioButtonId) ?: null
        outletPurchaseOnVisit = findViewById(outlet_puchase1.checkedRadioButtonId) ?: null
        totalVolume = t_volume.text.toString()
        remarks = remark.text.toString()
        val supervisorId = preferences!!.getInt("pre_employee_id", 0)

        if (groupButtonSeenOutlet!!.text == "No") {
            outlets = (groupButtonNotSeenOutlet!!.text as String?).toString()
        }

        val allData = PostToServer()
        allData.outletclass = outletClass
        allData.groupbuttonseenoutlet = (groupButtonSeenOutlet!!.text as String?).toString()
        allData.groupButtonnotseenoutlet = outlets
        allData.customeruyingfrom = (customerBuyingFrom!!.text as String?).toString()
        allData.custsatisfaction = (custSatisfaction!!.text as String?).toString()
        allData.smstoken = (smsToken!!.text as String?).toString()
        allData.totalvolume = ""
        allData.remarks = remarks
        allData.supervisorid = supervisorId
        allData.urno = urno
        allData.repid = repid
        allData.if_incorrect_phone = enter_correct_phone_number_new.text.toString()
        allData.if_open_market = if_whole_seller_re.text.toString()
        allData.if_customer_not_satisfy_reason = visit_satis_2.text.toString()
        allData.outlet_purchase_yes_qty_purchase = last_date_purchase_values.text.toString()
        allData.outlet_purchase_yes_last_puchase_before_visit = t_volume.text.toString()
        allData.outlet_purchase_yes_qty_purchase_before_visit = t_volume_m.text.toString()
        allData.outlet_purchase_no_reason = purchase_reasons_values.text.toString()
        allData.starting_time = start_time
        allData.confirmphone = (confirmPhone!!.text as String?).toString()
        allData.visitpurchase = (outletPurchaseOnVisit!!.text as String?).toString()
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

    private fun showDatePicker() {
        val datePicker = DatePickerDialog(
            this,
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH) + 1,
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
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

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val date = "$p1-$p2-$p3"
        t_volume.setText(date)
    }
}

