package com.example.itemscavengerhunt.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.itemscavengerhunt.Item

@Database(entities = [Item::class], version=1)
@TypeConverters(ItemTypeConverters::class)
abstract class ItemDatabase : RoomDatabase () {
    abstract fun itemDao(): ItemDao

}