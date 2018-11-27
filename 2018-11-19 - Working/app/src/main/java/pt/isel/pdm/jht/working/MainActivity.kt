package pt.isel.pdm.jht.working

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun doGo(view: View) {

        val workRequest =
                OneTimeWorkRequestBuilder<UselessWorker>()
                        .setConstraints(
                                Constraints.Builder()
                                        .setRequiresCharging(true)
                                        .build()
                        )
                        .build()

        Log.i("Working", "WORK: ${workRequest.id}\tTHREAD: ${Thread.currentThread().id}")
        WorkManager.getInstance().enqueue(workRequest)

        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer {
            textView.text = it.toString()
        })
    }
}

class UselessWorker(context: Context, private val params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {

        for (n in 0..9) {
            Log.i("Working", "STEP $n\tID: ${params.id}\tTHREAD: ${Thread.currentThread().id}")
            Thread.sleep(1500)
        }

        return Result.SUCCESS
    }
}








