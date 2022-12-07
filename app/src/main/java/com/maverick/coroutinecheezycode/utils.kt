package com.maverick.coroutinecheezycode

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Context.isNetworkAvailable(): Flow<Boolean> = callbackFlow {

    val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            trySend(true).isSuccess
        }

        override fun onUnavailable() {
            super.onUnavailable()
            trySend(false).isSuccess
        }
    }

    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    manager.registerNetworkCallback(NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build(), callback)

    awaitClose {
        manager.unregisterNetworkCallback(callback)
    }
}

fun EditText.onChange(): Flow<Editable?> = callbackFlow {

    val callback = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            trySend(s).isSuccess
        }

    }

    awaitClose {
        removeTextChangedListener(callback)
    }

}