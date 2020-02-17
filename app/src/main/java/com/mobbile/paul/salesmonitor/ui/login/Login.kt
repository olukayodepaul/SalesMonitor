package com.mobbile.paul.salesmonitor.ui.login


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mobbile.paul.salesmonitor.BaseActivity
import com.mobbile.paul.salesmonitor.R
import com.mobbile.paul.salesmonitor.model.LoginResponse
import com.mobbile.paul.salesmonitor.ui.salesrep.SalesRep
import com.mobbile.paul.salesmonitor.util.Util.prefencesData
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class Login : BaseActivity() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: LoginViewModel
    private var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        vmodel = ViewModelProviders.of(this, modelFactory)[LoginViewModel::class.java]
        preferences = getSharedPreferences(prefencesData, Context.MODE_PRIVATE)
        showProgressBar(false)
        btn_login.setOnClickListener {
            setLogin()
        }
    }

    private fun setLogin() {
        showProgressBar(true)
        val username: String = "david.m@mt3.com"//et_email.text.toString()
        val password: String = "185"//et_password.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Enter username and password", Toast.LENGTH_SHORT).show()
            return
        }

            vmodel.setLogins(username, password).observe(this, Observer<LoginResponse> {
                showProgressBar(false)
                if (it == null) {
                    Toast.makeText(
                        applicationContext,
                        "Network issue, please try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (it.status == 200 && it.messages == "OK") {
                        preferences!!.edit().apply()
                        val editor = preferences!!.edit()
                        editor.clear()
                        editor.putInt("pre_employee_id", it.supervisor_id)
                        editor.putInt("pre_employee_region", it.region_id)
                        editor.putInt("pre_employee_depot", it.depots_id)
                        editor.apply()
                        val intent = Intent(this, SalesRep::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, it.notification, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }
}
