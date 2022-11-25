package com.network.shopmanager.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.network.shopmanager.R
import com.network.shopmanager.databinding.ActivityMainDrawerBinding
import com.network.shopmanager.utils.*
import com.network.shopmanager.utils.Objects.APP
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainDrawerBinding
    lateinit var drawer: DrawerLayout
    private var tvHome: TextView? = null
    private var tvSupplier: TextView? = null
    private var tvTaxi: TextView? = null
    private var tvInfo: TextView? = null
    private var tvBank: TextView? = null
    private var tvAdmin: TextView? = null

    private var ivAvatar: ImageView? = null
    private var ivNightMode: ImageView? = null
    private var tvKurs: TextView? = null
    private var tvUserHeader: TextView? = null
    private var userInfo = ""

    private lateinit var navController: NavController

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP = this

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        drawer = binding.drawerLayout
        binding.navView.setNavigationItemSelectedListener(this)

        initHeader()
        initMenuCounters()
        setClickListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun initHeader() {
        val header = binding.navView.getHeaderView(0)
        ivAvatar = header.findViewById(R.id.iv_avatar)
        ivNightMode = header.findViewById(R.id.iv_night_mode)
        tvKurs = header.findViewById(R.id.tv_kurs)
        tvUserHeader = header.findViewById(R.id.tv_user_header)

        if (isDarkModeOn()) {
            ivNightMode?.setImageResource(R.drawable.ic_sun)
        } else {
            ivNightMode?.setImageResource(R.drawable.ic_moon)
        }
        ivAvatar?.setImageResource(R.drawable.shop)
        tvKurs?.text = "1$=11.235"
        userInfo = "Umid (1-do'kon)\n+99891123456\n+9989712300"
        tvUserHeader?.text = userInfo
    }

    private fun setClickListeners() {
        ivAvatar?.setOnClickListener {

        }
        ivNightMode?.setOnClickListener {
            val mode = !isDarkModeOn()
            Objects.PREF.setBoolean(Constants.KEY_NIGHT_MODE, mode)
            setNightMode(mode)
        }
        binding.btnTokenHeader.setOnClickListener {
            vm.fetchToken()
            vm.resultToken.observe(this) {
                val message = it.message
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressToken.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.progressToken.visibility = View.GONE
                        message.toToast()
                    }
                    Status.SUCCESS -> {
                        binding.progressToken.visibility = View.GONE
                        binding.tvTokenHeader.visibility = View.VISIBLE
                        binding.tvTokenHeader.text = it.data?.token
                        waitMoment(60_000) {
                            binding.tvTokenHeader.visibility = View.GONE
                        }
                    }
                    else -> {}
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    fun initMenuCounters() {
        tvHome =
            binding.navView.menu.findItem(R.id.fragment_home).actionView.findViewById(R.id.counter) as TextView
        tvSupplier =
            binding.navView.menu.findItem(R.id.fragment_suppliers).actionView.findViewById(R.id.counter) as TextView
        tvTaxi =
            binding.navView.menu.findItem(R.id.fragment_taxis).actionView.findViewById(R.id.counter) as TextView
        tvInfo =
            binding.navView.menu.findItem(R.id.fragment_info).actionView.findViewById(R.id.counter) as TextView
        tvBank =
            binding.navView.menu.findItem(R.id.fragment_bank).actionView.findViewById(R.id.counter) as TextView
        tvAdmin =
            binding.navView.menu.findItem(R.id.fragment_admin).actionView.findViewById(R.id.counter) as TextView
        tvHome?.text = "+9"
        tvSupplier?.text = "+1"
        tvTaxi?.text = "+3345"
        tvInfo?.text = "+5"
        tvBank?.text = "+423"
        tvAdmin?.text = "+16"

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fragment_home -> {
                navController.popBackStack(R.id.fragment_home, true)
                navController.navigate(R.id.fragment_home)
            }
            R.id.fragment_suppliers -> {

            }
            R.id.fragment_taxis -> {

            }
            R.id.fragment_info -> {

            }
            R.id.fragment_bank -> {

            }
            R.id.fragment_admin -> {
                navController.popBackStack(R.id.fragment_admin, true)
                navController.navigate(R.id.fragment_admin)
            }
            R.id.fragment_settings -> {

            }
            R.id.sign_out -> {
                ShowAlertDialog(
                    title = "Diqqat !",
                    message = "Akkauntdan chiqib ketishga ishonchingiz komilmi ?"
                ) {
                    if (it) {
                        vm.signOut()
                        val intent = Intent(this, SignInActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        finish()
                        startActivity(intent)
                    }
                }
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}