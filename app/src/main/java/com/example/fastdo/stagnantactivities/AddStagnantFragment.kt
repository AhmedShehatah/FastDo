package com.example.fastdo.stagnantactivities

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.fastdo.databinding.FragmentAddStagnantBinding
import com.example.fastdo.firebase.FireStore
import com.example.fastdo.models.Stagnants
import com.example.fastdo.stagnantapdaters.SearchStagnantModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddStagnantFragment : Fragment() {
    private lateinit var binding: FragmentAddStagnantBinding
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStagnantBinding.inflate(inflater, container, false)

        binding.btnExpiryDatePicker.setOnClickListener {
            datePicker()
        }
        binding.btnSend.setOnClickListener {
            storeStagnant()
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddStagnantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun storeStagnant() {
        try {
            val stagnantName = binding.etStagnantName.text.toString()
            val stagnantType = binding.stagnantTypeSpinner.selectedItem.toString()
            val priceType = binding.priceType.selectedItem.toString()
            val unitType = binding.stagnantAmountSpinner.selectedItem.toString()
            val stagnantAmount: Int = binding.etAmount.text.toString().toInt()
            val unitPrice: Double = binding.etUnitPrice.text.toString().toDouble()
            val stagnantExpiryDate = binding.tvExpiryDateShow.text.toString()
            val discount: Int = binding.etSalesPercentage.text.toString().toInt()
            val contactInfo: String = binding.etContactInfo.text.toString()

            if (validateForm(
                    stagnantName,
                    stagnantAmount,
                    unitPrice,
                    discount,
                    contactInfo
                )
            ) {
                val stagnantInfoStore = Stagnants(
                    FireStore().getCurrentUserID(),
                    stagnantName,
                    stagnantType,
                    stagnantAmount,
                    unitPrice,
                    priceType,
                    stagnantExpiryDate,
                    discount,
                    contactInfo
                )
                val searchStagnantInfo = SearchStagnantModel(
                    FireStore().getCurrentUserID(),
                    stagnantName,
                    stagnantType,
                    stagnantAmount,
                    unitType,
                    unitPrice,
                    priceType,
                    stagnantExpiryDate,
                    discount,
                    contactInfo

                )
                FireStore().storeStagnant(stagnantInfoStore, requireContext())
                FireStore().searchStagnant(searchStagnantInfo)
                eraseData()
            }
        } catch (ex: Exception) {
            Toast.makeText(requireContext(), "من فضل ادخل كامل البيانات", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateForm(
        stagnantName: String,
        stagnantAmount: Int,
        unitPrice: Double,
        discount: Int,
        contactInfo: String
    ): Boolean {
        return when {
            TextUtils.isEmpty(stagnantName) -> {
                showErrorToast("من فضلك ادخل اسم االراكد")
                false
            }
            TextUtils.isEmpty(stagnantAmount.toString()) -> {
                showErrorToast("من فضلك ادخل كمية االراكد")
                false
            }
            TextUtils.isEmpty(unitPrice.toString()) -> {
                showErrorToast("من فضلك ادخل سعر الوحدة")
                false
            }
            TextUtils.isEmpty(discount.toString()) -> {
                showErrorToast("من فضلك ادخل نسبة الخصم")
                false
            }
            TextUtils.isEmpty(contactInfo) -> {
                showErrorToast("من فضلك ادخل بيانات التواصل")
                false
            }
            binding.tvExpiryDateShow.text == "dd/mm/yyyy" -> {
                showErrorToast("من فضلك ادخل تاريخ صلاحية المنتج")
                false
            }
            else -> true
        }

    }

    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun datePicker() {
        val myCalendar = Calendar.getInstance()
        val currentYear = myCalendar.get(Calendar.YEAR)
        val currentMonth = myCalendar.get(Calendar.MONTH)
        val currentDay = myCalendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                binding.tvExpiryDateShow.text = "$day/${month + 1}/$year"

            }, currentYear,
            currentMonth,
            currentDay
        ).show()
    }

    private fun eraseData() {
        binding.etAmount.setText("")
        binding.etContactInfo.setText("")
        binding.etSalesPercentage.setText("")
        binding.etUnitPrice.setText("")
        binding.etStagnantName.setText("")
        binding.tvExpiryDateShow.text = "dd/mm/yyyy"

    }

}