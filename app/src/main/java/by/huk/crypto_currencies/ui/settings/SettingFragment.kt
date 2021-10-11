package by.huk.crypto_currencies.ui.settings

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.icu.text.DateFormat
import android.icu.text.DateFormat.getDateInstance
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import by.huk.crypto_currencies.MainViewModel
import by.huk.crypto_currencies.R
import by.huk.crypto_currencies.data.entities.user.User
import by.huk.crypto_currencies.databinding.FragmentSettingsBinding
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import java.io.File
import java.text.DateFormat.getDateInstance
import java.util.*

// TODO !REFACTORING! Remove unused imports in all classes
class SettingFragment : Fragment() {


    private val viewModel by inject<MainViewModel>()

    private lateinit var photoFile: File
    private lateinit var photoSelectResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var takePhotoResultLauncher: ActivityResultLauncher<Intent>

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var user: User? = null

    private var firstNameIsFilled = false
    private var lastNameIsFilled = false
    private var birthdayNameIsFilled = false
    private var saveButtonIsLock = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO !REFACTORING! You have a lot of empеy spaces between lines. It does not make sense and
        //  it only increases amount of lines and class size



        viewModel.loadUser()
        viewModel.user.observe(requireActivity()) {
            user = it
            fillFields(it)
        }
        //TODO !REFACTORING! Use construction with(something) { } in all classes where it is possible
        binding.firstNameEdittext.doAfterTextChanged {
            firstNameIsFilled = it!!.isNotEmpty()
            fieldsIsFilled()
        }
        binding.lastNameEdittext.doAfterTextChanged {
            lastNameIsFilled = it!!.isNotEmpty()
            fieldsIsFilled()
        }
        binding.birthdayEdittext.doAfterTextChanged {
            birthdayNameIsFilled = it!!.isNotEmpty()
            fieldsIsFilled()
        }




        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.date_of_birth))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .build()

        datePicker.addOnPositiveButtonClickListener {
            binding.birthdayEdittext.setText(DateFormat.getDateInstance().format(it))
        }

        binding.birthdayEdittext.setOnClickListener{ // TODO !REFACTORING! Use ctrl+alt+L hotkey fo code formatting in all classes
            datePicker.show(requireActivity().supportFragmentManager, "tag")
        }


        val items = arrayOf(getString(R.string.take_photo), getString(R.string.pick_from_gallery))
        binding.addPhotoBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.download_photo))
                .setItems(items) { _, which ->
                    when (which) {
                        0 -> takePhoto()
                        1 -> selectPhoto()
                    }
                }.show()
        }


        binding.settingToolbar.menu.findItem(R.id.item_save).setOnMenuItemClickListener {
            if (user != null && !saveButtonIsLock) {
                user?.firstName = binding.firstNameEdittext.text.toString()
                user?.secondName = binding.lastNameEdittext.text.toString()
                user?.birthday = binding.birthdayEdittext.text.toString()

                viewModel.updateUser(user!!)

                Toast.makeText(requireContext(), getString(R.string.saved), Toast.LENGTH_SHORT)
                    .show()
                it.setIcon(R.drawable.ic_save)
                saveButtonIsLock = true
            }
            true
        }



        photoSelectResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val photoUri = it.data?.data

                    user?.photo = photoUri.toString()
                    Glide.with(this).load(photoUri).into(binding.userPhoto)
                }
            }
        takePhotoResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val uri = photoFile.toUri().toString()
                    user?.photo = uri
                    Glide.with(this).load(uri).into(binding.userPhoto)

                }
            }


    }

    private fun fieldsIsFilled() {
        saveButtonIsLock = if (firstNameIsFilled && lastNameIsFilled && birthdayNameIsFilled) {
            binding.settingToolbar.menu.findItem(R.id.item_save).setIcon(R.drawable.ic_save_unlock)
            false
        } else {
            binding.settingToolbar.menu.findItem(R.id.item_save).setIcon(R.drawable.ic_save)
            true
        }
    }

    private fun fillFields(user: User) {
        Glide.with(this).load(user.photo).error(R.drawable.ic_person).into(binding.userPhoto)
        binding.firstNameEdittext.setText(user.firstName)
        binding.lastNameEdittext.setText(user.secondName)
        binding.birthdayEdittext.setText(user.birthday)
    }


    private fun selectPhoto() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"

        photoSelectResultLauncher.launch(intent)
    }

    private fun takePhoto() {
        val storage = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        photoFile = File.createTempFile("photo", ".jpeg", storage)
        val fileProvider = FileProvider.getUriForFile(requireContext(),
            "by.huk.crypto_currencies.fileprovider",
            photoFile)
        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

        takePhotoResultLauncher.launch(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}