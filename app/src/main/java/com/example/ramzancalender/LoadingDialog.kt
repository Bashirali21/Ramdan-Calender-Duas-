package com.example.ramzancalender

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ProgressBar

class LoadingDialog(context: Context) : Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        setCancelable(false)
    }

    override fun show() {
        super.show()
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}