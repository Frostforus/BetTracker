package com.frostforus.betTracker.list_data.fragments


import android.app.Dialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.frostforus.betTracker.R
import com.frostforus.betTracker.list_data.data.BetItem

private lateinit var nameEditText: EditText
private lateinit var descriptionEditText: EditText
private lateinit var potEditText: EditText
private lateinit var categorySpinner: Spinner
private lateinit var statusSpinner: Spinner


private lateinit var picker: DatePicker

class NewBetItemDialogFragment : DialogFragment() {
    interface NewBetItemDialogListener {
        fun onBetItemCreated(newItem: BetItem)
    }

    private lateinit var listener: NewBetItemDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewBetItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewBetItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_bet)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { _, _ ->
                if (isValid()) {
                    listener.onBetItemCreated(getBetItem())
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun isValid() = nameEditText.text.isNotEmpty()

    private fun getBetItem() = BetItem(
        id = null,
        nameOfBetWith = nameEditText.text.toString(),
        description = descriptionEditText.text.toString(),
        pot = potEditText.text.toString(),
        category = BetItem.Category.getByOrdinal(categorySpinner.selectedItemPosition)
            ?: BetItem.Category.ITEM,
        status = BetItem.Status.getByOrdinal(statusSpinner.selectedItemPosition)
            ?: BetItem.Status.INPROGRESS,

        betEndYear = picker.year.toShort(),
        betEndMonth = picker.month.toShort(),
        betEndDay = picker.dayOfMonth.toShort(),

        betStartYear = Calendar.getInstance().get(Calendar.YEAR).toShort(),
        betStartMonth = Calendar.getInstance().get(Calendar.MONTH).toShort(),
        betStartDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toShort()
    )

    private fun getContentView(): View {
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.dialog_new_bet_item, null)


        //TODO: get logs working properly

        Log.v("new bet", "lets go")

        picker = contentView.findViewById(R.id.datePicker1)
        nameEditText = contentView.findViewById(R.id.BetItemNameEditText)
        descriptionEditText = contentView.findViewById(R.id.BetItemDescriptionEditText)
        potEditText = contentView.findViewById(R.id.BetItemPotEditText)
        categorySpinner = contentView.findViewById(R.id.CategorySpinner)
        categorySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )
        statusSpinner = contentView.findViewById(R.id.StatusSpinner)
        statusSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.status_items)
        )
        return contentView
    }


    companion object {
        const val TAG = "NewShoppingItemDialogFragment"
    }
}