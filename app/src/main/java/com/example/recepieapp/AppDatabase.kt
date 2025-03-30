package com.example.recepieapp

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDao() : Dao

}