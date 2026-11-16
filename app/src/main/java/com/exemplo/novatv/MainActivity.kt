package com.exemplo.novatv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.format.DateFormat
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.*
import java.util.Date

class MainActivity : Activity() {

    private lateinit var containerCards: LinearLayout
    private lateinit var textSaudacao: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null) // guia: evita pintar duas vezes
        setContentView(R.layout.activity_main)

        containerCards = findViewById(R.id.container_cards)
        textSaudacao = findViewById(R.id.text_saudacao)

        atualizarSaudacao()
        montarCards()
        iniciarRelogio()
    }

    private fun atualizarSaudacao() {
        val nome = Prefs.getNome(this)
        textSaudacao.text = "Olá, $nome"
    }

    private fun iniciarRelogio() {
        val hora = findViewById<TextView>(R.id.text_hora)
        val data = findViewById<TextView>(R.id.text_data)
        val handler = android.os.Handler(mainLooper)
        val runnable = object : Runnable {
            override fun run() {
                val agora = Date()
                hora.text = DateFormat.format("HH:mm", agora)
                data.text = DateFormat.format("EEE, dd 'de' MMM", agora)
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    private fun montarCards() {
        containerCards.removeAllViews()
        val lista = Apps.lista
        var i = 0
        // Linhas 1-3: 2 cards grandes
        for (linha in 0 until 3) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 1f
                )
            }
            for (col in 0 until 2) {
                if (i < lista.size) {
                    val card = CartaoView(this, lista[i])
                    card.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                    card.setOnClickListener { abrirApp(lista[i]) }
                    row.addView(card)
                    i++
                }
            }
            containerCards.addView(row)
        }
        // Linhas 4-5: 4 cards pequenos
        for (linha in 0 until 2) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 0.8f
                )
            }
            for (col in 0 until 4) {
                if (i < lista.size) {
                    val card = CartaoView(this, lista[i])
                    card.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                    card.setOnClickListener { abrirApp(lista[i]) }
                    row.addView(card)
                    i++
                }
            }
            containerCards.addView(row)
        }
    }

    private fun abrirApp(app: AppInfo) {
        if (app.pacote == "__TODOS__") {
            // Abre a lista de apps do Android TV
            startActivity(Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS))
            return
        }
        val intent = packageManager.getLaunchIntentForPackage(app.pacote)
        if (intent != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "App não encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // No início, Back não faz nada quando é a tela inicial
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
