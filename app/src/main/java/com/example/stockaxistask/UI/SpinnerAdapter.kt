package com.example.stockaxistask.UI

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.stockaxistask.DataSource.Data

class SpinnerAdapter(private val context: Context, private val dataList: MutableList<Data>) : BaseAdapter() {
    override fun getCount(): Int {
        return dataList.size + 1
    }

    override fun getItem(position: Int): Any {
        return dataList[position - 1]
    }

    override fun getItemId(position: Int): Long {
        return (position -1).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var textView = convertView as? TextView
        if (textView == null) {
            textView = TextView(context)
            textView.setPadding(16, 16, 16, 16)
        }
        val textToShow = if (position == 0) {
            "Select an item"  // Default text when spinner is at 0th position
        } else {
            val data = dataList[position - 1]
            if (data.PTotaAmount.isEmpty()) {
                "${data.PDescription} - Rs. ${data.PAmount}"
            } else {
                applyStrikethrough(data.PDescription, data.PAmount, data.PTotaAmount)
            }
        }

        textView.text = textToShow
        return textView
    }

    fun applyStrikethrough(
        description: String,
        totalAmount: String,
        amount: String
    ): SpannableString {
        // Construct the full string
        val fullString = "$description - Rs. $totalAmount Rs. $amount"

        // Find the index range of the totalAmount substring within the full string
        val startIndex = fullString.indexOf(totalAmount) - 4
        val endIndex = startIndex + totalAmount.length + 4

        // Create a SpannableString from the full string
        val spannableString = SpannableString(fullString)

        // Apply StrikethroughSpan to the totalAmount substring
        spannableString.setSpan(
            StrikethroughSpan(),
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannableString
    }
}