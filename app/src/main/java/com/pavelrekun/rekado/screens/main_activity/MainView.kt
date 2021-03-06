package com.pavelrekun.rekado.screens.main_activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.about_activity.AboutActivity
import com.pavelrekun.rekado.screens.instructions_fragment.InstructionsFragment
import com.pavelrekun.rekado.screens.logs_fragment.LogsFragment
import com.pavelrekun.rekado.screens.payload_fragment.PayloadsFragment
import com.pavelrekun.rekado.screens.settings_activity.SettingsActivity
import com.pavelrekun.rekado.services.dialogs.DonateDialog
import com.pavelrekun.rekado.services.utils.DesignUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainView(private val activity: BaseActivity, private val savedInstanceState: Bundle?) : MainContract.View {

    init {
        initViews()
    }

    override fun initViews() {
        initToolbar()
        initNavigationClickListener()
    }

    override fun initToolbar() {
        activity.setSupportActionBar(activity.mainToolbar)
    }

    override fun initNavigationClickListener() {
        if (savedInstanceState == null) {
            chooseNavigationItem(R.id.navigation_instructions)
            activity.mainNavigationBar.selectedItemId = R.id.navigation_instructions
        }

        activity.mainNavigationBar.setOnNavigationItemSelectedListener {
            chooseNavigationItem(it.itemId)
            true
        }

        activity.mainNavigationBar.setOnNavigationItemReselectedListener { }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_about -> {
                activity.startActivity(Intent(activity, AboutActivity::class.java))
                true
            }

            R.id.navigation_settings -> {
                activity.startActivity(Intent(activity, SettingsActivity::class.java))
                true
            }

            R.id.navigation_donate -> {
                val donateDialog = DonateDialog(activity)
                donateDialog.window.setLayout(DesignUtils.convertDPtoPX(360), ViewGroup.LayoutParams.WRAP_CONTENT)
                donateDialog.show()
                true
            }

            else -> return false
        }
    }

    private fun chooseNavigationItem(id: Int) {
        var fragment: Fragment? = null

        when (id) {
            R.id.navigation_payloads -> fragment = PayloadsFragment()
            R.id.navigation_instructions -> fragment = InstructionsFragment()
            R.id.navigation_logs -> fragment = LogsFragment()
        }

        if (fragment != null) {
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainFragmentFrame, fragment)
            transaction.commit()
        }
    }
}