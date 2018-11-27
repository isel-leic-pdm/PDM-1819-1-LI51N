package pt.isel.pdm.jht.placard

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class Placard(private val app: Application, private val deviceId: String) {

    val database = FirebaseFirestore.getInstance()

    val messages = database.collection("messages")

    val placard = messages.document("placard")

    init {
        placard.addSnapshotListener { doc, ex ->
            if (ex != null) {
                Log.w("placard", "Listen failed: ${ex.message}", ex)
            } else if (doc != null && doc.exists()) {
                message.value = doc.toObject(Message::class.java)?.text
            }
        }
    }

    val message = MutableLiveData<String>()

    fun post(newMessage: String) {
        //message.value = newMessage
        placard.set(
            Message(deviceId, newMessage)
        ).addOnSuccessListener {
            Toast.makeText(app, "POSTED", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(app, "FAILED", Toast.LENGTH_SHORT).show()
        }
    }
}
