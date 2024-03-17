package com.example.roomdatabase.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.model.User
import com.example.roomdatabase.databinding.CustomRowBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.bind(currentItem)

//        holder._binding.rowLayout.setOnClickListener{
//            val action = ListFragmentDirections.actionListFragmentToAddFragment()
//        }

    }
    class MyViewHolder(val _binding: CustomRowBinding): RecyclerView.ViewHolder(_binding.root){
        fun bind(ourItem: User){
            _binding.tvID.text = ourItem.id.toString()
            _binding.tvFirstName.text = ourItem.firstName
            _binding.tvLastName.text = ourItem.lastName
            _binding.tvAge.text = ourItem.age.toString()
        }
    }

    fun setData (user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }
}