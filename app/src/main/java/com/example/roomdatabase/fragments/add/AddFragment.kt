package com.example.roomdatabase.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdatabase.R
import com.example.roomdatabase.model.User
import com.example.roomdatabase.viewmodel.UserViewModel
import com.example.roomdatabase.databinding.FragmentAddBinding



class AddFragment : Fragment() {

    private lateinit var _binding: FragmentAddBinding
    private lateinit var mUserViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        _binding.btnAdd.setOnClickListener {
            insertDataToDatabase()
        }

        return _binding.root
    }

    private fun insertDataToDatabase() {
        // get strings from edit text boxes
        val firstName = _binding.etFirstName.text.toString()
        val lastName = _binding.etLastName.text.toString()
        val age = _binding.etAge.text.toString().toInt()

        // if there is data in all three boxes
        if (inputCheck(firstName, lastName, age.toString())){
            // create a User object
            val user = User(0, firstName, lastName, age)
            // add data to database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Entry added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age))
    }

}