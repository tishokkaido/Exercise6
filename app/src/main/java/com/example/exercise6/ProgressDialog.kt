package com.example.exercise6

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ProgressDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_progress, null)
        builder.setView(view)
        return builder.create()
    }

    companion object {
        fun newInstance(): ProgressDialog {
            return ProgressDialog()
        }
    }
}