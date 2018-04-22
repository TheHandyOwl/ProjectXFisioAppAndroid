package com.projectx.fisioapp.app.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.adapter.SectionsPagerAdapter
import com.projectx.fisioapp.app.fragment.LoginFragment
import com.projectx.fisioapp.app.fragment.RegisterFragment
import com.projectx.fisioapp.app.helper.BottomNavigationViewHelper
import com.projectx.fisioapp.app.router.Router
import com.projectx.fisioapp.app.utils.toastIt
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.users.authenticateuser.AuthenticateUserIntImpl
import com.projectx.fisioapp.domain.interactor.users.authenticateuser.AuthenticateUserInteractor
import com.projectx.fisioapp.domain.interactor.users.registeruser.RegisterUserIntImpl
import com.projectx.fisioapp.domain.interactor.users.registeruser.RegisterUserInteractor
import com.projectx.fisioapp.domain.model.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : ParentActivity(),
        LoginFragment.OnFragmentButtonLoginPressedListener,
        RegisterFragment.OnFragmentButtonRegisterPressedListener{

    private var mViewPager: ViewPager? = null

    companion object {
        private val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Set up the ViewPager with the sections adapter.
        mViewPager = container as ViewPager
        setupViewPager(mViewPager)

        val tabLayout = findViewById<TabLayout>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)

        // Tabs without icons. Title set at setupViewPager
        /*
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_tab_login)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_tab_register)
        */

    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = SectionsPagerAdapter(supportFragmentManager)
        adapter.addFragment(LoginFragment() as Fragment, getString(R.string.fragment_title_login))
        adapter.addFragment(RegisterFragment() as Fragment, getString(R.string.fragment_title_register))
        viewPager?.adapter = adapter
    }

    override fun buttonLoginPressed(email: String, password: String) {
        authenticateUser(email, password)
    }

    override fun buttonRegisterPressed(name: String, email: String, password: String) {
        registerUser(name, email, password)
    }

    private fun authenticateUser(email: String, password: String) {
        val authenticateUser: AuthenticateUserInteractor = AuthenticateUserIntImpl(this)
            authenticateUser.execute(
                    email,
                    password,
                    success = { user: User, tkn: String ->
                        try {
                            token = tkn
                            uId = user.id
                            if (checkToken()) finish()
                        } catch (e: Throwable) {
                            toastIt(this, "Error: " + e.localizedMessage )
                        }
                    }, error = object : ErrorCompletion {
                        override fun errorCompletion(errorMessage: String) {
                            toastIt(baseContext, errorMessage)
                        }
                    })
    }

    private fun registerUser(name: String, email: String, password: String) {
        val registerUser: RegisterUserInteractor = RegisterUserIntImpl(this)
            registerUser.execute(
                    name,
                    email,
                    password,
                    success = { ok: Boolean, msg: String ->
                        try {
                            if (ok) {
                                    toastIt(baseContext, "You can press 'Login' to start")
                            } else {
                                toastIt(baseContext, "Success/error: Try again")
                            }
                        } catch (e: Exception) {
                            toastIt(this, "Error: " + e.localizedMessage )
                        }
                    }, error = object : ErrorCompletion {
                override fun errorCompletion(errorMessage: String) {
                    toastIt(baseContext, errorMessage)
                }
            })
    }

}
