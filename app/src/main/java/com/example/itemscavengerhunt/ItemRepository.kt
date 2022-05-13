package com.example.itemscavengerhunt

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.itemscavengerhunt.database.ItemDatabase
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "item-database"

class ItemRepository private constructor(context: Context) {

    private val database : ItemDatabase = Room.databaseBuilder(
        context.applicationContext,
        ItemDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val itemDao = database.itemDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getItems(): LiveData<List<Item>> = itemDao.getItems()

    fun getItem(id: UUID): LiveData<Item?> = itemDao.getItem(id)

    fun updateItem(item: Item){
        executor.execute{
            itemDao.updateItem(item)
        }
    }

    fun addItem(item: Item) {
        executor.execute {
            itemDao.addItem(item)
        }
    }

    companion object {
        private var INSTANCE: ItemRepository? = null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = ItemRepository(context)
            }
         }
        fun get(): ItemRepository {
            return INSTANCE ?:
            throw IllegalStateException("ItemRepository must be initialized")
        }
    }
}