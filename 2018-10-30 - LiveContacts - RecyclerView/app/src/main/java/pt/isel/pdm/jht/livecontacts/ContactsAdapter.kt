package pt.isel.pdm.jht.livecontacts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ContactViewHolder(val view: ViewGroup) : RecyclerView.ViewHolder(view) {
    private val contactNameView = view.findViewById<TextView>(android.R.id.text1)
    private val contactKeyView = view.findViewById<TextView>(android.R.id.text2)

    fun bindTo(contact: ContactSummary) {
        contactNameView.text = contact.name
        contactNameView.setTextColor(ContextCompat.getColor(view.context, getColor(contact.name)))
        contactKeyView.text = contact.key
    }

    private fun getColor(name: String) =
        if (name[0] == 'A') android.R.color.holo_red_dark else android.R.color.black
}

class ContactsAdapter(val contacts: List<ContactSummary>) : RecyclerView.Adapter<ContactViewHolder>() {

    override fun getItemCount(): Int = contacts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
            ContactViewHolder(
                    LayoutInflater
                            .from(parent.context)
                            .inflate(android.R.layout.simple_list_item_2, parent, false) as ViewGroup
            )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
            holder.bindTo(contacts[position])
}