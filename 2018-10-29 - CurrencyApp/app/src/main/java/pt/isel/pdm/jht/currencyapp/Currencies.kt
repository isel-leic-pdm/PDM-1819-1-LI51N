package pt.isel.pdm.jht.currencyapp

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

object Currencies {

    private val url = "http://apilayer.net/api/live" +
            "?access_key=a76c13f17a0d445356c0f4d36738fe92" +
            "&source=USD" +
            "&format=1"

    fun loadCurrencies(queue : RequestQueue, onSuccess : (List<ExchangeRate>) -> Unit, onError : (String) -> Unit) {
        queue.add(
                JsonObjectRequest(Request.Method.GET, url, null,
                        { response ->
                            onSuccess(convertQuotes(response))
                        },
                        { error ->
                            onError(error.message ?: "error")
                        }
                )
        )
    }

    private fun convertQuotes(response: JSONObject): List<ExchangeRate> {
        val result = mutableListOf<ExchangeRate>()
        val quotes = response.getJSONObject("quotes")
        for (curr in quotes.keys()) {
            result.add(ExchangeRate(curr.take(3), curr.takeLast(3), quotes.getDouble(curr)))
        }
        return result
    }
}

