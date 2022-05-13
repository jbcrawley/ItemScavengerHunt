package com.example.itemscavengerhunt

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "ItemListFragment"

class ItemListFragment : Fragment() {

    interface Callbacks{
        fun onItemSelected(itemId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var itemRecyclerView: RecyclerView
    private var adapter: ItemAdapter? = ItemAdapter(emptyList())

    private val itemListViewModel: ItemListViewModel by lazy {
        ViewModelProvider(this).get(ItemListViewModel::class.java)
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        itemRecyclerView =
            view.findViewById(R.id.item_recycler_view) as RecyclerView
        itemRecyclerView.layoutManager = LinearLayoutManager(context)
        itemRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        itemListViewModel.itemListLiveData.observe(
            viewLifecycleOwner,
            Observer { items ->
                items?.let {
                    Log.i(TAG, "Got items ${items.size}")
                    updateUI(items)
                }
            })
    }

    override fun onDetach(){
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_item_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.new_item -> {
                val thing = Item()
                itemListViewModel.addItem(thing)
                callbacks?.onItemSelected(thing.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(items: List<Item>){
        adapter = ItemAdapter(items)
        itemRecyclerView.adapter = adapter
    }

    private inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var item: Item

        private val titleTextView: TextView = itemView.findViewById(R.id.item_title)
        private val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
        private val foundImageView: ImageView = itemView.findViewById(R.id.item_found)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Item) {
            this.item = item
            titleTextView.text = this.item.title
            itemNameTextView.text = this.item.itemDescription
            foundImageView.visibility = if (item.isFound) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View) {
            callbacks?.onItemSelected(item.id)
        }
    }

    private inner class ItemAdapter(var items: List<Item>) : RecyclerView.Adapter<ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : ItemHolder {
            val view = layoutInflater.inflate(R.layout.list_item, parent, false)
            return ItemHolder(view)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val item = items[position]
            holder.bind(item)
        }

        override fun getItemCount() = items.size
    }

    companion object {
        fun newInstance(): ItemListFragment {
            return ItemListFragment()
        }
    }
}
