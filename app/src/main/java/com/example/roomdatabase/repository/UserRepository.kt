package com.example.roomdatabase.repository

import androidx.lifecycle.LiveData
import com.example.roomdatabase.model.User
import com.example.roomdatabase.data.UserDao

/* use repository to abstract accesss to multiple data sources
suggested best practice for code separation
 */
class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }
}