package com.exemplo.novatv

data class AppInfo(
    val pacote: String,
    val nome: String,
    val cor: Int,
    val logoRes: Int? = null // null = usar banner do app
)

object Apps {
    val lista = listOf(
        AppInfo("com.netflix.ninja", "Netflix", Cores.NETFLIX, R.drawable.logo_netflix),
        AppInfo("com.google.android.youtube.tv", "YouTube", Cores.YOUTUBE, R.drawable.logo_youtube),
        AppInfo("org.smarttube.beta", "SmartTube", Cores.SMARTTUBE),
        AppInfo("com.global.veloplaytv", "VeloPlay TV", Cores.VELOPLAY),
        AppInfo("com.android.everycine.tv", "EveryCine TV", Cores.EVERYCCINE),
        AppInfo("io.freemytv", "FreeMyTV", Cores.FREEMYTV),
        AppInfo("com.kapron.ap.aicamview.tv", "AI Cam View", Cores.AICAM),
        AppInfo("com.yablio.sendfilestotv", "Send Files to TV", Cores.SENDFILES),
        AppInfo("com.alphainventor.filemanager", "File Manager", Cores.FILEMANAGER),
        AppInfo("com.esaba.downloader", "Esaba Downloader", Cores.ESABA),
        AppInfo("com.integration.unitvsiptv", "UnitvSIPTV", Cores.UNITV),
        AppInfo("br.com.saimo.tv", "Saimo TV", Cores.SAIMO),
        AppInfo("com.google.android.youtube.tvmusic", "YouTube Music", Cores.YTMUSIC),
        AppInfo("__TODOS__", "Todos os Apps", Cores.TODOS)
    )
}
