package com.exemplo.novatv

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class FundoView(context: Context) : View(context) {
    private val paint = Paint().apply { color = Cores.FUNDO }
    // No Prompt 08, aqui entra o papel de parede
    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.rgb(5, 7, 20))
    }
}
