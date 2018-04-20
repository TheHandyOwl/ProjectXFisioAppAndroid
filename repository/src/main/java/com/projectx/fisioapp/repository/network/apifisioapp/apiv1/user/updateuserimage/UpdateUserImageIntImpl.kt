package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.updateuserimage

import android.graphics.drawable.Drawable
import com.projectx.fisioapp.repository.entitymodel.responses.UpdateUserImageResponse
import com.projectx.fisioapp.repository.entitymodel.user.UserData
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppClient
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppInterface
import okhttp3.MediaType
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.RequestBody
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import java.io.ByteArrayOutputStream


class UpdateUserImageIntImpl(): UpdateUserImageInteractor {
    override fun execute(token: String, id: String, image: Drawable, success: (ok: Boolean, user: UserData, message: String) -> Unit, error: (errorMessage: String) -> Unit) {

        val apiInterfaceLocalhost: APIV1FisioAppInterface =
                APIV1FisioAppClient.client.create(APIV1FisioAppInterface::class.java)

        /**
         * Drawable 2 JPEG
         */
        val bitmapDrawable = image as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val imageInByte = stream.toByteArray()

        /**
         * Update User Image
         */
        val fileName = "$id.jpg"
        val reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageInByte)
        val body = MultipartBody.Part.createFormData("image", fileName, reqFile)

        val updateUserImage = apiInterfaceLocalhost.doUpdateUserImage( token, id, body)
        updateUserImage.enqueue(object : Callback<UpdateUserImageResponse> {

            override fun onResponse(call: Call<UpdateUserImageResponse>, response: Response<UpdateUserImageResponse>) {
                val backResponse = response.body()
                val ok = backResponse?.ok ?: false
                val userData = backResponse?.user as UserData
                val message = backResponse?.message as String
                if (ok) success(ok, userData, message)
            }

            override fun onFailure(call: Call<UpdateUserImageResponse>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })


    }

}