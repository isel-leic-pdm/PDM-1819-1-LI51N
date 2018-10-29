package pt.isel.pdm.jht.currencyapp

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

data class ExchangeRate(
        val sourceCurrency : String,
        val targetCurrency : String,
        val exchangeRate : Double
)

class CurrenciesViewModel(val app : Application) : AndroidViewModel(app) {

    val currencies = MutableLiveData<List<ExchangeRate>>()

    init {
        updateCurrencies()
    }

    fun updateCurrencies() {
        Currencies.loadCurrencies(
                app.requestQueue,
                {
                    currencies.value = it
                },
                {
                    Toast.makeText(app, "Update failed: $it", Toast.LENGTH_LONG).show()
                }
        )
    }
}
