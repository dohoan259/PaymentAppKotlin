package me.hp.paymentapp.ui.dialog


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

import me.hp.paymentapp.R
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class PaySuccessFragment : DialogFragment() {

    private lateinit var transCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        transCode = PaySuccessFragmentArgs.fromBundle(requireArguments()).transCode
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mBuilder = AlertDialog.Builder(activity)
        val view = View.inflate(context, R.layout.dialog_pay_success, null)

        val okBtn = view.findViewById<Button>(R.id.ok_btn)
        val transCodeTv = view.findViewById<TextView>(R.id.transaction_code_tv)

        transCodeTv.text = String.format(Locale.getDefault(), getString(R.string.transaction_code), transCode)
        okBtn.setOnClickListener {
            requireActivity().finish()
        }

        mBuilder.setView(view)
        val dialog = mBuilder.create()
        dialog.setCanceledOnTouchOutside(false)

        val window = dialog.window
        window?.apply {
            setBackgroundDrawableResource(R.drawable.bg_dialog)
            val wlp = window.attributes
            wlp.gravity = Gravity.CENTER
            attributes = wlp
        }

        return dialog
    }
}
