package xyz.savvamirzoyan.share.ajaxtest.ui.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import xyz.savvamirzoyan.share.ajaxtest.R
import xyz.savvamirzoyan.share.ajaxtest.core.AjaxApplication
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDetailsUi

class UserDetailsFragment : Fragment() {

    val args by navArgs<UserDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = (requireActivity().application as AjaxApplication).contactDetailsViewModel

        val imageViewUserPhoto = view.findViewById<ImageView>(R.id.imageView_userPicture)
        val textViewName = view.findViewById<EditText>(R.id.editText_firstName)
        val textViewSurname = view.findViewById<EditText>(R.id.editText_lastName)
        val textViewEmail = view.findViewById<EditText>(R.id.editText_email)

        viewModel.fetchUser(args.userId)

        lifecycleScope.launchWhenStarted {
            viewModel.contactDetailsUiFlow.collect {
                it.map(object : ContactDetailsUi.ContactDetailsUiMapper {
                    override fun map(name: String, surname: String, email: String, photoUrl: String) {
                        textViewName.setText(name)
                        textViewSurname.setText(surname)
                        textViewEmail.setText(email)
                        Glide.with(view)
                            .load(photoUrl)
                            .placeholder(R.drawable.ic_round_person_48)
                            .error(R.drawable.ic_round_person_48)
                            .into(imageViewUserPhoto)
                    }
                })
            }
        }
    }
}