package com.projectx.fisioapp.app.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.View
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.utils.toastIt
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.users.getuser.GetUserIntImpl
import com.projectx.fisioapp.domain.interactor.users.getuser.GetUserInteractor
import com.projectx.fisioapp.domain.interactor.users.updateuser.UpdateUserIntImpl
import com.projectx.fisioapp.domain.interactor.users.updateuser.UpdateUserInteractor
import com.projectx.fisioapp.domain.model.User
import kotlinx.android.synthetic.main.activity_user_detail.*
import java.text.SimpleDateFormat
import java.util.*

class UserDetailActivity : ParentActivity() {

    private lateinit var user: User
    private var userWithChanges: User? = null
    private var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        //Back button
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setListeners()

        getUser()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setListeners() {

        btnSave.setOnClickListener {
            updateUser()
        }

        rbFemale.setOnClickListener {
            rbFemale.isChecked = rbFemale.isChecked
            rbMale.isChecked = !rbFemale.isChecked
        }

        rbMale.setOnClickListener {
            rbMale.isChecked = rbMale.isChecked
            rbFemale.isChecked = !rbMale.isChecked
        }

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateBirthdateInView()
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        btnCalendar.setOnClickListener {
            DatePickerDialog(this@UserDetailActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }

    private fun getUser() {
        val getUser: GetUserInteractor = GetUserIntImpl(this)

        try {
            getUser.execute(
                    token,
                    uId,
                    success = {
                        user = it
                        fillFileds(it)
                    }, error = object : ErrorCompletion {
                        override fun errorCompletion(errorMessage: String) {
                            toastIt(baseContext, errorMessage)
                        }
                    })
        } catch (e: Exception) {
            toastIt(this, "Error: " + e.localizedMessage )
        }
    }

    private fun updateUser() {

        val checkFields = getFieldsOrErrors()
        if (checkFields.second != null) {
            toastIt(this, "Fields with errors")
            return
        }

        if (checkFields.first == null) {
            toastIt(this, "No user information available")
            return
        }

        userWithChanges = checkFields.first

        val updateUser: UpdateUserInteractor = UpdateUserIntImpl(this)

        try {
            updateUser.execute(
                    token,
                    userWithChanges as User,
                    success = { ok: Boolean, user: User ->
                        if (ok) {
                            fillFileds(user)
                            toastIt(this, "User updated")
                        }
                        else {
                            toastIt(this, "Success/error")
                        }
                    }, error = object : ErrorCompletion {
                        override fun errorCompletion(errorMessage: String) {
                            toastIt(baseContext, errorMessage)
                        }
                    })
        } catch (e: Exception) {
            toastIt(this, "Error: " + e.localizedMessage )
        }
    }

    private fun fillBackgroundColorForFileds(fields: List<String>) {

        val allFields : MutableList<View> = mutableListOf(lblName, lblLastName, lblLastName, lblEmail, lblAddress, lblPhone, lblBirthdate, lblNationalID, lblFellowshipNumber,lblRegistrationDate, lblLastLoginDate, lblProfessional, lblGender)
        allFields.map { it.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields) }

        fields.map{
            when (it) {
                "lblName" -> lblName.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields_error)
                "lblLastName" -> lblLastName.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields_error)
                "lblEmail" -> lblEmail.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields_error)
                "lblAddress" -> lblAddress.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields_error)
                "lblPhone" -> lblPhone.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields_error)
                "lblBirthdate" -> lblBirthdate.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields_error)
                "lblNationalID" -> lblNationalID.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields_error)
                "lblFellowshipNumber" -> lblFellowshipNumber.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields_error)
                "lblProfessional" -> lblProfessional.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields_error)
                "lblGender" -> lblGender.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields_error)
                else -> toastIt(this, it)
            }
        }
    }


    private fun fillFileds(user: User) {
        etName.setText(user.name)
        etLastName.setText(user.lastName)
        etEmail.setText(user.email)
        etAddress.setText(user.address)
        etPhone.setText(user.phone)
        etBirthdate.setText(formatDateToString(user.birthDate))
        etNationalID.setText(user.nationalId)
        etFellowshipNumber.setText(user.fellowshipNumber)
        etRegistrationDate.setText(formatDateToString(user.registrationDate))
        etLastLoginDate.setText(formatDateToString(user.lastLoginDate))
        swProfesional.isChecked = user.isProfessional
        if (user.gender == "female") {
            rbFemale.isChecked = true
            rbMale.isChecked = false
        } else if (user.gender == "male") {
            rbMale.isChecked = true
            rbFemale.isChecked = false
        }
    }

    private fun getFieldsOrErrors(): Pair<User?, List<String>?> {
        val fieldsWithErrors: MutableList<String> = mutableListOf()

        lateinit var gender: String
        if (rbFemale.isChecked) {
            gender = "female"
        } else if (rbMale.isChecked) {
            gender = "male"
        } else {
            fieldsWithErrors.add("lblGender")
        }

        fillBackgroundColorForFileds(fieldsWithErrors)

        if (fieldsWithErrors.size != 0) return Pair(null, fieldsWithErrors)

        val user = User(
                uId,
                etName.text.toString(),
                etLastName.text.toString(),
                etEmail.text.toString(),
                swProfesional.isChecked,
                etFellowshipNumber.text.toString(),
                gender,
                etAddress.text.toString(),
                etPhone.text.toString(),
                formatStringToDate(etBirthdate.text.toString()),
                etNationalID.text.toString(),
                formatStringToDate(etRegistrationDate.text.toString()),
                formatStringToDate(etLastLoginDate.text.toString())
        )
        return Pair(user, null)
    }

    private fun updateBirthdateInView() {
        val myFormat = "dd/MM/yyyy" // Choose the format you need
        val sdf = SimpleDateFormat(myFormat)
        etBirthdate.setText(sdf.format(calendar.getTime()))
    }

    private fun formatDateToString(date: Date): String{
        val format = SimpleDateFormat("dd/MM/yyyy")
        val d = format.format(date)
        return d
    }

    private fun formatStringToDate(date: String): Date {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val d: Date = sdf.parse(date)
        return d
    }

}
