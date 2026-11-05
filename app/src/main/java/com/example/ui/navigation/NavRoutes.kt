package com.example.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Beranda")
    object Pedoman : Screen("pedoman", "Pedoman")
    object Pelanggaran : Screen("pelanggaran", "Pelanggaran")
    object TanyaTatib : Screen("tanya_tatib", "Tanya Tatib")
    object Belajar : Screen("belajar", "Belajar")
    object Quiz : Screen("quiz", "Kuis Tatib")
    object Standards : Screen("standards", "Standar Visual")
    object Search : Screen("search", "Pencarian Cerdas")
    object Bookmarks : Screen("bookmarks", "Simpanan Saya")
    object EducationalResources : Screen("educational_resources", "Edukasi & Nilai")
    object About : Screen("about", "Tentang Aplikasi")
}
