package pt.isel.pdm.jht.tryviewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val counterModel by lazy { ViewModelProviders.of(this).get(CounterModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterModel.getData().observe(this, Observer<Int> {
              update(it)
        })
    }

    private fun update(c : Int) {
        countView.text = c.toString()
    }

    fun doDec(view: View) {
        counterModel.dec()
    }

    fun doInc(view: View) {
        counterModel.inc()
    }

    fun doNew(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}

class CounterModel : ViewModel() {
    private var count = 0
    private val countData = MutableLiveData<Int>()

    init {
        countData.value = count
    }

    fun dec() { countData.value = --count }
    fun inc() { countData.value = ++count  }

    fun get() = count
    fun getData() = countData
}



