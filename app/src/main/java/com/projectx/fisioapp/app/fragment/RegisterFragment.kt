package com.projectx.fisioapp.app.fragment

import android.app.Activity
import android.support.v4.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.projectx.fisioapp.R


class RegisterFragment : Fragment() {

    private var onFragmentButtonRegisterPressedListener: OnFragmentButtonRegisterPressedListener? = null
    private var registerButton: Button? = null
    var name: EditText? = null
    private var email: EditText? = null
    private var pass: EditText? = null

    companion object {
        private val TAG = "RegisterFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val root = inflater?.inflate(R.layout.fragment_register, container, false)
        registerButton = root?.findViewById(R.id.btnRegister)
        name = root?.findViewById(R.id.etName)
        email = root?.findViewById(R.id.etEmail)
        pass = root?.findViewById(R.id.etPass)

        registerButton?.setOnClickListener {
            onFragmentButtonRegisterPressedListener?.buttonRegisterPressed(
                    name?.text.toString(),
                    email?.text.toString(),
                    pass?.text.toString()
            )
        }

        setFakeDataInForm()

        return root

    }

    private fun setFakeDataInForm() {
        name?.setText( "anotherFisio")
        email?.setText( "anotherfisio@invalid.com")
        pass?.setText("12345678")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        commonOnAttach(context)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonOnAttach(activity)
    }

    private fun commonOnAttach(context: Context?) {
        if (context is OnFragmentButtonRegisterPressedListener) {
            onFragmentButtonRegisterPressedListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onFragmentButtonRegisterPressedListener = null
    }

    interface OnFragmentButtonRegisterPressedListener {
        fun buttonRegisterPressed(name: String, email: String, password: String)
    }

}