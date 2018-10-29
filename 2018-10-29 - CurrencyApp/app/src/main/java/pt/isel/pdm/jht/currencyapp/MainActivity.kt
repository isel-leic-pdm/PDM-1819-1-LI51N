package pt.isel.pdm.jht.currencyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListAdapter
import android.widget.SimpleAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val currenciesModel by lazy {
        ViewModelProviders
                .of(this)
                .get(CurrenciesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currenciesModel.currencies.observe(this, Observer <List<ExchangeRate>> {
            // it.joinToString("\n", transform = { it.toString() })
            listView.adapter = buildAdapter(it)
        })
    }

    private fun buildAdapter(rates : List<ExchangeRate>) : ListAdapter =
            SimpleAdapter(
                    this,
                    rates.map {
                        rate -> mapOf(
                            "target" to rate.targetCurrency,
                            "value" to rate.exchangeRate
                        )
                    },
                    android.R.layout.simple_list_item_2,
                    arrayOf("target", "value"),
                    intArrayOf(android.R.id.text1, android.R.id.text2)
            )

}
