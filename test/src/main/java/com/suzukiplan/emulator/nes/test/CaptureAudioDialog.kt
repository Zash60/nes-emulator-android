package com.suzukiplan.emulator.nes.test

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.suzukiplan.emulator.nes.core.NESAudioCaptureService
import com.suzukiplan.emulator.nes.core.NESView
import java.nio.ByteBuffer

class CaptureAudioDialog : DialogFragment() {

    private var nesView: NESView? = null
    private var preview: SurfaceView? = null
    private var thread: Thread? = null
    private var alive = false

    fun show(manager: androidx.fragment.app.FragmentManager, nesView: NESView?) {
        this.nesView = nesView
        isCancelable = true
        super.show(manager, "CaptureAudioDialog")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_capture_audio, container, false)
        preview = view.findViewById(R.id.capture_preview)

        preview?.holder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                // no-op
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                if (alive) return
                alive = true
                thread = Thread {
                    drawWaveform(holder)
                }
                thread?.start()
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                alive = false
                thread?.join()
                thread = null
            }
        })

        return view
    }

    private fun drawWaveform(holder: SurfaceHolder) {
        val capture = NESAudioCaptureService(nesView, 100)
        val input = capture.open() ?: return
        val bufferSize = 8192
        val raw = ByteArray(bufferSize * 2)
        var offset = 0

        while (alive && offset < raw.size) {
            val read = input.read(raw, offset, raw.size - offset)
            if (read <= 0) break
            offset += read
        }
        capture.close()

        if (!alive) return

        val canvas = holder.lockCanvas() ?: return
        canvas.drawColor(android.graphics.Color.BLACK)

        val paint = Paint().apply {
            color = Color.GREEN
            strokeWidth = 3f
        }

        val shorts = ByteBuffer.wrap(raw).asShortBuffer()
        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()
        val centerY = height / 2f
        val scaleY = height / 2f / 32768f

        var x = 0f
        var prevY = centerY

        while (shorts.hasRemaining() && alive) {
            val sample = shorts.get().toFloat()
            val y = centerY + sample * scaleY
            canvas.drawLine(x - 1, prevY, x, y, paint)
            prevY = y
            x += width / shorts.remaining().coerceAtLeast(1)
        }

        holder.unlockCanvasAndPost(canvas)
    }

    override fun onDestroyView() {
        nesView?.setOnCaptureAudioListener(null)
        super.onDestroyView()
    }
}
