package com.exemplo.novatv

import android.graphics.*
import android.graphics.Bitmap.Config.ALPHA_8

object Vidro {
    // Desenha a borda de vidro no canvas
    fun desenhar(
        canvas: Canvas,
        rect: RectF,
        raio: Float,
        corBrilho: Int,
        emFoco: Boolean,
        dp: Float
    ) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = if (emFoco) 1.8f * dp else 1.2f * dp
            shader = LinearGradient(
                rect.left, rect.top, rect.right, rect.bottom,
                intArrayOf(
                    Color.argb(if (emFoco) 255 else 190, 255, 255, 255),
                    Color.argb(if (emFoco) 70 else 26, 255, 255, 255),
                    Color.argb(if (emFoco) 220 else 130, 255, 255, 255)
                ),
                floatArrayOf(0f, 0.5f, 1f),
                Shader.TileMode.CLAMP
            )
        }
        canvas.drawRoundRect(rect, raio, raio, paint)

        // Segunda linha interna
        val paintInterna = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 1.6f * dp
            color = Color.argb(22, 255, 255, 255)
        }
        val rectInterno = RectF(rect)
        rectInterno.inset(1.6f * dp, 1.6f * dp)
        canvas.drawRoundRect(rectInterno, raio, raio, paintInterna)

        // Reflexo no alto do card
        val paintReflexo = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = LinearGradient(
                rect.left, rect.top, rect.left, rect.top + rect.height() * 0.38f,
                Color.argb(26, 255, 255, 255), Color.TRANSPARENT,
                Shader.TileMode.CLAMP
            )
        }
        canvas.drawRoundRect(
            RectF(rect.left, rect.top, rect.right, rect.top + rect.height() * 0.38f),
            raio, raio, paintReflexo
        )

        // Halo colorido só no foco, com BlurMaskFilter
        if (emFoco) {
            val bmp = Bitmap.createBitmap(
                rect.width().toInt() + 12,
                rect.height().toInt() + 12,
                ALPHA_8
            )
            val c = Canvas(bmp)
            val p = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = 2f * dp
                color = Color.WHITE
                maskFilter = BlurMaskFilter(4f * dp, BlurMaskFilter.Blur.NORMAL)
            }
            c.drawRoundRect(
                RectF(6f, 6f, rect.width() - 6f, rect.height() - 6f),
                raio, raio, p
            )
            val paintHalo = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = corBrilho
                alpha = 200
            }
            canvas.drawBitmap(bmp, rect.left - 6f, rect.top - 6f, paintHalo)
            bmp.recycle()
        }
    }
}
