package com.suzukiplan.emulator.nes.test

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

class CaptureVideoDialog : DialogFragment() {

    private lateinit var bitmap: Bitmap

    fun show(manager: androidx.fragment.app.FragmentManager, bitmap: Bitmap) {
        this.bitmap = bitmap
        isCancelable = true
        super.show(manager, "CaptureVideoDialog")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_capture_video, container, false)
        view.findViewById<ImageView>(R.id.capture_preview).setImageBitmap(bitmap)
        return view
    }
}
