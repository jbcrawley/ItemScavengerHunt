package com.example.itemscavengerhunt

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity()
data class Item(@PrimaryKey val id:UUID = UUID.randomUUID(),
                var title: String = "",
                var itemDescription: String = "",
                var isFound: Boolean = false)


