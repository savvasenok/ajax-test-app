package xyz.savvamirzoyan.share.ajaxtest.ui.contactslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import xyz.savvamirzoyan.share.ajaxtest.R

class ContactsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView_contactsList)
        val adapter = ContactsListAdapter()
        recycler.adapter = adapter

        super.onViewCreated(view, savedInstanceState)
    }
}