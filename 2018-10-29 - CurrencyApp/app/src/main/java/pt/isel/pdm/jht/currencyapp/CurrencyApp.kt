package pt.isel.pdm.jht.currencyapp

import android.app.Application
import com.android.volley.toolbox.Volley

class CurrencyApp : Application() {
    val requestQueue by lazy { Volley.newRequestQueue(this) }
}

val Application.requestQueue
        get() = (this as CurrencyApp).requestQueue
