package com.example.roomdatabase.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentListBinding
import com.example.roomdatabase.databinding.FragmentUpdateBinding
import com.example.roomdatabase.model.User
import com.example.roomdatabase.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var _updateBinding: FragmentUpdateBinding
    private lateinit var ufUpdateViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _updateBinding = FragmentUpdateBinding.inflate(inflater, container, false)
        // this is the value we return, could just put return _updateBinding.root, tomato potato
        val view = _updateBinding.root

        ufUpdateViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        _updateBinding.etUpdateFirstName.setText(args.currentUser.firstName)
        _updateBinding.etUpdateLastName.setText(args.currentUser.lastName)
        _updateBinding.etUpdateAge.setText(args.currentUser.age.toString())

        // onclick listener calls the updateItem function
        _updateBinding.btnUpdate.setOnClickListener{
            updateItem()
        }

        // add the delete menu
        setHasOptionsMenu(true)

        return view
    }

    // function to update entity in database
    private fun updateItem(){
        // create variables from input fields, make sure to have toString()/toInt()
        val firstName = _updateBinding.etUpdateFirstName.text.toString()
        val lastName = _updateBinding.etUpdateLastName.text.toString()
        val age = _updateBinding.etUpdateAge.text.toString().toInt()

        // if there is data in all three boxes
        if (inputCheck(firstName, lastName, age.toString())){
            // create a User object, use args to get the id of the current user
            val updateUser = User(args.currentUser.id, firstName, lastName, age)
            // calls updateUser function from UserViewModel
            ufUpdateViewModel.updateUser(updateUser)
            Toast.makeText(requireContext(), "Entry updated", Toast.LENGTH_SHORT).show()
            /* if you copy paste this check from the add fragment make sure you change
            action_addFragment to action_updateFragment or the app crashes
             */
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteUser()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        // create a alert dialog box
        val builder = AlertDialog.Builder(requireContext())
        // positive button appears as "yes", {_,_ shows that there will be two buttons
        builder.setPositiveButton("Yes"){_,_->
            ufUpdateViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(),
                "${args.currentUser.firstName} deleted!",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        // negative button selection does nothing but get rid of alert box
        builder.setNegativeButton("No"){_,_ -> }
        // title of the alert box
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        // message inside alert box above positive and negative buttons
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
        // create and show the alert box
        builder.create().show()
    }

}