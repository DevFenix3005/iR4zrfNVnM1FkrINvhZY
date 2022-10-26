package com.rebirth.unitfy.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class NewUnitOrClassDialog constructor(
    private val title: String,
    private val res: Int
) : DialogFragment() {

    lateinit var listener: AddUnitOrClassListener

    private fun myContentView(
        inflater: LayoutInflater = requireActivity().layoutInflater
    ): View {
        return inflater.inflate(res, null)
    }

    interface AddUnitOrClassListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(myContentView())
            builder.setTitle(title)
                .setPositiveButton("OK") { _, _ ->
                    // Send the positive button event back to the host activity
                    listener.onDialogPositiveClick(this)
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}