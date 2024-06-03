package com.org.wfnr_2024.Activity

import android.content.Context
import android.graphics.Canvas
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class JustifiedTextView(context: Context, attrs: AttributeSet?) :
    AppCompatTextView(context, attrs) {

    private val textPaint: TextPaint = paint

    override fun onDraw(canvas: Canvas) {
        val text = text.toString()
        val width = width - paddingLeft - paddingRight.toFloat()

        // Create a StaticLayout with text, width, and alignment
        val layout = StaticLayout.Builder.obtain(
            text, 0, text.length, textPaint, width.toInt()
        ).setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(lineSpacingExtra, lineSpacingMultiplier)
            .setIncludePad(true)
            .setMaxLines(Int.MAX_VALUE)
            .build()

        // Calculate total height of the text
        var totalHeight = 0f
        val lineCount = layout.lineCount
        for (i in 0 until lineCount) {
            totalHeight += layout.getLineBottom(i) - layout.getLineTop(i)
        }

        // Draw the layout on the canvas, adjusting for vertical alignment
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        val textHeight = totalHeight + paddingTop + paddingBottom
        val verticalOffset = (height - textHeight) / 2f - paddingTop
        canvas.save()
        canvas.translate(paddingLeft.toFloat(), verticalOffset)
        layout.draw(canvas)
        canvas.restore()
    }
}
