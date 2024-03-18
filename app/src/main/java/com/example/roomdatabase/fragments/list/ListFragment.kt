package com.example.roomdatabase.fragments.list

import android.app.AlertDialog
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.viewmodel.UserViewModel
import com.example.roomdatabase.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var _binding: FragmentListBinding
    private lateinit var lfUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        // this is the value we return, could just put return _binding.root, tomato potato
        val view = _binding.root

        _binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        // set the adapter to the ListAdapter I made
        val adapter = ListAdapter()
        // create a recyclerview variable for ease of use (don't have to keep typing _binding.rvlist)
        val recyclerView = _binding.rvList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        lfUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // observes for changes and updates the list when there are any
        lfUserViewModel.readAllData.observe(viewLifecycleOwner){
            user ->
            // calls the setData function from ListAdapter
            adapter.setData(user)
        }

        // add options menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllUsers()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        // create a alert dialog box
        val builder = AlertDialog.Builder(requireContext())
        // positive button appears as "yes", {_,_ shows that there will be two buttons
        builder.setPositiveButton("Yes"){_,_->
            lfUserViewModel.deleteAllUsers()
            Toast.makeText(requireContext(),
                "All entries deleted!",
                Toast.LENGTH_SHORT).show()
        }
        // negative button selection does nothing but get rid of alert box
        builder.setNegativeButton("No"){_,_ -> }
        // title of the alert box
        builder.setTitle("Delete all items?")
        // message inside alert box above positive and negative buttons
        builder.setMessage("Are you sure you want to delete all items?")
        // create and show the alert box
        builder.create().show()
    }

}
