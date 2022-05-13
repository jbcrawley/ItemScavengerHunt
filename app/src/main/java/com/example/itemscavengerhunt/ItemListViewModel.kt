package com.example.itemscavengerhunt

import androidx.lifecycle.ViewModel

class ItemListViewModel : ViewModel() {

    private val itemRepository = ItemRepository.get()
    val itemListLiveData = itemRepository.getItems()

    fun addItem(item: Item){
        itemRepository.addItem(item)
    }



//    val items = listOf(
//        Item("Item #1", "Yellow Jeep", false),
//        Item("Item #2", "Bank Sign", false),
//        Item("Item #3", "McDonald's Restaurant", false),
//        Item("Item #4", "Walmart", false),
//        Item("Item #5", "Construction Sign", false),
//        Item("Item #6", "Police Car", false),
//        Item("Item #7", "American Flag", false),
//        Item("Item #8", "Church", false),
//        Item("Item #9", "Cell Phone Tower", false),
//        Item("Item #10", "Chick Fil A", false),
//        Item("Item #11", "Charter Bus", false),
//        Item("Item #12", "Mall", false),
//        Item("Item #13", "Interstate Sign", false),
//        Item("Item #14", "NAME", false),
//        Item("Item #15", "NAME", false),
//        Item("Item #16", "NAME", false),
//        Item("Item #17", "NAME", false),
//        Item("Item #18", "NAME", false),
//        Item("Item #19", "NAME", false),
//        Item("Item #20", "NAME", false)
//
//    )

}