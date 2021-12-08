package xyz.savvamirzoyan.share.ajaxtest.ui.contactslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import xyz.savvamirzoyan.share.ajaxtest.R
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactUi

class ContactsListAdapter : RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder>() {

    private companion object {
        private const val TYPE_BASE = 0
        private const val TYPE_ERROR = 1
        private const val TYPE_PROGRESS = 2
    }

    private val contacts = ArrayList<ContactUi>()

    fun update(newContacts: List<ContactUi>) {
        contacts.clear()
        contacts.addAll(newContacts)
        notifyDataSetChanged()
        // TODO: Add diffutils
    }

    override fun getItemViewType(position: Int): Int = when (contacts[position]) {
        is ContactUi.Base -> TYPE_BASE
        is ContactUi.Fail -> TYPE_ERROR
        ContactUi.Progress -> TYPE_PROGRESS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return when (viewType) {
            TYPE_BASE -> ContactViewHolder.Base(R.layout.layout_contacts_list_item.makeView(parent))
            TYPE_PROGRESS -> ContactViewHolder.Progress(R.layout.layout_contacts_list_progress.makeView(parent))
            else -> ContactViewHolder.Error(R.layout.layout_contacts_list_error.makeView(parent))
        }
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount() = contacts.size

    abstract class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        open fun bind(contactUi: ContactUi) {}

        class Progress(view: View) : ContactViewHolder(view)

        class Base(private val view: View) : ContactViewHolder(view) {
            private val userFullName = view.findViewById<TextView>(R.id.textView_userFullName)
            private val userPhoto = view.findViewById<ImageView>(R.id.imageView_userPicture)

            override fun bind(contactUi: ContactUi) {
                contactUi.map(object : ContactUi.ContactUiMapper {
                    override fun map(id: Int, name: String, photoUrl: String) {
                        userFullName.text = name
                        Glide.with(view).load(photoUrl).into(userPhoto)
                    }

                    // TODO: maybe empty mapper is not a good idea
                    override fun map(error: String) {}
                })
            }
        }

        class Error(view: View) : ContactViewHolder(view) {
            private val errorMessage = view.findViewById<TextView>(R.id.textView_errorReason)

            override fun bind(contactUi: ContactUi) {
                contactUi.map(object : ContactUi.ContactUiMapper {
                    override fun map(error: String) {
                        errorMessage.text = error
                    }

                    override fun map(id: Int, name: String, photoUrl: String) {}
                })
            }
        }
    }
}

private fun Int.makeView(parent: ViewGroup) = LayoutInflater
    .from(parent.context)
    .inflate(this, parent, false)
