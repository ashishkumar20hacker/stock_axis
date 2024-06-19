package com.example.stockaxistask.UI

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.stockaxistask.DataSource.Data
import com.example.stockaxistask.R
import com.example.stockaxistask.ViewModel.MainViewModel
import com.example.stockaxistask.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewOrderModel: MainViewModel

    lateinit var dataList1: MutableList<Data>
    lateinit var dataList2: MutableList<Data>
    var savedAmt : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewOrderModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewOrderModel.getLM {
            it?.let { it1 ->
                dataList1 = it1
                setSpinner(binding.spinner1,it1) }
        }
        viewOrderModel.getEML {
            it?.let { it1 ->
                dataList2 = it1
                setSpinner(binding.spinner2,it1) }
        }

    }

    private fun setSpinner(spinner: Spinner, dataList: MutableList<Data>) {
        val ad = SpinnerAdapter(this, dataList)
        spinner.adapter = ad
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                calculateAndUpdateUI()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                calculateAndUpdateUI()
            }
        }
    }


    private fun calculateAndUpdateUI() {
        val selectedItem1 = if (binding.spinner1.selectedItemPosition > 0) {
            binding.spinnerCard1.strokeColor = resources.getColor(R.color.green)
            dataList1[binding.spinner1.selectedItemPosition - 1]
        } else {
            binding.spinnerCard1.strokeColor = resources.getColor(R.color.grey2)
            null
        }

        val selectedItem2 = if (binding.spinner2.selectedItemPosition > 0) {
            binding.spinnerCard2.strokeColor = resources.getColor(R.color.green)
            dataList2[binding.spinner2.selectedItemPosition - 1]
        } else {
            binding.spinnerCard2.strokeColor = resources.getColor(R.color.grey2)
            null
        }

        val totalAmount = calculateAmt(selectedItem1, selectedItem2)

        binding.totalAmtTv.text = "Rs. $totalAmount"
    }

    private fun calculateAmt(item1: Data?, item2: Data?): Long {
        var totalAmt = 0L

        if (item1 != null && item2 != null) {

            item1?.let {
                if (it.PTotaAmount.isEmpty()) {
                    totalAmt += it.PAmount.toLong()
                } else {
                    totalAmt += it.PTotaAmount.toLong()
                }
            }

            item2?.let {
                if (it.PTotaAmount.isEmpty()) {
                    totalAmt += it.PAmount.toLong()
                } else {
                    totalAmt += it.PTotaAmount.toLong()
                }
            }

            savedAmt = ((totalAmt/100) * 20)
            totalAmt = totalAmt - savedAmt

            binding.savedAmtTv.setText("You will save Rs. $savedAmt on this plan")
            binding.savedAmtTv.visibility = View.VISIBLE
        } else {
            item1?.let {
                if (it.PTotaAmount.isEmpty()) {
                    totalAmt += it.PAmount.toLong()
                } else {
                    totalAmt += it.PTotaAmount.toLong()
                }
            }

            item2?.let {
                if (it.PTotaAmount.isEmpty()) {
                    totalAmt += it.PAmount.toLong()
                } else {
                    totalAmt += it.PTotaAmount.toLong()
                }
            }

            savedAmt = 0

            binding.savedAmtTv.setText("You will save Rs. $savedAmt on this plan")
            binding.savedAmtTv.visibility = View.GONE
        }

        return totalAmt
    }


}