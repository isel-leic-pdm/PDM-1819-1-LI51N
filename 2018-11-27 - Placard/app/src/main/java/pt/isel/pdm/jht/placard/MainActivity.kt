package pt.isel.pdm.jht.placard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val model by lazy {
        ViewModelProviders.of(this).get(PlacardModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.message.observe(this, Observer { message ->
            txtMessage.text = message
        })
    }

    fun doPost(view: View) {
        model.post(edtNewMessage.text.toString())
    }
}
