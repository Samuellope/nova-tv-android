package com.exemplo.novatv

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.view.View
import kotlin.math.min

class CartaoView(context: Context, val app: AppInfo) : View(context) {

    private val paintFundo = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintVeu = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintLogo = Paint(Paint.ANTI_ALIAS_FLAG)
    private var logoBitmap: Bitmap? = null
    private var emFoco = false
    private val dp = resources.displayMetrics.density

    init {
        isFocusable = true
        isFocusableInTouchMode = false
        setLayerType(LAYER_TYPE_HARDWARE, null) // guia: acelera o foco
        // Carrega logo oficial ou banner do app
        if (app.logoRes != null) {
            logoBitmap = BitmapFactory.decodeResource(resources, app.logoRes)
        } else {
            try {
                val drawable = context.packageManager.getApplicationBanner(app.pacote)
                if (drawable is BitmapDrawable) {
                    logoBitmap = drawable.bitmap
                }
            } catch (_: Exception) {}
        }
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        emFoco = gainFocus
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()
        val raio = 16f * dp
        val folga = 6f * dp // folga para o halo sumir antes da beirada
        val rect = RectF(folga, folga, w - folga, h - folga)

        // Fundo do card: véu escuro (alfa 28)
        paintFundo.color = Cores.FUNDO_CARD
        canvas.drawRoundRect(rect, raio, raio, paintFundo)

        // Reflexo colorido leve na base
        val paintBase = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = LinearGradient(
                rect.left, rect.bottom - rect.height() * 0.25f,
                rect.left, rect.bottom,
                Color.TRANSPARENT, Color.argb(40, Color.red(app.cor), Color.green(app.cor), Color.blue(app.cor)),
                Shader.TileMode.CLAMP
            )
        }
        canvas.drawRoundRect(rect, raio, raio, paintBase)

        // Logo centralizada (máx 50% largura, 40% altura no grande; 68%/50% no pequeno)
        logoBitmap?.let { bmp ->
            val maxW = rect.width() * if (isGrande()) 0.50f else 0.68f
            val maxH = rect.height() * if (isGrande()) 0.40f else 0.50f
            val escala = min(maxW / bmp.width, maxH / bmp.height)
            val lw = bmp.width * escala
            val lh = bmp.height * escala
            val left = rect.centerX() - lw / 2
            val top = rect.centerY() - lh / 2
            canvas.drawBitmap(bmp, null, RectF(left, top, left + lw, top + lh), paintLogo)
        }

        // Borda de vidro (por último, por cima)
        Vidro.desenhar(canvas, rect, raio, app.cor, emFoco, dp)
    }

    private fun isGrande(): Boolean = width > 400 * dp
}
