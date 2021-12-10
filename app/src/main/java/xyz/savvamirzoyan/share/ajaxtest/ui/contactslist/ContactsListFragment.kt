package xyz.savvamirzoyan.share.ajaxtest.ui.contactslist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
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

    val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT + ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }

        override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            if (viewHolder !is ContactsListAdapter.ContactViewHolder.Base) {
                return 0
            }

            return super.getSwipeDirs(recyclerView, viewHolder)
        }
    }

    val swipeHelper = ItemTouchHelper(swipeCallback)

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

        swipeHelper.attachToRecyclerView(recycler)

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

private fun Boolean.toInt() = if (this) 1 else 0