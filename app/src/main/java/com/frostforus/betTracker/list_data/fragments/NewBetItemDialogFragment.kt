package com.frostforus.betTracker.list_data.fragments


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.frostforus.betTracker.R
import hu.bme.aut.shoppinglist.data.BetItem

//TODO: Rename all this shit
private lateinit var nameEditText: EditText
private lateinit var descriptionEditText: EditText
private lateinit var estimatedPriceEditText: EditText
private lateinit var categorySpinner: Spinner
private lateinit var alreadyPurchasedCheckBox: CheckBox
private lateinit var picker: DatePicker
class NewBetItemDialogFragment : DialogFragment() {
    interface NewBetItemDialogListener {
        fun onBetItemCreated(newItem: BetItem)
    }


    var dateYear: Short = 0
    var dateMonth: Short = 0
    var dateDay: Short = 0

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
            .setPositiveButton(R.string.ok) { dialogInterface, i ->
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
        pot = estimatedPriceEditText.text.toString(),
        category = BetItem.Category.getByOrdinal(categorySpinner.selectedItemPosition)
            ?: BetItem.Category.ITEM,
        betOver = alreadyPurchasedCheckBox.isChecked,
        betEndYear = picker.year.toShort(),
        betEndMonth = picker.month.toShort(),
        betEndDay = picker.dayOfMonth.toShort()
    )

    private fun getContentView(): View {
        val contentView =
            LayoutInflater.from(context).inflate(R.layout.dialog_new_bet_item, null)


        //TODO: get logs working properly

        Log.v("new bet", "lets go")

        picker = contentView.findViewById(R.id.datePicker1)
        nameEditText = contentView.findViewById(R.id.ShoppingItemNameEditText)
        descriptionEditText = contentView.findViewById(R.id.ShoppingItemDescriptionEditText)
        estimatedPriceEditText = contentView.findViewById(R.id.ShoppingItemEstimatedPriceEditText)
        categorySpinner = contentView.findViewById(R.id.ShoppingItemCategorySpinner)
        categorySpinner.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.category_items)
            )
        )
        alreadyPurchasedCheckBox = contentView.findViewById(R.id.ShoppingItemIsPurchasedCheckBox)
        return contentView
    }


    companion object {
        const val TAG = "NewShoppingItemDialogFragment"
    }
}