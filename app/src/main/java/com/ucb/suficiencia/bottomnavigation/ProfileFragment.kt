package com.ucb.suficiencia.bottomnavigation

import ProfileViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val profileViewModel: ProfileViewModel by activityViewModels()  // Shared ViewModel
    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val editButton = view.findViewById<Button>(R.id.btn_edit)
        val saveButton = view.findViewById<Button>(R.id.btn_save)
        val cancelButton = view.findViewById<Button>(R.id.btn_cancel)

        val nameText = view.findViewById<TextView>(R.id.tv_name)
        val nameEdit = view.findViewById<EditText>(R.id.et_name)

        val genderRadioGroup = view.findViewById<RadioGroup>(R.id.rg_gender)
        val genderText = view.findViewById<TextView>(R.id.tv_gender)

        val interestsLayout = view.findViewById<LinearLayout>(R.id.ll_interests)
        val interestsText = view.findViewById<TextView>(R.id.tv_interests)
        val sportsCheckbox = view.findViewById<CheckBox>(R.id.cb_sports)
        val musicCheckbox = view.findViewById<CheckBox>(R.id.cb_music)
        val travelCheckbox = view.findViewById<CheckBox>(R.id.cb_travel)
        val animeCheckbox = view.findViewById<CheckBox>(R.id.cb_anime)
        val videoGamesCheckbox = view.findViewById<CheckBox>(R.id.cb_video_games)
        val drawingCheckbox = view.findViewById<CheckBox>(R.id.cb_drawing)

        // Observe ViewModel data
        profileViewModel.name.observe(viewLifecycleOwner, Observer { name ->
            nameText.text = name
            nameEdit.setText(name)
        })

        profileViewModel.gender.observe(viewLifecycleOwner, Observer { gender ->
            genderText.text = gender
            if (gender == "Male") {
                genderRadioGroup.check(R.id.rb_male)
            } else {
                genderRadioGroup.check(R.id.rb_female)
            }
        })

        profileViewModel.interests.observe(viewLifecycleOwner, Observer { interests ->
            interestsText.text = interests.joinToString(", ")

            // Set checkboxes based on interests
            sportsCheckbox.isChecked = interests.contains("Sports")
            musicCheckbox.isChecked = interests.contains("Music")
            travelCheckbox.isChecked = interests.contains("Travel")
            animeCheckbox.isChecked = interests.contains("Anime")
            videoGamesCheckbox.isChecked = interests.contains("Video Games")
            drawingCheckbox.isChecked = interests.contains("Drawing")
        })

        // Toggle Edit Mode
        editButton.setOnClickListener {
            isEditing = !isEditing
            toggleEditMode(isEditing, view)
        }

        // Save profile and switch back to view mode
        saveButton.setOnClickListener {
            val name = nameEdit.text.toString()
            val gender = when (genderRadioGroup.checkedRadioButtonId) {
                R.id.rb_male -> "Male"
                R.id.rb_female -> "Female"
                else -> ""
            }
            val interests = mutableListOf<String>()
            if (sportsCheckbox.isChecked) interests.add("Sports")
            if (musicCheckbox.isChecked) interests.add("Music")
            if (travelCheckbox.isChecked) interests.add("Travel")
            if (animeCheckbox.isChecked) interests.add("Anime")
            if (videoGamesCheckbox.isChecked) interests.add("Video Games")
            if (drawingCheckbox.isChecked) interests.add("Drawing")

            // Validation: Check if all fields are filled
            if (name.isEmpty() || gender.isEmpty() || interests.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show()
            } else {
                // Update ViewModel
                profileViewModel.setName(name)
                profileViewModel.setGender(gender)
                profileViewModel.setInterests(interests)

                // Show toast and switch back to view mode
                Toast.makeText(requireContext(), "Profile Saved", Toast.LENGTH_SHORT).show()
                toggleEditMode(false, view)
            }
        }

        // Cancel edit and revert to view mode
        cancelButton.setOnClickListener {
            toggleEditMode(false, view)
        }

        return view
    }

    private fun toggleEditMode(editing: Boolean, view: View) {
        val nameText = view.findViewById<TextView>(R.id.tv_name)
        val nameEdit = view.findViewById<EditText>(R.id.et_name)
        val genderRadioGroup = view.findViewById<RadioGroup>(R.id.rg_gender)
        val genderText = view.findViewById<TextView>(R.id.tv_gender)
        val interestsText = view.findViewById<TextView>(R.id.tv_interests)
        val interestsLayout = view.findViewById<LinearLayout>(R.id.ll_interests)

        val editButton = view.findViewById<Button>(R.id.btn_edit)
        val saveCancelLayout = view.findViewById<LinearLayout>(R.id.ll_save_cancel_buttons)

        if (editing) {
            nameText.visibility = View.GONE
            nameEdit.visibility = View.VISIBLE

            genderText.visibility = View.GONE
            genderRadioGroup.visibility = View.VISIBLE

            interestsText.visibility = View.GONE
            interestsLayout.visibility = View.VISIBLE

            editButton.visibility = View.GONE
            saveCancelLayout.visibility = View.VISIBLE
        } else {
            nameText.visibility = View.VISIBLE
            nameEdit.visibility = View.GONE

            genderText.visibility = View.VISIBLE
            genderRadioGroup.visibility = View.GONE

            interestsText.visibility = View.VISIBLE
            interestsLayout.visibility = View.GONE

            editButton.visibility = View.VISIBLE
            saveCancelLayout.visibility = View.GONE
        }
    }
}
