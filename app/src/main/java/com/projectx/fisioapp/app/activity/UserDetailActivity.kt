package com.projectx.fisioapp.app.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.utils.CircleTransform
import com.projectx.fisioapp.app.utils.toastIt
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.users.getuser.GetUserIntImpl
import com.projectx.fisioapp.domain.interactor.users.getuser.GetUserInteractor
import com.projectx.fisioapp.domain.interactor.users.updateuserimage.UpdateUserImageIntImpl
import com.projectx.fisioapp.domain.interactor.users.updateuserimage.UpdateUserImageInteractor
import com.projectx.fisioapp.domain.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_detail.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import java.io.File

class UserDetailActivity : ParentActivity(), View.OnFocusChangeListener {

    private lateinit var user: User
    private var userWithChanges: User? = null
    private var calendar = Calendar.getInstance()

    private var imageFilenameChanged: String? = null
    private lateinit var imageFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        //Back button
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setListeners()

        roundedPhoto(R.drawable.no_image)

        getUser()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v?.id) {
            ivPhoto.id -> if(hasFocus) Log.d("App", "focus") else Log.d("App", "NO focus")
            else -> Log.d("App", "ELSE")
        }
    }

    private fun setListeners() {

        ivPhoto.setOnClickListener {
            try {
                selectImageInAlbum()
            } catch (e: Exception) {
                Log.d("App", "Error: ${e.localizedMessage}")
            }
        }

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

    private fun roundedPhoto(imageId: Int) {
        val img = BitmapFactory.decodeResource(resources, imageId)
        val round = RoundedBitmapDrawableFactory.create(resources, img)

        round.isCircular = true
        ivPhoto.setImageDrawable(round)
    }

    private fun getUser() {
        val getUser: GetUserInteractor = GetUserIntImpl(this)

        try {
            getUser.execute(
                    token,
                    uId,
                    success = {
                        user = it
                        fillFields(it)
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

        val updateUserImage: UpdateUserImageInteractor = UpdateUserImageIntImpl(this)

        try {
            updateUserImage.execute(
                    token,
                    user.id,
                    ivPhoto.drawable,
                    success = { ok: Boolean, user: User, message: String ->
                        if (ok) {
                            toastIt(this, "Message: $message")
                            //fillFields(user)
                            toastIt(this, "Image updated")
                        }
                        else {
                            toastIt(this, "There was an error uploading the image")
                        }
                    }, error = object : ErrorCompletion {
                        override fun errorCompletion(errorMessage: String) {
                            toastIt(baseContext, errorMessage)
                        }
                    })
        } catch (e: Exception) {
            toastIt(this, "Error: " + e.localizedMessage )
        }
        /*
        val updateUser: UpdateUserInteractor = UpdateUserIntImpl(this)

        try {
            updateUser.execute(
                    token,
                    userWithChanges as User,
                    success = { ok: Boolean, user: User ->
                        if (ok) {
                            fillFields(user)
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
        */
    }

    private fun fillBackgroundColorForFields(fields: List<String>) {

        val allFields : MutableList<View> = mutableListOf(lblPhoto, lblName, lblLastName, lblLastName, lblEmail, lblAddress, lblPhone, lblBirthdate, lblNationalID, lblFellowshipNumber,lblRegistrationDate, lblLastLoginDate, lblProfessional, lblGender)
        allFields.map { it.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields) }

        fields.map{
            when (it) {
                "lblPhoto" -> lblPhoto.background = ContextCompat.getDrawable(this, R.drawable.gradient_left_column_fields_error)
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

    private fun fillFields(user: User) {
        user.img?.let {
            imageFilenameChanged = it
            Picasso.with(this)
                //.load("https://i.pinimg.com/originals/50/54/3a/50543adfc79f3209893aa528d35142ba.jpg")
                    // .load("http://192.168.1.41:3000/apiv1/images/users/$imageFilenameChanged")
                    .load("http://192.168.1.41:3000/apiv1/images/users/$imageFilenameChanged")
                .transform(CircleTransform())
                .placeholder(R.drawable.no_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(ivPhoto)
        }
        user.name?.let { etName.setText(it) }
        user.lastName?.let { etLastName.setText(it) }
        user.email?.let { etEmail.setText(it) }
        user.address?.let { etAddress.setText(it) }
        user.phone?.let { etPhone.setText(it) }
        user.birthDate?.let { etBirthdate.setText(formatDateToString(it)) }
        user.nationalId?.let { etNationalID.setText(it) }
        user.fellowshipNumber?.let { etFellowshipNumber.setText(it) }
        user.registrationDate?.let { etRegistrationDate.setText(formatDateToString(it)) }
        user.lastLoginDate?.let { etLastLoginDate.setText(formatDateToString(it)) }
        user.name?.let { etName.setText(it) }
        user.name?.let { etName.setText(it) }
        user.isProfessional?.let { swProfesional.isChecked = it }
        user.gender?.let {
            if (it == "female") {
                rbFemale.isChecked = true
                rbMale.isChecked = !rbFemale.isChecked
            } else if (it == "male") {
                rbMale.isChecked = true
                rbFemale.isChecked = !rbMale.isChecked
            }
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

        fillBackgroundColorForFields(fieldsWithErrors)

        if (fieldsWithErrors.size != 0) return Pair(null, fieldsWithErrors)

        // Test image upload
        //imageFilenameChanged = "https://static.affinity-petcare.com/advance/cdn/farfuture/6-_63ZLwTwPDjBFAaZfOdXjOsdXua-pw3T_lgULbJkE/drupal-cache:p7c4h7/sites/default/files/img_gatitos_body.png"

        val user = User(
                uId,
                imageFilenameChanged,
                etName.text.toString(),
                etLastName.text.toString(),
                etEmail.text.toString(),
                swProfesional.isChecked,
                etFellowshipNumber.text.toString(),
                gender,
                etAddress.text.toString(),
                etPhone.text.toString(),
                etBirthdate.text.toString()?.let {  formatStringToDate(it) },
                etNationalID.text.toString(),
                etRegistrationDate.text.toString()?.let { formatStringToDate(it) },
                etLastLoginDate.text.toString().let { formatStringToDate(it) }
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

    private fun formatStringToDate(date: String): Date? {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val d: Date = sdf.parse(date)
            return d
        } catch (e: Exception) {
            Log.e("Error", e.localizedMessage)
        }
        return null

    }

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            try {
                startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
            } catch (e: Exception) {
                Log.d("App", "Error: ${e.localizedMessage}")
            }

        }
    }
    fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            REQUEST_SELECT_IMAGE_IN_ALBUM -> {
                if (resultCode === Activity.RESULT_OK) {
                    Toast.makeText(this, "Picture taken!", Toast.LENGTH_SHORT).show()
                    // by this point we have the camera photo on disk
                    val uri = data.data
                    uri.let {
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                        bitmap.let { ivPhoto.setImageBitmap(it) }
                    }
                    imageFile = File(uri.toString())

                } else { // Result was a failure
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_TAKE_PHOTO = 0
        private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    }

}
