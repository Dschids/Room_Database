package com.example.roomdatabase.data

import androidx.lifecycle.LiveData

/* use repository to abstract accesss to multiple data sources
suggested best practice for code separation
 */
class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
}