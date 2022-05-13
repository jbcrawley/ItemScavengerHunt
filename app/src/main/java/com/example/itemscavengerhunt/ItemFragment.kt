package com.example.itemscavengerhunt

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import java.util.*

private const val ARG_ITEM_ID = "item_id"

class ItemFragment : Fragment() {

    private lateinit var item: Item
    private lateinit var titleField: EditText
    private lateinit var itemField: EditText
    private lateinit var foundCheckBox: CheckBox
    private val itemDetailViewModel: ItemDetailViewModel by lazy {
        ViewModelProvider(this).get(ItemDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = Item()
        val itemId: UUID = arguments?.getSerializable(ARG_ITEM_ID) as UUID
        itemDetailViewModel.loadItem(itemId)
    }
        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item, container, false)

        titleField = view.findViewById(R.id.item_title) as EditText
        itemField = view.findViewById(R.id.item_details) as EditText
        foundCheckBox = view.findViewById(R.id.item_found) as CheckBox

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemDetailViewModel.itemLiveData.observe(
            viewLifecycleOwner,
            Observer{ item ->
                item?.let{
                    this.item = item
                    updateUI()
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                item.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }
        titleField.addTextChangedListener(titleWatcher)

        val itemWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                item.itemDescription = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        itemField.addTextChangedListener(itemWatcher)

        foundCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                item.isFound = isChecked
            }
        }
    }

    override fun onStop() {
        super.onStop()
        itemDetailViewModel.saveItem(item)
    }

    private fun updateUI() {
        titleField.setText(item.title)
        itemField.setText(item.itemDescription)
        foundCheckBox. apply {
            isChecked = item.isFound
            jumpDrawablesToCurrentState()
        }
    }

    companion object {

        fun newInstance(itemId: UUID): ItemFragment {
            val args = Bundle().apply {
                putSerializable(ARG_ITEM_ID, itemId)
            }
            return ItemFragment().apply {
                arguments = args
            }
        }
    }
}
