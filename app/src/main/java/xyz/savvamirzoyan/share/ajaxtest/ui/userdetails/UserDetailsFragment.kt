package xyz.savvamirzoyan.share.ajaxtest.ui.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import xyz.savvamirzoyan.share.ajaxtest.R
import xyz.savvamirzoyan.share.ajaxtest.core.AjaxApplication
import xyz.savvamirzoyan.share.ajaxtest.core.TextWatcherWrapper
import xyz.savvamirzoyan.share.ajaxtest.ui.ContactDetailsUi

class UserDetailsFragment : Fragment() {

    private val args by navArgs<UserDetailsFragmentArgs>()

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
        val deleteButton = view.findViewById<Button>(R.id.button_deleteContact)
        val saveButton = view.findViewById<Button>(R.id.button_saveContact)

        deleteButton.setOnClickListener {
            viewModel.deleteUser(args.userId)
        }

        saveButton.setOnClickListener {
            viewModel.saveChanges(args.userId)
        }

        textViewName.addTextChangedListener(object : TextWatcherWrapper {
            override fun onTextChanged(text: String) {
                viewModel.updateName(text)
            }
        })

        textViewSurname.addTextChangedListener(object : TextWatcherWrapper {
            override fun onTextChanged(text: String) {
                viewModel.updateSurname(text)
            }
        })

        textViewEmail.addTextChangedListener(object : TextWatcherWrapper {
            override fun onTextChanged(text: String) {
                viewModel.updateEmail(text)
            }
        })

        viewModel.fetchUser(args.userId)

        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.contactDetailsUiFlow.collect {
                    it.map(object : ContactDetailsUi.ContactDetailsUiMapper {
                        override fun map(
                            name: String,
                            surname: String,
                            email: String,
                            photoUrl: String,
                            thumbnailUrl: String
                        ) {
                            textViewName.setText(name)
                            textViewSurname.setText(surname)
                            textViewEmail.setText(email)

                            Glide.with(view)
                                .load(photoUrl)
                                .error(
                                    Glide.with(view)
                                        .load(thumbnailUrl)
                                        .placeholder(R.drawable.ic_round_person_48)
                                )
                                .placeholder(R.drawable.ic_round_person_48)
                                .into(imageViewUserPhoto)
                        }
                    })
                }
            }
            launch {
                viewModel.returnBackFlow.collect {
                    Navigation.findNavController(view).popBackStack()
                }
            }
            launch {
                viewModel.nameErrorFlow.collect {
                    textViewName.error = it
                }
            }
            launch {
                viewModel.surnameErrorFlow.collect {
                    textViewSurname.error = it
                }
            }
            launch {
                viewModel.emailErrorFlow.collect {
                    textViewEmail.error = it
                }
            }
            launch {
                viewModel.saveButtonIsEnableFlow.collect {
                    saveButton.isEnabled = it
                }
            }
        }
    }
}