package com.projectx.fisioapp.repository.internetstatus

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.projectx.fisioapp.repository.CodeClosure
import com.projectx.fisioapp.repository.ErrorClosure


class InternetStatusIntImpl(val context: Context): InternetStatusInteractor {

    internal lateinit var connectivityManager: ConnectivityManager
    internal var connected = false

    override fun execute(success: CodeClosure, error: ErrorClosure) {

        val msgError = "Please, check your internet connection"

        try {
            connectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkInfo = connectivityManager.activeNetworkInfo
            connected = networkInfo != null
                    && networkInfo.isAvailable
                    && networkInfo.isConnected
            if (connected) return success()

        } catch (e: Exception) {
            Log.d("connectivity", "CheckConnectivity Exception: " + e.message)
            Log.d("connectivity", e.toString())
        }

        return  error(msgError)

    }

}