package com.example.roomdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var _instance: UserDatabase? = null

        // function checks if instance exists
        fun getDatabase(context: Context): UserDatabase{
            val tempInstance = _instance
            // if tempInstance is not zero return tempInstance which we set equal to _instance
            if (tempInstance != null){
                return tempInstance
            }
            // if there is no instance we create a new instance of user_database
            synchronized(this){
                // builds the room database named user_database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                _instance = instance
                return instance
            }
        }
    }
}