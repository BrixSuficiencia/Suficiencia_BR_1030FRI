package com.ucb.suficiencia.menuapp

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction

class CustomDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Dialog Title")
                .setMessage("This is the message of the dialog")
                .setPositiveButton("First Menu") { dialog, _ ->
                    dialog.dismiss()

                    val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, FirstFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                .setNegativeButton("Exit") { _, _ ->
                    activity?.finish()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

