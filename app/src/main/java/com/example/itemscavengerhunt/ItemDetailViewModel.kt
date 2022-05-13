package com.example.itemscavengerhunt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class ItemDetailViewModel() : ViewModel() {

    private val itemRepository = ItemRepository.get()
    private val itemIdLiveData = MutableLiveData<UUID>()

    var itemLiveData: LiveData<Item?> =
        Transformations.switchMap(itemIdLiveData) { itemId ->
            itemRepository.getItem(itemId)
        }

    fun loadItem(itemId:UUID){
        itemIdLiveData.value = itemId
    }

    fun saveItem(item: Item) {
        itemRepository.updateItem(item)
    }

}