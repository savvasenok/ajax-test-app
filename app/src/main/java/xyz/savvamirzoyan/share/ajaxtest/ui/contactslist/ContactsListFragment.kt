package xyz.savvamirzoyan.share.ajaxtest.ui.contactslist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import xyz.savvamirzoyan.share.ajaxtest.R
import xyz.savvamirzoyan.share.ajaxtest.core.AjaxApplication
import xyz.savvamirzoyan.share.ajaxtest.core.ClickListener
import xyz.savvamirzoyan.share.ajaxtest.core.Retry

class ContactsListFragment : Fragment() {

    val viewModel by lazy {
        (requireActivity().application as AjaxApplication).contactsListViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView_contactsList)
        val adapter = ContactsListAdapter(
            object : Retry {
                override fun tryAgain() {
                    viewModel.fetchContacts()
                }
            },
            object : ClickListener<Int> {
                override fun click(item: Int) {
                    openDetails(item)
                }
            }
        )
        recycler.adapter = adapter

        viewModel.fetchContacts()

        lifecycleScope.launchWhenStarted {
            viewModel.contactsUiFlow.collect {
                adapter.update(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_contacts_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItem_reload -> true.also { viewModel.reloadContacts() }
        }

        return true
    }

    private fun openDetails(userId: Int) {
        val action = ContactsListFragmentDirections.toUserDetailsFragment(userId)
        Navigation.findNavController(requireView()).navigate(action)
    }
}