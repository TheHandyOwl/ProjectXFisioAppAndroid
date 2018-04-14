package com.projectx.fisioapp.app.activity

import android.app.Activity
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.helper.BottomNavigationViewHelper
import com.projectx.fisioapp.app.router.Router
import com.projectx.fisioapp.app.settingsmanager.SettingsManager

open class ParentActivity : AppCompatActivity() {

    private val settingsManager = SettingsManager()

    /******** users ********/

    // Token
    protected var token: String
        get() {
            val token = settingsManager.getCustomSharedPreference<String>(
                    this,
                    settingsManager.FILE_USER_PREFERENCES,
                    settingsManager.KEY_TOKEN
            )

            return token ?: ""
        }
        set(value) {
            settingsManager.setCustomSharedPreference(this, settingsManager.FILE_USER_PREFERENCES, settingsManager.KEY_TOKEN, value)
        }

    protected fun checkToken(): Boolean {
        return token.length !== 0
    }

    // UId
    protected var uId: String
        get() {
            val uId = settingsManager.getCustomSharedPreference<String>(
                    this,
                    settingsManager.FILE_USER_PREFERENCES,
                    settingsManager.KEY_UID
            )

            return uId ?: ""
        }
        set(value) {
            settingsManager.setCustomSharedPreference(this, settingsManager.FILE_USER_PREFERENCES, settingsManager.KEY_UID, value)
        }

    protected fun checkUId(): Boolean {
        return uId.length !== 0
    }

    protected fun addBottomBar(main: Activity){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavView_bar) as BottomNavigationView
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView)

        val menu = bottomNavigationView.menu
        val menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_menu_login -> {
                    Router().moveToLoginActivity(main)
                }
                R.id.ic_menu_aboutme -> {
                    Router().moveToAboutMeActivity(main)
                }
                R.id.ic_menu_calendar -> {
                    Router().moveToCalendarActivity(main)
                }
                R.id.ic_menu_products -> {
                    Router().moveToProductsActivity(main)
                }
                R.id.ic_menu_services -> {
                    Router().moveToServicesActivity(main)
                }
            }

            false
        }
    }

}
