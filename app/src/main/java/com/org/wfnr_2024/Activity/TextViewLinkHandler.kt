package com.org.wfnr_2024.Activity

import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.widget.TextView

class TextViewLinkHandler : LinkMovementMethod() {
    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        try {
            return super.onTouchEvent(widget, buffer, event)
        } catch (e: Exception) {
            // Handle the exception here (if any)
        }
        return false
    }

     fun onClick(widget: TextView, buffer: Spannable, event: MotionEvent) {
        try {
            val action = event.action
            if (action == MotionEvent.ACTION_UP) {
                val x = event.x.toInt()
                val y = event.y.toInt()
                val layout = widget.layout
                val line = layout.getLineForVertical(y)
                val off = layout.getOffsetForHorizontal(line, x.toFloat())
                val link = buffer.getSpans(off, off, ClickableSpan::class.java)
                if (link.isNotEmpty()) {
                    link[0].onClick(widget)
                }
            }
        } catch (e: Exception) {
            // Handle the exception here (if any)
        }
    }
}
