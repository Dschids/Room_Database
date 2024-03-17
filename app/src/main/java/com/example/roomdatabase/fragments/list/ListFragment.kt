package com.example.roomdatabase.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.viewmodel.UserViewModel
import com.example.roomdatabase.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var _binding: FragmentListBinding
    private lateinit var mUserViewModel: UserViewModel

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
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // observes for changes and updates the list when there are any
        mUserViewModel.readAllData.observe(viewLifecycleOwner){
            user ->
            // calls the setData function from ListAdapter
            adapter.setData(user)
        }


        return view
    }

}