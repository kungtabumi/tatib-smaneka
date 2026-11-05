package com.example.data.local

import com.example.data.model.HandlingProcedureEntity
import com.example.data.model.LearningTopicEntity
import com.example.data.model.QuizQuestionEntity
import com.example.data.model.RegulationEntity
import com.example.data.model.ViolationEntity
import com.example.data.model.VisualStandardEntity

object TatibDatabaseSeeder {

    suspend fun seed(dao: TatibDao) {
        dao.insertRegulations(regulations)
        dao.insertViolations(violations)
        dao.insertProcedures(handlingProcedures)
        dao.insertStandards(visualStandards)
        dao.insertLearningTopics(learningTopics)
        dao.insertQuizQuestions(quizQuestions)
    }

    val regulations = listOf(
        // BAB 1. DASAR HUKUM
        RegulationEntity(
            id = "REG_1_1",
            chapterNumber = "BAB 1",
            chapterTitle = "DASAR HUKUM",
            sectionNumber = "1.1",
            title = "UUD 1945 Pasal 28B (2)",
            fullContent = "Pasal 28B (2) Undang-Undang Dasar 1945 menyebutkan bahwa “Setiap anak berhak atas kelangsungan hidup, tumbuh, dan berkembang serta berhak atas perlindungan dari kekerasan dan diskriminasi.”",
            summaryContent = "Hak setiap anak atas perlindungan dari kekerasan dan diskriminasi serta kelangsungan hidup dan tumbuh kembang.",
            category = "Dasar Hukum",
            pageNumber = 1,
            keywords = "uud 1945, pasal 28b, hak anak, perlindungan anak, kekerasan, diskriminasi"
        ),
        RegulationEntity(
            id = "REG_1_2",
            chapterNumber = "BAB 1",
            chapterTitle = "DASAR HUKUM",
            sectionNumber = "1.2",
            title = "UU No. 20 Tahun 2003 (Sisdiknas)",
            fullContent = "Undang-undang No. 20 Tahun 2003 tentang Sistem Pendidikan Nasional;",
            summaryContent = "Landasan penyelenggaraan sistem pendidikan nasional.",
            category = "Dasar Hukum",
            pageNumber = 1,
            keywords = "uu 20 2003, sisdiknas, sistem pendidikan nasional"
        ),
        RegulationEntity(
            id = "REG_1_3",
            chapterNumber = "BAB 1",
            chapterTitle = "DASAR HUKUM",
            sectionNumber = "1.3",
            title = "PP No. 57 Th 2021 & PP No. 4 Th 2022 (SNP)",
            fullContent = "Peraturan Pemerintah No. 57 Tahun 2021. PP No. 4 Tahun 2022 tentang Standar Nasional Pendidikan.",
            summaryContent = "Standar nasional pengelolaan dan pembinaan pendidikan.",
            category = "Dasar Hukum",
            pageNumber = 1,
            keywords = "pp 57 2021, pp 4 2022, standar nasional pendidikan, snp"
        ),
        RegulationEntity(
            id = "REG_1_4",
            chapterNumber = "BAB 1",
            chapterTitle = "DASAR HUKUM",
            sectionNumber = "1.4",
            title = "Permendikbudristek No. 50 Tahun 2022 (Seragam)",
            fullContent = "Permendikbudristek No. 50 Tahun 2022 tentang Pakaian Seragam Sekolah Bagi Peserta Didik Jenjang Pendidikan Dasar dan Pendidikan Menengah.",
            summaryContent = "Regulasi resmi pakaian seragam sekolah bagi peserta didik jenjang pendidikan dasar dan menengah.",
            category = "Dasar Hukum",
            pageNumber = 1,
            keywords = "permendikbudristek 50 2022, seragam sekolah, pakaian seragam"
        ),
        RegulationEntity(
            id = "REG_1_5",
            chapterNumber = "BAB 1",
            chapterTitle = "DASAR HUKUM",
            sectionNumber = "1.5",
            title = "UU No. 24 Tahun 2009 (Lambang Negara)",
            fullContent = "Undang-undang No. 24 Tahun 2009 tentang Bendera, Bahasa, dan lambang Negara.",
            summaryContent = "Ketentuan kehormatan bendera merah putih, bahasa persatuan, dan lambang negara.",
            category = "Dasar Hukum",
            pageNumber = 1,
            keywords = "uu 24 2009, bendera, bahasa, lambang negara"
        ),
        RegulationEntity(
            id = "REG_1_6",
            chapterNumber = "BAB 1",
            chapterTitle = "DASAR HUKUM",
            sectionNumber = "1.6",
            title = "UU No. 35 Tahun 2014 (Perlindungan Anak)",
            fullContent = "Undang-undang No. 35 Tahun 2014 Undang-Undang Perlindungan Anak.",
            summaryContent = "Payung hukum perlindungan hak anak di satuan pendidikan.",
            category = "Dasar Hukum",
            pageNumber = 1,
            keywords = "uu 35 2014, perlindungan anak"
        ),

        // BAB 2. HAL MASUK SEKOLAH
        RegulationEntity(
            id = "REG_2_1",
            chapterNumber = "BAB 2",
            chapterTitle = "HAL MASUK SEKOLAH",
            sectionNumber = "2.1",
            title = "Bel Masuk dan Kehadiran 15 Menit Sebelum Bel",
            fullContent = "Bel masuk dibunyikan pukul 06.45 dan murid hadir di sekolah 15 menit sebelum bel berbunyi.",
            summaryContent = "Bel masuk berbunyi pukul 06.45. Murid wajib tiba di sekolah 15 menit sebelumnya (06.30 WIB).",
            category = "Masuk Sekolah",
            pageNumber = 1,
            keywords = "bel masuk, 06.45, 15 menit, kehadiran, waktu datang"
        ),
        RegulationEntity(
            id = "REG_2_2",
            chapterNumber = "BAB 2",
            chapterTitle = "HAL MASUK SEKOLAH",
            sectionNumber = "2.2",
            title = "Doa Bersama Sebelum Pembelajaran",
            fullContent = "Sebelum memulai pembelajaran Murid berdoa bersama;",
            summaryContent = "Seluruh murid mengawali kegiatan belajar dengan berdoa bersama di kelas.",
            category = "Masuk Sekolah",
            pageNumber = 1,
            keywords = "doa bersama, mulai pelajaran, kbm"
        ),
        RegulationEntity(
            id = "REG_2_3",
            chapterNumber = "BAB 2",
            chapterTitle = "HAL MASUK SEKOLAH",
            sectionNumber = "2.3",
            title = "Jadwal Jam Belajar Sekolah",
            fullContent = "Jam belajar dimulai:\n- Senin : Pukul 06.45 – 15.05\n- Selasa s.d Kamis : Pukul 06.45 – 15.15\n- Jum'at : Pukul 07.30 – 14.40",
            summaryContent = "Jadwal KBM: Senin (06.45-15.05), Selasa-Kamis (06.45-15.15), Jum'at (07.30-14.40).",
            category = "Masuk Sekolah",
            pageNumber = 1,
            keywords = "jam belajar, jadwal sekolah, senin, selasa, rabu, kamis, jumat, jam pulang"
        ),
        RegulationEntity(
            id = "REG_2_4",
            chapterNumber = "BAB 2",
            chapterTitle = "HAL MASUK SEKOLAH",
            sectionNumber = "2.4",
            title = "Kriteria Keterlambatan Siswa",
            fullContent = "Murid dinyatakan terlambat jika murid datang ke sekolah setelah bel masuk dibunyikan.",
            summaryContent = "Murid dinyatakan terlambat jika tiba setelah bel berbunyi (setelah 06.45 pada Senin-Kamis atau setelah 07.30 pada Jumat).",
            category = "Masuk Sekolah",
            pageNumber = 1,
            keywords = "terlambat, kriteria terlambat, bel berbunyi"
        ),
        RegulationEntity(
            id = "REG_2_5",
            chapterNumber = "BAB 2",
            chapterTitle = "HAL MASUK SEKOLAH",
            sectionNumber = "2.5",
            title = "Kewajiban Lapor Piket Tatib Bagi yang Terlambat",
            fullContent = "Murid yang datang terlambat wajib lapor pada petugas piket tatib, dengan menerima pembinaan dari tim piket tatib.",
            summaryContent = "Siswa terlambat wajib melapor ke piket tatib dan menerima pembinaan langsung.",
            category = "Masuk Sekolah",
            pageNumber = 1,
            keywords = "lapor piket, petugas piket tatib, pembinaan terlambat"
        ),
        RegulationEntity(
            id = "REG_2_6",
            chapterNumber = "BAB 2",
            chapterTitle = "HAL MASUK SEKOLAH",
            sectionNumber = "2.6",
            title = "Izin Ketidakhadiran Tertulis dari Orang Tua/Wali",
            fullContent = "Murid yang tidak masuk sekolah karena sakit atau keperluan penting lain wajib memberi informasi tertulis dari orang tua/wali.",
            summaryContent = "Wajib menyertakan surat keterangan tertulis dari orang tua/wali saat berhalangan hadir karena sakit atau keperluan penting.",
            category = "Masuk Sekolah",
            pageNumber = 1,
            keywords = "izin tidak masuk, surat sakit, izin tertulis, orang tua, wali"
        ),
        RegulationEntity(
            id = "REG_2_7",
            chapterNumber = "BAB 2",
            chapterTitle = "HAL MASUK SEKOLAH",
            sectionNumber = "2.7",
            title = "Ketentuan Meninggalkan Sekolah Sebelum KBM Berakhir",
            fullContent = "Murid tidak boleh meninggalkan sekolah sebelum KBM berakhir kecuali mendapat izin dari guru di kelas, guru piket, wakil kepala sekolah dan dijemput oleh orang tua.",
            summaryContent = "Izin keluar sekolah sebelum KBM selesai memerlukan persetujuan guru kelas, guru piket, waka, serta wajib dijemput orang tua.",
            category = "Masuk Sekolah",
            pageNumber = 1,
            keywords = "izin pulang, keluar sekolah sebelum kbm, izin guru piket, dijemput orang tua"
        ),
        RegulationEntity(
            id = "REG_2_8",
            chapterNumber = "BAB 2",
            chapterTitle = "HAL MASUK SEKOLAH",
            sectionNumber = "2.8",
            title = "Doa Penutup Pembelajaran",
            fullContent = "Kegiatan pembelajaran diakhiri dengan doa.",
            summaryContent = "Setiap akhir kegiatan KBM ditutup dengan doa bersama.",
            category = "Masuk Sekolah",
            pageNumber = 1,
            keywords = "doa penutup, akhir pembelajaran"
        ),
        RegulationEntity(
            id = "REG_2_9",
            chapterNumber = "BAB 2",
            chapterTitle = "HAL MASUK SEKOLAH",
            sectionNumber = "2.9",
            title = "Batas Waktu Berada di Lingkungan Sekolah (17.00 WIB)",
            fullContent = "Murid berada di lingkungan sekolah maksimal pukul 17.00 WIB (kecuali jika ada event dan ST maka harus didampingi pembina).",
            summaryContent = "Maksimal berada di sekolah hingga pukul 17.00 WIB. Jika ada event/kegiatan khusus (ST), wajib didampingi pembina.",
            category = "Masuk Sekolah",
            pageNumber = 1,
            keywords = "batas waktu di sekolah, 17.00 wib, event sekolah, st, didampingi pembina"
        ),

        // BAB 3. KEWAJIBAN MURID
        RegulationEntity(
            id = "REG_3_1",
            chapterNumber = "BAB 3",
            chapterTitle = "KEWAJIBAN MURID",
            sectionNumber = "3.1",
            title = "Hormat & Taat kepada Pimpinan dan Staf Sekolah",
            fullContent = "Murid wajib menghormati dan taat kepada Kepala Sekolah, guru, staf TU dan karyawan sekolah.",
            summaryContent = "Menghormati dan mematuhi seluruh jajaran Kepala Sekolah, dewan guru, staf TU, dan karyawan.",
            category = "Kewajiban",
            pageNumber = 2,
            keywords = "hormat, taat, kepala sekolah, guru, staf tu, karyawan"
        ),
        RegulationEntity(
            id = "REG_3_2",
            chapterNumber = "BAB 3",
            chapterTitle = "KEWAJIBAN MURID",
            sectionNumber = "3.2",
            title = "Tanggung Jawab Kebersihan, Keindahan, Keamanan & KBM",
            fullContent = "Murid ikut bertanggung jawab atas terselenggaranya kebersihan, keindahan, kelestarian lingkungan dan keamanan, serta kelancaran jalannya pelajaran dikelas.",
            summaryContent = "Ikut aktif menjaga 7K (kebersihan, keindahan, kelestarian lingkungan, keamanan) dan kelancaran KBM di kelas.",
            category = "Kewajiban",
            pageNumber = 2,
            keywords = "kebersihan, keindahan, kelestarian, keamanan, kelancaran pelajaran"
        ),
        RegulationEntity(
            id = "REG_3_3",
            chapterNumber = "BAB 3",
            chapterTitle = "KEWAJIBAN MURID",
            sectionNumber = "3.3",
            title = "Memelihara Rasa Kekeluargaan",
            fullContent = "Murid wajib menumbuhkan dan memelihara rasa kekeluargaan sesama warga sekolah.",
            summaryContent = "Menjaga kerukunan, solidaritas, dan persaudaraan antarwarga sekolah.",
            category = "Kewajiban",
            pageNumber = 2,
            keywords = "kekeluargaan, rasa persaudaraan, sesama warga sekolah"
        ),
        RegulationEntity(
            id = "REG_3_4_A",
            chapterNumber = "BAB 3",
            chapterTitle = "KEWAJIBAN MURID",
            sectionNumber = "3.4.A",
            title = "Ketentuan Seragam dan Jadwal Pemakaian",
            fullContent = """Murid memakai seragam dan atribut yang telah ditentukan:
- Pakaian seragam nasional (putih abu-abu) adalah pakaian yang dikenakan pada hari belajar oleh murid di sekolah, yang jenis, model, dan warnanya sama berlaku secara nasional.
- Pakaian seragam khas sekolah adalah pakaian seragam batik yang bercirikan karakteristik sekolah dikenakan oleh murid pada hari Kamis, dalam rangka meningkatkan kebanggaan murid terhadap sekolahnya.
- Atribut adalah kelengkapan pakaian seragam nasional yang menunjukkan identitas masing-masing sekolah terdiri dari badge organisasi kesiswaan, badge merah putih, badge nama murid, badge nama sekolah dan nama kabupaten/kota.

Ketentuan Penggunaan seragam di sekolah:
a. Hari Senin: Baju putih lengan panjang (putra & putri), celana/rok putih lengkap dengan atribut (topi upacara & dasi sekolah dengan standar panjang dasi di bawah pusar).
b. Hari Selasa: Baju putih lengan panjang (putra & putri wajib lengan panjang), celana/rok abu-abu lengkap dengan atribut.
c. Hari Rabu: Baju putih lengan pendek (untuk laki-laki), baju putih lengan panjang (untuk perempuan), celana/rok abu-abu lengkap dengan atribut.
d. Hari Kamis: Baju Batik Sekolah lengan pendek (untuk laki-laki), baju batik lengan panjang (untuk perempuan), celana/rok putih.
e. Hari Jumat: Baju atasan pramuka lengan pendek (untuk laki-laki), baju pramuka lengan panjang (untuk perempuan) serta atribut pramuka lengkap.

*Ketentuan Khusus Siswi (Perempuan): Seluruh seragam atasan (Senin s.d. Jumat) WAJIB berlengan panjang dan rok panjang sampai mata kaki.*
*Standar Pemakaian Dasi: Ujung dasi wajib terpasang rapi dengan standar panjang dasi di bawah pusar.*""",
            summaryContent = "Jadwal Seragam:\n• Senin: Putih-Putih Lengan Panjang (Putra & Putri) + Dasi di Bawah Pusar & Atribut\n• Selasa: Putih Lengan Panjang (Putra & Putri Wajib Lengan Panjang), Abu-abu + Atribut\n• Rabu: Putih Lengan Pendek (Putra) / Lengan Panjang (Putri), Abu-abu + Atribut\n• Kamis: Batik SMANEKA Lengan Pendek (Putra) / Lengan Panjang (Putri), Putih\n• Jumat: Pramuka Lengan Pendek (Putra) / Lengan Panjang (Putri) + Atribut Lengkap.\n• Standar Dasi: Panjang dasi di bawah pusar.\n• Ketentuan Siswi: Seluruh seragam atasan wajib lengan panjang.",
            category = "Seragam",
            pageNumber = 2,
            keywords = "seragam, putih abu-abu, batik sekolah, pramuka, jadwal seragam, senin, selasa, rabu, kamis, jumat, atribut, badge, siswi, lengan panjang, dasi di bawah pusar, standar panjang dasi"
        ),
        RegulationEntity(
            id = "REG_3_4_B_E",
            chapterNumber = "BAB 3",
            chapterTitle = "KEWAJIBAN MURID",
            sectionNumber = "3.4.B-E",
            title = "Jilbab, Upacara, Standar Dasi, Panjang Rok & Kerapian Seragam",
            fullContent = """B. Bagi yang berjilbab, memakai jilbab kain sesuai ketentuan sekolah (Segi empat) kecuali saat KBM Olah raga.
C. Pada saat Upacara Bendera dilengkapi topi pet, ikat pinggang warna hitam (bukan ikat pinggang dari SMP), dan dasi sesuai warna seragam masing-masing jenjang sekolah dengan standar panjang dasi di bawah pusar, dilengkapi dengan logo tut wuri handayani di bagian depan topi.
D. Standar Pemakaian Dasi: Ujung dasi wajib terpasang rapi dengan standar panjang dasi di bawah pusar.
E. Panjang rok, tidak boleh melebihi/di atas mata kaki.
F. Pakaian baik laki-laki dan perempuan tidak boleh ketat dan membentuk lekuk tubuh.""",
            summaryContent = "Ketentuan detail seragam: Jilbab kain segi empat; Upacara wajib topi pet logo Tut Wuri Handayani, dasi rapi dengan standar panjang dasi di bawah pusar, & sabuk hitam polos; Rok wajib menutup hingga mata kaki; Pakaian dilarang ketat/membentuk lekuk tubuh.",
            category = "Seragam",
            pageNumber = 2,
            keywords = "jilbab segi empat, topi pet upacara, dasi, standar panjang dasi di bawah pusar, ikat pinggang hitam, panjang rok mata kaki, pakaian ketat"
        ),
        RegulationEntity(
            id = "REG_3_5",
            chapterNumber = "BAB 3",
            chapterTitle = "KEWAJIBAN MURID",
            sectionNumber = "3.5",
            title = "Ketentuan Standar Sepatu dan Kaos Kaki",
            fullContent = """Murid memakai sepatu dan kaos kaki sesuai ketentuan yang ada, diantaranya:
A. Sepatu model standar siswa warna hitam polos atau doff, bukan glossy.
B. Tidak diperkenankan model flatshoes dsb yang tidak sesuai standar.
C. Kaos kaki sesuai ketentuan hari:
- Hari Senin-Kamis : putih polos dengan panjang 15 cm dari mata kaki
- Hari Jumat : kaos kaki warna hitam polos panjang 15 cm dari mata kaki.""",
            summaryContent = "Sepatu wajib hitam polos/doff bertali/fantovel standar (dilarang glossy/flatshoes). Kaos kaki polos tanpa motif panjang 15 cm di atas mata kaki: Senin-Kamis putih polos, Jumat hitam polos.",
            category = "Kewajiban",
            pageNumber = 3,
            keywords = "sepatu hitam polos, doff, bukan glossy, bukan flatshoes, kaos kaki putih polos, kaos kaki hitam jumat, 15 cm"
        ),
        RegulationEntity(
            id = "REG_3_6",
            chapterNumber = "BAB 3",
            chapterTitle = "KEWAJIBAN MURID",
            sectionNumber = "3.6",
            title = "Ketentuan Rambut, Make Up, Aksesoris, dan Kuku",
            fullContent = """Murid mematuhi peraturan terkait dengan rambut, penggunaan make up dan kuku diantaranya:
A. Berambut pendek rapi, tidak gondrong dan tidak dicat warna serta tidak gundul yang ada garis-garisnya juga tidak dimodel panjang bagian belakangnya, tebal bagian atas dan kriting yang tidak natural bagian depan.
B. Rambut model sederhana dengan ukuran potongan rambut 2-2-1 (untuk laki–laki).
C. Tidak memakai anting, tindik, tato, kalung, gelang (untuk laki–laki).
D. Rambut disisir rapi dan dikuncir rapi, tidak boleh dicat warna, dan disambung (untuk perempuan yang tidak berhijab).
E. Tidak memakai perhiasan, tato, tindik telinga lebih dari 1 (satu).
F. Alis tidak dicukur dan digaris.
G. Tidak menggunakan make up dan aksesoris yang berlebihan.
H. Tidak diperbolehkan berkuku panjang dan di cat kuku.""",
            summaryContent = "Standar penampilan: Putra wajib berambut potongan 2-2-1 rapi, bebas cat/tindik/tato/gelang/anting. Putri non-hijab rambut diikat rapi, dilarang cat/sambung (hair extension). Dilarang cukur/garis alis, kuku panjang, cat kuku/nail art, dan make up berlebihan.",
            category = "Kewajiban",
            pageNumber = 3,
            keywords = "potongan 2-2-1, rambut rapi, tidak gondrong, rambut putri dikuncir, alis tidak dicukur, kuku pendek, dilarang cat kuku, make up berlebihan, tindik, tato"
        ),
        RegulationEntity(
            id = "REG_3_7",
            chapterNumber = "BAB 3",
            chapterTitle = "KEWAJIBAN MURID",
            sectionNumber = "3.7",
            title = "Ketentuan Penggunaan Smartphone / HP Saat KBM",
            fullContent = "Penggunaan HP pada saat KBM berlangsung disimpan dalam kotak penyimpanan yang disediakan sekolah dan dikunci. Apabila akan menggunakan HP harus seijin guru pengajar di kelas.",
            summaryContent = "Saat KBM, HP wajib disimpan dan dikunci di kotak penyimpanan kelas. Penggunaan HP hanya diperbolehkan atas izin guru yang sedang mengajar.",
            category = "Kewajiban",
            pageNumber = 3,
            keywords = "penggunaan hp, kotak penyimpanan hp, smartphone, kunci hp, izin guru kbm"
        ),
        RegulationEntity(
            id = "REG_3_8_9",
            chapterNumber = "BAB 3",
            chapterTitle = "KEWAJIBAN MURID",
            sectionNumber = "3.8 - 3.9",
            title = "Kewajiban Mengikuti Kegiatan Sekolah & Ekstrakurikuler",
            fullContent = """3.8. Murid wajib mengikuti kegiatan yang diselenggarakan oleh sekolah.
3.9. Murid mengikuti 1 kegiatan ekskul wajib (kepramukaan) dan 1 kegiatan ekstra pilihan yang ada di sekolah.""",
            summaryContent = "Wajib berpartisipasi pada agenda resmi sekolah, serta mengikuti 1 ekskul wajib Pramuka ditambah 1 ekskul pilihan.",
            category = "Kewajiban",
            pageNumber = 3,
            keywords = "kegiatan sekolah, ekskul wajib pramuka, ekstrakurikuler pilihan"
        ),
        RegulationEntity(
            id = "REG_3_10_13",
            chapterNumber = "BAB 3",
            chapterTitle = "KEWAJIBAN MURID",
            sectionNumber = "3.10 - 3.13",
            title = "Pemeliharaan Sarpras, Nama Baik, Kebersihan & Etika Digital",
            fullContent = """3.10. Mengembangkan rasa ikut memiliki dan memelihara sarana prasarana dan inventaris kelas yang ada di sekolah.
3.11. Menjaga nama baik sekolah baik di dalam maupun di luar sekolah.
3.12. Menjaga kebersihan dan ketertiban masing-masing kelas.
3.13. Menjaga etika dan sopan santun dalam seluruh aktivitas digital.""",
            summaryContent = "Menjaga sarpras dan inventaris kelas, menjaga nama baik SMANEKA di mana pun berada, merawat kebersihan kelas, dan menjunjung etika sopan santun dalam aktivitas digital/medsos.",
            category = "Kewajiban",
            pageNumber = 3,
            keywords = "sarana prasarana, nama baik sekolah, kebersihan kelas, etika digital, media sosial"
        ),

        // BAB 4. LARANGAN MURID
        RegulationEntity(
            id = "REG_4_ALL",
            chapterNumber = "BAB 4",
            chapterTitle = "LARANGAN MURID",
            sectionNumber = "4.1 - 4.13",
            title = "Daftar Larangan Utama Murid SMANEKA",
            fullContent = """4.1. Murid meninggalkan kelas/sekolah tanpa izin.
4.2. Murid melakukan kegiatan yang bertentangan dengan norma hukum, agama, dan masyarakat.
4.3. Membawa barang di luar kebutuhan belajar dan alat komunikasi. Apabila tetap membawa segala resiko kehilangan dan kerusakan menjadi tanggung jawab pribadi.
4.4. Murid yang tidak memiliki SIM dan kelengkapan kendaraan, bagi kelas X mengendarai sepeda motor serta parkir di lingkungan sekolah.
4.5. Murid melakukan taruhan (judi) baik manual atau online.
4.6. Murid memalsukan tanda tangan dan surat ijin serta stempel sekolah.
4.7. Murid mengambil uang atau barang yang bukan miliknya.
4.8. Murid lakukan live streaming saat di sekolah kecuali melaksanakan tugas sekolah.
4.9. Murid merekam guru tanpa izin.
4.10. Murid membawa, menggunakan dan mengedarkan: Rokok, vape, liquid nikotin (berserta turunannya sejenisnya), Napza, Minuman keras dan sejenisnya yang memabukkan, Senjata tajam, Serta barang lain yang tidak berhubungan dengan kegiatan sekolah.
4.11. Murid melakukan intimidasi (fisik dan psikis), bullying secara langsung, dan SARA serta perbuatan asusila.
4.12. Murid membuat, menyimpan dan mengedarkan: Konten pornografi/pornoaksi, Konten yang merusak nama baik sekolah dan pihak warga sekolah, Berita hoax, Cyberbullying.
4.13. Merusak sarana dan prasarana sekolah.""",
            summaryContent = "13 Larangan Inti: Bolos/keluar tanpa izin; Tindakan melanggar norma; Barang non-belajar; Motor kelas X tanpa SIM; Judi manual/online; Pemalsuan surat/stempel; Mengambil barang orang lain; Live streaming tanpa tugas; Merekam guru tanpa izin; Rokok/Vape/Napza/Miras/Sajam; Bullying/SARA/Asusila; Konten pornografi/Hoax/Cyberbullying; Merusak sarpras.",
            category = "Larangan",
            pageNumber = 3,
            keywords = "larangan murid, judi, sim kelas x, live streaming, rekam guru, rokok, vape, napza, miras, sajam, bullying, hoax, pornografi, sarpras"
        ),

        // BAB 5. PENANGANAN PELANGGARAN
        RegulationEntity(
            id = "REG_5_ALL",
            chapterNumber = "BAB 5",
            chapterTitle = "PENANGANAN PELANGGARAN",
            sectionNumber = "5.A - 5.C",
            title = "Prinsip dan Tingkatan Penanganan Pelanggaran",
            fullContent = """Murid yang melakukan pelanggaran akan di proses melalui pendekatan pembinaan, edukatif, restoratif sesuai tingkat pelanggaran.

A. Pelanggaran I: teguran lisan, memberitahukan kepada guru wali melalui laporan tiap pekan, dan guru wali memberikan pembinaan. Pelanggaran berulang, melalui teguran tertulis dengan membuat surat komitmen, dan tindakan edukatif. Lebih dari 5 kali melakukan pelanggaran, guru wali bekerja sama dengan wali kelas melakukan pemanggilan wali murid didampingi tatib dan BK.
B. Pelanggaran II: berdasar laporan Tatib, Wali Kelas memanggil orang tua, memberikan teguran secara tertulis (dalam bentuk surat komitmen atau pernyataan) dan pembinaan edukatif didampingi Tatib dan BK.
C. Pelanggaran III: Wali kelas, Tatib, BK, Waka, Kepala Sekolah bersama-sama mengadakan konferensi khusus untuk mengambil kebijakan.""",
            summaryContent = "Prinsip: Pembinaan, Edukatif, dan Restorative Justice. Tiga tingkatan penanganan dari teguran lisan & pembinaan Guru Wali (Kategori I), pemanggilan orang tua & surat komitmen tertulis bersama BK (Kategori II), hingga Konferensi Kasus Khusus bersama Kepala Sekolah (Kategori III).",
            category = "Penanganan",
            pageNumber = 4,
            keywords = "penanganan pelanggaran, pembinaan edukatif, restorative justice, teguran lisan, surat komitmen, panggilan orang tua, konferensi khusus kepala sekolah"
        ),

        // BAB 6. HAK MURID
        RegulationEntity(
            id = "REG_6_ALL",
            chapterNumber = "BAB 6",
            chapterTitle = "HAK MURID",
            sectionNumber = "6.1 - 6.5",
            title = "Hak-Hak Peserta Didik",
            fullContent = """6.1. Presensi kehadiran murid di atur sesuai kebijakan sekolah di daerah masing - masing;
6.2. Murid menggunakan sarana dan prasarana sekolah setelah mendapat izin sekolah;
6.3. Murid mendapat perlakuan yang sama;
6.4. Murid mengikuti kegiatan sekolah; dan
6.5. Murid mendapatkan pelajaran agama sesuai yang dianutnya.""",
            summaryContent = "Hak Murid SMANEKA:\n1. Pengaturan presensi kehadiran sesuai kebijakan daerah/sekolah.\n2. Menggunakan sarpras sekolah dengan izin resmi.\n3. Memperoleh perlakuan yang setara dan adil (tanpa diskriminasi).\n4. Berhak mengikuti kegiatan sekolah.\n5. Memperoleh pendidikan agama sesuai agama yang dianutnya.",
            category = "Hak",
            pageNumber = 5,
            keywords = "hak murid, presensi, menggunakan sarana prasarana, perlakuan yang sama, kegiatan sekolah, pelajaran agama"
        ),

        // BAB 7. LAIN-LAIN
        RegulationEntity(
            id = "REG_7_ALL",
            chapterNumber = "BAB 7",
            chapterTitle = "LAIN-LAIN",
            sectionNumber = "7.1 - 7.3",
            title = "Ketentuan Penutup & Legalitas Pemberlakuan",
            fullContent = """7.1. Penerapan tata tertib di atas disesuaikan dengan kondisi sekolah SMA Negeri 1 Kepanjen.
7.2. Hal-hal lain yang tidak tercantum dalam tata tertib ini akan diatur kemudian.
7.3. Tata tertib ini mulai berlaku sejak tanggal ditetapkan.

Ditetapkan di: Kepanjen, 13 Juli 2026
Kepala SMA Negeri 1 Kepanjen Kabupaten Malang
FADILAH UMI MAISYAROH, M.Pd
Pembina Utama Muda - NIP. 19750827 200012 2 003""",
            summaryContent = "Tata tertib berlaku sejak 13 Juli 2026 yang disahkan oleh Kepala SMAN 1 Kepanjen, Fadilah Umi Maisyaroh, M.Pd.",
            category = "Lain-lain",
            pageNumber = 5,
            keywords = "lain-lain, 13 juli 2026, kepala sekolah, fadilah umi maisyaroh, sman 1 kepanjen"
        )
    )

    val violations = listOf(
        // KATEGORI A: PELANGGARAN I (RINGAN) - 29 BENTUK
        ViolationEntity(
            id = "V1_1",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 1,
            name = "Datang terlambat",
            consequence = "Teguran lisan langsung dari Tim Tatib/Guru Piket, pencatatan rekapitulasi mingguan, dan pembinaan individual oleh Guru Wali.",
            procedure = "SOP Kategori I: Pembinaan lisan. Jika > 5 kali terjadi pemanggilan orang tua oleh Wali Kelas didampingi Tatib & BK.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "datang terlambat, telat sekolah, masuk sekolah, piket tatib"
        ),
        ViolationEntity(
            id = "V1_2",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 2,
            name = "Tidak mengikuti pelajaran tanpa izin",
            consequence = "Teguran lisan dan pembinaan oleh Guru Wali, dilaporkan pada rekap mingguan.",
            procedure = "SOP Kategori I: Teguran lisan -> Teguran tertulis jika berulang -> Pemanggilan orang tua jika >5 kali.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "tidak ikut pelajaran, tanpa izin, alfa kbm, bolos jam pelajaran"
        ),
        ViolationEntity(
            id = "V1_3",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 3,
            name = "Meninggalkan kelas tanpa izin",
            consequence = "Teguran lisan dan pembinaan oleh Guru Wali.",
            procedure = "SOP Kategori I: Pembinaan oleh Guru Wali dan pencatatan oleh Tatib.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "meninggalkan kelas, keluar kelas tanpa izin, bolos kelas"
        ),
        ViolationEntity(
            id = "V1_4",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 4,
            name = "Berada di kantin saat jam pelajaran berlangsung",
            consequence = "Teguran lisan langsung dari Tim Tatib / Guru Piket dan pembinaan edukatif.",
            procedure = "SOP Kategori I: Dicatat petugas tatib dan dilaporkan ke Guru Wali.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "kantin saat jam pelajaran, jajan kbm, nongkrong di kantin"
        ),
        ViolationEntity(
            id = "V1_5",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 5,
            name = "Tidak berseragam sesuai dengan ketentuan",
            consequence = "Teguran lisan, penyesuaian pakaian seragam sesuai hari, pembinaan Guru Wali.",
            procedure = "SOP Kategori I: Pembinaan dan verifikasi jadwal seragam (Senin-Jumat).",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "salah seragam, tidak sesuai jadwal, pakaian sekolah, baju"
        ),
        ViolationEntity(
            id = "V1_6",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 6,
            name = "Seragam yang dicoret-coret",
            consequence = "Teguran lisan/tertulis, kewajiban mengganti seragam yang rapi dan bersih.",
            procedure = "SOP Kategori I: Pembinaan oleh Guru Wali & Tim Tatib.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "seragam dicoret-coret, baju kotor, corat coret seragam"
        ),
        ViolationEntity(
            id = "V1_7",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 7,
            name = "Tidak memakai kaos kaki",
            consequence = "Teguran lisan dari Tim Tatib dan pembinaan individual.",
            procedure = "SOP Kategori I: Pembinaan Tim Tatib/Guru Piket.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "tidak memakai kaos kaki, tanpa kaos kaki"
        ),
        ViolationEntity(
            id = "V1_8",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 8,
            name = "Memakai kaos kaki tidak sesuai ketentuan dengan panjang 10 cm dari mata kaki (Senin-Kamis putih polos, Jum'at hitam polos)",
            consequence = "Teguran lisan dan arahan menggunakan kaos kaki standar (15 cm di atas mata kaki polos).",
            procedure = "SOP Kategori I: Pembinaan dan pencatatan tatib mingguan.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "kaos kaki pendek, kaos kaki motif, kaos kaki tidak sesuai, 10 cm, kaos kaki hitam senin"
        ),
        ViolationEntity(
            id = "V1_9",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 9,
            name = "Tidak memakai ikat pinggang",
            consequence = "Teguran lisan dari piket tatib dan pembinaan kerapian.",
            procedure = "SOP Kategori I: Pembinaan lisan oleh Guru Wali.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "tidak memakai ikat pinggang, tanpa sabuk"
        ),
        ViolationEntity(
            id = "V1_10",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 10,
            name = "Memakai ikat pinggang tidak sesuai dengan ketentuan (hitam, tidak memakai atribut instansi lain)",
            consequence = "Teguran lisan, penggantian ikat pinggang hitam polos standar.",
            procedure = "SOP Kategori I: Pembinaan kerapian seragam.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "ikat pinggang smp, sabuk warna warni, atribut instansi lain"
        ),
        ViolationEntity(
            id = "V1_11",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 11,
            name = "Menggunakan Aksesoris berlebihan",
            consequence = "Teguran lisan, pelepasan aksesoris berlebihan untuk disimpan mandiri.",
            procedure = "SOP Kategori I: Pembinaan individual oleh Guru Wali.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "aksesoris berlebihan, perhiasan berlebihan"
        ),
        ViolationEntity(
            id = "V1_12",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 12,
            name = "Kuku Panjang",
            consequence = "Teguran lisan dan kewajiban memotong kuku agar bersih dan pendek.",
            procedure = "SOP Kategori I: Pembinaan kebersihan dan kerapian diri.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "kuku panjang, potong kuku, kuku kotor"
        ),
        ViolationEntity(
            id = "V1_13",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 13,
            name = "Berambut panjang/bisa dikuncir, diwarnai, gundul bergaris. Tidak sesuai aturan 2-2-1 (Untuk Putra)",
            consequence = "Teguran lisan, pembinaan Guru Wali, dan kewajiban memotong rambut sesuai standar potongan 2-2-1.",
            procedure = "SOP Kategori I: Pembinaan dan tindak lanjut perapian rambut.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "rambut gondrong, cat rambut, rambut putra, gundul garis, potongan 2-2-1, mohawk, mullet, skin fade",
            applicableTo = "Putra"
        ),
        ViolationEntity(
            id = "V1_14",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 14,
            name = "Seragam atribut tidak lengkap",
            consequence = "Teguran lisan dan kewajiban melengkapi badge/atribut seragam nasional.",
            procedure = "SOP Kategori I: Pembinaan oleh Guru Wali.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "atribut tidak lengkap, badge kesiswaan, badge merah putih, badge nama"
        ),
        ViolationEntity(
            id = "V1_15",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 15,
            name = "Tidak memakai sepatu hitam sesuai aturan sekolah polos bukan flatshoes, (selain olah raga)",
            consequence = "Teguran lisan dan arahan menggunakan sepatu hitam polos/doff bertali/fantovel standar.",
            procedure = "SOP Kategori I: Pembinaan Tim Tatib/Guru Piket.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "sepatu putih, sepatu warna, flatshoes, sepatu glossy, sepatu tidak standar"
        ),
        ViolationEntity(
            id = "V1_18",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 18,
            name = "Berambut panjang terurai, poni samping memanjang (murid putri)",
            consequence = "Teguran lisan dan kewajiban mengikat/menguncir rambut rapi menjadi satu.",
            procedure = "SOP Kategori I: Pembinaan kerapian diri oleh Guru Wali.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "rambut putri terurai, poni panjang, kuncir rambut putri",
            applicableTo = "Putri"
        ),
        ViolationEntity(
            id = "V1_19",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 19,
            name = "Memparkir sepeda/motor tidak pada tempatnya",
            consequence = "Teguran lisan dan kewajiban memindahkan kendaraan ke area parkir resmi.",
            procedure = "SOP Kategori I: Pembinaan ketertiban lingkungan sekolah.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "parkir sembarangan, parkir motor, parkir sepeda"
        ),
        ViolationEntity(
            id = "V1_20",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 20,
            name = "Melindungi teman yang bersalah",
            consequence = "Teguran lisan dan pembinaan integritas serta kejujuran oleh Guru Wali.",
            procedure = "SOP Kategori I: Pembinaan individual oleh Guru Wali.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "melindungi teman salah, berbohong, menutupi pelanggaran"
        ),
        ViolationEntity(
            id = "V1_21",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 21,
            name = "Membawa atau bermain bola, kartu remi, kartu uno dan domino di sekolah",
            consequence = "Teguran lisan dan pengamanan barang permainan oleh guru/tim tatib.",
            procedure = "SOP Kategori I: Pembinaan oleh Guru Piket/Tatib.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "main kartu remi, uno, domino, main bola di kelas, mainan di sekolah"
        ),
        ViolationEntity(
            id = "V1_22",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 22,
            name = "Memakai anting, tindik (bagi putra), tato, kalung, gelang",
            consequence = "Teguran lisan, pelepasan aksesoris, dan pembinaan kedisiplinan diri.",
            procedure = "SOP Kategori I: Pembinaan Guru Wali didampingi Tim Tatib.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "anting putra, tindik, tato, kalung putra, gelang putra",
            applicableTo = "Putra"
        ),
        ViolationEntity(
            id = "V1_23",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 23,
            name = "Alis dicukur, ditato dan memakai kosmetik berlebihan",
            consequence = "Teguran lisan dan pembinaan penampilan wajar sesuai usia peserta didik.",
            procedure = "SOP Kategori I: Pembinaan oleh Guru Wali.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "cukur alis, alis ditato, kosmetik berlebihan, make up berlebihan"
        ),
        ViolationEntity(
            id = "V1_24",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 24,
            name = "Memakai extention (rambut, bulu mata, kuku)",
            consequence = "Teguran lisan dan pembinaan untuk melepas sambungan kecantikan non-standar.",
            procedure = "SOP Kategori I: Pembinaan Guru Wali.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "extension rambut, bulu mata palsu, sambung kuku"
        ),
        ViolationEntity(
            id = "V1_25",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 25,
            name = "Memakai cat kuku (untuk putri)",
            consequence = "Teguran lisan dan kewajiban membersihkan/menghapus cat kuku.",
            procedure = "SOP Kategori I: Pembinaan kebersihan dan kerapian diri.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "cat kuku, kutek, nail art putri",
            applicableTo = "Putri"
        ),
        ViolationEntity(
            id = "V1_26",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 26,
            name = "Mencoret-coret atau mengotori dinding, pintu, meja, kursi, pagar sekolah",
            consequence = "Teguran lisan/tertulis, kewajiban membersihkan dan merapikan kembali fasilitas yang dikotori.",
            procedure = "SOP Kategori I: Tindakan edukatif relevan (kebersihan/refleksi diri).",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "coret meja, vandalisme ringan, coret dinding, kotori bangku, pagar sekolah"
        ),
        ViolationEntity(
            id = "V1_27",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 27,
            name = "Menggunakan HP saat KBM berlangsung tanpa seijin guru",
            consequence = "Teguran lisan langsung dari guru pengajar, HP diamankan ke dalam kotak penyimpanan terkunci.",
            procedure = "SOP Kategori I: Pembinaan kepatuhan belajar di kelas.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "main hp saat kbm, smartphone tanpa izin guru, hp saat pelajaran"
        ),
        ViolationEntity(
            id = "V1_28",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 28,
            name = "Pulang sebelum waktunya tanpa izin sekolah",
            consequence = "Teguran tertulis, pembinaan Guru Wali, dan pemberitahuan kepada orang tua.",
            procedure = "SOP Kategori I: Pembinaan dan verifikasi izin resmi.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 6,
            keywords = "pulang sebelum waktu, pulang awal tanpa izin, kabur kbm"
        ),
        ViolationEntity(
            id = "V1_29",
            tier = "Pelanggaran I (Ringan)",
            tierNumber = 1,
            number = 29,
            name = "Menyalahgunakan uang sekolah",
            consequence = "Teguran tertulis, kewajiban pengembalian penuh, dan pembinaan intensif.",
            procedure = "SOP Kategori I: Pembinaan oleh Guru Wali bekerja sama dengan Wali Kelas & Tatib.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID A. PELANGGARAN I",
            pageNumber = 7,
            keywords = "salah guna uang sekolah, uang kas, penggelapan iuran"
        ),

        // KATEGORI B: PELANGGARAN II (SEDANG) - 6 BENTUK
        ViolationEntity(
            id = "V2_1",
            tier = "Pelanggaran II (Sedang)",
            tierNumber = 2,
            number = 1,
            name = "Meloncat jendela dan pagar sekolah",
            consequence = "Pemanggilan resmi Orang Tua/Wali Murid oleh Wali Kelas, pembuatan Surat Pernyataan/Komitmen Tertulis bermaterai/diketahui orang tua, serta pembinaan edukatif difasilitasi BK dan Wali Kelas.",
            procedure = "SOP Kategori II: Tatib membuat laporan -> Wali Kelas menerbitkan surat pemanggilan resmi -> Pertemuan Orang Tua, Wali Kelas, Tatib, BK -> Surat Komitmen Tertulis.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID B. PELANGGARAN II",
            pageNumber = 7,
            keywords = "lompat pagar, loncat jendela, kabur lewat pagar"
        ),
        ViolationEntity(
            id = "V2_2",
            tier = "Pelanggaran II (Sedang)",
            tierNumber = 2,
            number = 2,
            name = "Merusak sarana dan prasarana sekolah",
            consequence = "Pemanggilan resmi orang tua, ganti rugi perbaikan sarpras yang rusak, surat komitmen bermaterai, dan pembinaan khusus BK.",
            procedure = "SOP Kategori II: Penanganan dihadiri Orang Tua, Wali Kelas, Tim Tatib, dan Guru BK.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID B. PELANGGARAN II",
            pageNumber = 7,
            keywords = "merusak sarpras, fasilitas sekolah rusak, perusakan inventaris"
        ),
        ViolationEntity(
            id = "V2_3",
            tier = "Pelanggaran II (Sedang)",
            tierNumber = 2,
            number = 3,
            name = "Berada di luar lingkungan sekolah saat kegiatan sekolah berlangsung",
            consequence = "Pemanggilan orang tua secara resmi oleh Wali Kelas, pembuatan surat komitmen bermaterai, dan pendampingan pembinaan BK.",
            procedure = "SOP Kategori II: Penanganan resmi bersama Tim Tatib dan BK.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID B. PELANGGARAN II",
            pageNumber = 7,
            keywords = "di luar sekolah saat kbm, bolos keluar sekolah, nongkrong di luar"
        ),
        ViolationEntity(
            id = "V2_4",
            tier = "Pelanggaran II (Sedang)",
            tierNumber = 2,
            number = 4,
            name = "Membuat surat izin palsu",
            consequence = "Pemanggilan resmi orang tua oleh Wali Kelas, pembuatan surat komitmen tertulis bermaterai, dan bimbingan integritas oleh BK.",
            procedure = "SOP Kategori II: Penanganan resmi dihadiri Orang Tua, Wali Kelas, Tim Tatib, dan Guru BK.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID B. PELANGGARAN II",
            pageNumber = 7,
            keywords = "surat izin palsu, tanda tangan palsu surat izin, memalsukan izin"
        ),
        ViolationEntity(
            id = "V2_5",
            tier = "Pelanggaran II (Sedang)",
            tierNumber = 2,
            number = 5,
            name = "Membawa atau merokok baik itu vape atau sejenisnya saat masih mengenakan seragam sekolah (baik di lingkungan sekolah atau luar sekolah)",
            consequence = "Pemanggilan resmi orang tua oleh Wali Kelas, penyitaan barang bukti, pembuatan surat pernyataan komitmen bermaterai diketahui orang tua, dan pembinaan edukatif khusus BK.",
            procedure = "SOP Kategori II: Laporan Tatib -> Surat Panggilan Resmi -> Penanganan oleh Orang Tua, Wali Kelas, Tatib, BK.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID B. PELANGGARAN II",
            pageNumber = 7,
            keywords = "merokok, vape, rokok elektrik, berseragam merokok, nikotin, pod"
        ),
        ViolationEntity(
            id = "V2_6",
            tier = "Pelanggaran II (Sedang)",
            tierNumber = 2,
            number = 6,
            name = "Melakukan live streaming saat di sekolah kecuali melaksanakan tugas sekolah",
            consequence = "Pemanggilan resmi orang tua oleh Wali Kelas, pembuatan surat komitmen bermaterai, dan pembinaan literasi digital oleh BK.",
            procedure = "SOP Kategori II: Penanganan resmi Tim Tatib dan BK.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID B. PELANGGARAN II",
            pageNumber = 7,
            keywords = "live streaming di sekolah, siaran langsung, medsos kbm, tiktok live"
        ),

        // KATEGORI C: PELANGGARAN III (BERAT) - 16 BENTUK
        ViolationEntity(
            id = "V3_1",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 1,
            name = "Bertindak tidak sopan/ melecehkan di lingkungan Civitas Akademika SMAN 1 Kepanjen",
            consequence = "Pelaksanaan Konferensi Kasus Khusus (Kepala Sekolah, Waka Kesiswaan, Tim Tatib, Guru BK, Wali Kelas, Orang Tua & Siswa) dan pengambilan Keputusan Sanksi Tertinggi (misal: skorsing pembinaan orang tua/pengembalian siswa kepada orang tua).",
            procedure = "SOP Kategori III: Rekapitulasi jejak rekam oleh Tatib & BK -> Konferensi Kasus Khusus 6 pihak -> Sanksi Tertinggi.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "pelecehan, tidak sopan, civitas akademika, guru, staf smaneka"
        ),
        ViolationEntity(
            id = "V3_2",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 2,
            name = "Melakukan bullying, SARA, ujaran kebencian, penghinaan dan pencemaran nama baik terhadap warga sekolah di dalam maupun di luar sekolah baik secara langsung maupun dalam media sosial",
            consequence = "Konferensi Kasus Khusus dipimpin Kepala Sekolah, penetapan Sanksi Tertinggi (skorsing pembinaan/pengembalian ke orang tua).",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus (Kepala Sekolah, Waka, Tatib, BK, Wali Kelas, Orang Tua).",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "bullying, perundungan, sara, ujaran kebencian, pencemaran nama baik, media sosial"
        ),
        ViolationEntity(
            id = "V3_3",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 3,
            name = "Membawa atau membunyikan petasan di sekolah",
            consequence = "Konferensi Kasus Khusus dan Sanksi Kebijakan Tertinggi dari Kepala Sekolah.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus dipimpin Kepala Sekolah.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "petasan, mercon, membunyikan petasan, bahan peledak"
        ),
        ViolationEntity(
            id = "V3_4",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 4,
            name = "Menyalahgunakan media sosial yang merugikan pihak lain yang berhubungan dengan sekolah",
            consequence = "Konferensi Kasus Khusus 6 pihak dan penjatuhan Sanksi Tertinggi.",
            procedure = "SOP Kategori III: Rekap jejak digital -> Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "salah guna medsos, mencemarkan pihak sekolah, akun palsu"
        ),
        ViolationEntity(
            id = "V3_5",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 5,
            name = "Terlibat langsung maupun tidak langsung perkelahian/tawuran di sekolah, di luar sekolah atau antar sekolah",
            consequence = "Konferensi Kasus Khusus Kepala Sekolah dan penetapan Sanksi Tertinggi.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "tawuran, perkelahian, baku hantam, antar sekolah, tawuran pelajar"
        ),
        ViolationEntity(
            id = "V3_6",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 6,
            name = "Membawa, menggunakan atau mengedarkan miras",
            consequence = "Konferensi Kasus Khusus dan penjatuhan Sanksi Tertinggi (skorsing/pengembalian ke orang tua).",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "miras, minuman keras, alkohol, mabuk di sekolah"
        ),
        ViolationEntity(
            id = "V3_7",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 7,
            name = "Mencuri di sekolah dan di luar sekolah",
            consequence = "Konferensi Kasus Khusus, ganti rugi pemulihan, dan Sanksi Tertinggi.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "mencuri, pencurian barang, maling di sekolah"
        ),
        ViolationEntity(
            id = "V3_8",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 8,
            name = "Memalsukan stempel sekolah, edaran sekolah atau tanda tangan Kepala Sekolah, guru dan karyawan sekolah serta wali murid",
            consequence = "Konferensi Kasus Khusus 6 pihak dan penjatuhan Sanksi Tertinggi.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "palsukan stempel, palsu tanda tangan kepala sekolah, edaran palsu"
        ),
        ViolationEntity(
            id = "V3_9",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 9,
            name = "Berjudi dalam bentuk apapun di sekolah",
            consequence = "Konferensi Kasus Khusus dan penjatuhan Sanksi Tertinggi.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "judi di sekolah, judi online, taruhan uang"
        ),
        ViolationEntity(
            id = "V3_10",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 10,
            name = "Membawa, menggunakan atau mengedarkan NAPZA terlarang",
            consequence = "Konferensi Kasus Khusus, penindakan tegas, dan Sanksi Tertinggi pengembalian kepada orang tua.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "napza, narkoba, obat terlarang, psikotropika"
        ),
        ViolationEntity(
            id = "V3_11",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 11,
            name = "Terlibat tindakan kriminal",
            consequence = "Konferensi Kasus Khusus dan Sanksi Tertinggi.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "tindakan kriminal, kejahatan hukum pidana"
        ),
        ViolationEntity(
            id = "V3_12",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 12,
            name = "Terbukti hamil atau menghamili",
            consequence = "Konferensi Kasus Khusus dan Keputusan Kebijakan/Sanksi Tertinggi.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "hamil, menghamili"
        ),
        ViolationEntity(
            id = "V3_13",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 13,
            name = "Membawa senjata tajam, senjata api dsb. di sekolah",
            consequence = "Penyitaan barang berbahaya, Konferensi Kasus Khusus, dan Sanksi Tertinggi.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 7,
            keywords = "senjata tajam, sajam, senjata api, pistol, pisau belati"
        ),
        ViolationEntity(
            id = "V3_14",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 14,
            name = "Berperilaku asusila baik didalam maupun diluar sekolah",
            consequence = "Konferensi Kasus Khusus dan Keputusan Sanksi Tertinggi.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 8,
            keywords = "asusila, perbuatan asusila, pornografi fisik"
        ),
        ViolationEntity(
            id = "V3_15",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 15,
            name = "Membawa, membuat, melakukan, serta mengedarkan sesuatu yang bersifat pornografi dan pornoaksi",
            consequence = "Penyitaan konten/perangkat, Konferensi Kasus Khusus, dan penetapan Sanksi Tertinggi.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 8,
            keywords = "pornografi, pornoaksi, edarkan video porno, gambar tidak senonoh"
        ),
        ViolationEntity(
            id = "V3_16",
            tier = "Pelanggaran III (Berat)",
            tierNumber = 3,
            number = 16,
            name = "Mengikuti aliran/perkumpulan/geng terlarang/Komunitas LGBT dan radikalisme",
            consequence = "Konferensi Kasus Khusus bersama Kepala Sekolah dan Sanksi Tertinggi.",
            procedure = "SOP Kategori III: Konferensi Kasus Khusus.",
            sourceChapter = "BENTUK-BENTUK PELANGGARAN MURID C. PELANGGARAN III",
            pageNumber = 8,
            keywords = "geng terlarang, aliran sesat, radikalisme, komunitas lgbt, ormas dilarang"
        )
    )

    val handlingProcedures = listOf(
        HandlingProcedureEntity(
            id = "SOP_1",
            categoryName = "KATEGORI PELANGGARAN I (Sanksi Ringan)",
            severityLevel = "Ringan",
            stage = "Tindakan Awal & Bertahap (Sanksi Ringan)",
            steps = """• Tindakan Awal:
1. Pembinaan dan Teguran Lisan langsung dari Tim Tatib/Guru Piket.
2. Tim Tatib mencatat dan melaporkan rekapitulasi pelanggaran mingguan kepada Guru Wali.
3. Guru Wali memberikan pembinaan individual.

• Jika Berulang (Pelanggaran Lanjutan):
1. Diberikan Teguran Tertulis dan siswa membuat Surat Komitmen.
2. Diberikan tindakan edukatif yang relevan (misalnya: tugas refleksi diri/kegiatan sosial sekolah).

• Jika Pelanggaran > 5 Kali:
1. Guru Wali berkoordinasi dengan Wali Kelas.
2. Pemanggilan Orang Tua/Wali Murid oleh Wali Kelas didampingi oleh Tim Tatib dan Guru BK.""",
            actorsInvolved = "Tim Tatib, Guru Piket, Guru Wali, Wali Kelas, Guru BK, Orang Tua",
            expectedOutcome = "Pemahaman aturan, refleksi diri, komitmen tertulis, dan pemulihan perilaku melalui pembinaan restoratif.",
            pageNumber = 9
        ),
        HandlingProcedureEntity(
            id = "SOP_2",
            categoryName = "KATEGORI PELANGGARAN II (Sanksi Sedang)",
            severityLevel = "Sedang",
            stage = "Tindakan Penanganan Resmi (Sanksi Sedang)",
            steps = """1. Tim Tatib menyerahkan Laporan Catatan Pelanggaran kepada Wali Kelas.
2. Wali Kelas menerbitkan surat pemanggilan Resmi kepada Orang Tua/Wali Murid.
3. Pelaksanaan penanganan dihadiri oleh Orang Tua, Wali Kelas, Tim Tatib, dan Guru BK.
4. Siswa membuat Surat Pernyataan/Komitmen Tertulis bermaterai/diketahui orang tua.
5. Pembinaan edukatif khusus difasilitasi oleh BK dan Wali Kelas.""",
            actorsInvolved = "Tim Tatib, Wali Kelas, Guru BK, Orang Tua/Wali Murid, Siswa",
            expectedOutcome = "Surat Pernyataan/Komitmen bermaterai, pembinaan edukatif intensif BK, dan pendampingan terpadu orang tua.",
            pageNumber = 9
        ),
        HandlingProcedureEntity(
            id = "SOP_3",
            categoryName = "KATEGORI PELANGGARAN III (Sanksi Berat)",
            severityLevel = "Berat",
            stage = "Konferensi Kasus Khusus & Sanksi Berat (Tertinggi)",
            steps = """1. Tim Tatib dan BK merekap seluruh rekam jejak pelanggaran siswa.
2. Pelaksanaan Konferensi Kasus Khusus yang dihadiri oleh:
   ▪ Kepala Sekolah
   ▪ Wakil Kepala Sekolah (Waka Kesiswaan)
   ▪ Tim Ketertiban (Tatib)
   ▪ Guru Bimbingan Konseling (BK)
   ▪ Wali Kelas
   ▪ Orang Tua/Wali Murid beserta Siswa yang bersangkutan.
3. Konferensi menghasilkan Keputusan Kebijakan/Sanksi Tertinggi (misalnya: skorsing pembinaan orang tua/pengembalian siswa kepada orang tua).""",
            actorsInvolved = "Kepala Sekolah, Waka Kesiswaan, Tim Tatib, Guru BK, Wali Kelas, Orang Tua, Siswa",
            expectedOutcome = "Keputusan Kebijakan/Sanksi Tertinggi (skorsing pembinaan orang tua / pengembalian siswa kepada orang tua).",
            pageNumber = 10
        )
    )

    val visualStandards = listOf(
        VisualStandardEntity(
            id = "STD_SERAGAM",
            title = "Ketentuan Seragam Sekolah",
            subtitle = "Jadwal harian, aturan lengan seragam, ketentuan seragam siswa putra/putri, rambut & dasi",
            category = "Seragam",
            permittedRule = "• Siswa Laki-Laki (Putra): Rambut rapi ukuran 2-2-1, kemeja dimasukkan rapi ke dalam celana, WAJIB mengenakan dasi sekolah resmi dengan standar panjang di bawah pusar setiap saat di sekolah, ikat pinggang hitam polos standar, celana panjang model sopan standar siswa (bukan pensil/botol/cutbray).\n• Siswa Perempuan (Putri): WAJIB mengenakan dasi sekolah resmi dengan standar panjang di bawah pusar setiap saat di sekolah, seluruh baju seragam atasan (Senin s.d. Jumat) WAJIB LENGAN PANJANG, rok panjang rapi sampai mata kaki, jilbab segi empat resmi.\n• Jadwal Harian: Senin Putih-Putih Lengan Panjang + Dasi (Putra & Putri) & Topi Upacara, Selasa Putih Abu-abu (Putra Putri wajib lengan panjang), Rabu Putih Abu-abu (Putra lengan pendek, Putri lengan panjang), Kamis Batik SMANEKA (Putra lengan pendek, Putri lengan panjang), Jumat Pramuka (Putra lengan pendek, Putri lengan panjang) + Atribut Lengkap.",
            prohibitedRule = "Dilarang siswa putra/putri tidak memakai dasi di sekolah, dasi menggantung tinggi di atas pusar, rambut putra gondrong/tidak rapi/diwarnai, celana ketat/pensil/botol/cutbray, kemeja dikeluarkan, rok putri di atas mata kaki, baju lengan pendek bagi siswi putri, seragam ketat membentuk lekuk tubuh, atribut tidak lengkap.",
            details = "Siswa laki-laki (putra) wajib berambut rapi ukuran 2-2-1 dan mengenakan dasi resmi sekolah dengan standar panjang di bawah pusar (ujung dasi rapi menutupi pusar hingga di bawah pusar), kemeja dimasukkan rapi ke celana panjang, serta ikat pinggang hitam polos standar berlogo sekolah. Siswi perempuan (putri) juga wajib mengenakan dasi sekolah resmi dengan panjang di bawah pusar setiap saat di sekolah, seluruh atasan seragam wajib lengan panjang, dan rok panjang menutup mata kaki.",
            sourceReference = "BAB 3 Poin 3.4 & Lampiran Pedoman",
            pageNumber = 2
        ),
        VisualStandardEntity(
            id = "STD_SEPATU_KAOSKAKI",
            title = "Standar Sepatu & Kaos Kaki",
            subtitle = "Model sepatu yang boleh / dilarang dan spesifikasi kaos kaki",
            category = "Sepatu & Kaos Kaki",
            permittedRule = "• Sepatu Wajib: Model standar siswa warna hitam polos atau hitam doff bertali hitam / model pantofel/fantovel hitam polos standar.\n• Kaos Kaki: Panjang minimal 15 cm di atas mata kaki. Senin s.d. Kamis warna putih polos tanpa motif. Hari Jumat warna hitam polos tanpa motif.",
            prohibitedRule = "• Dilarang sepatu warna putih / warna-warni / bergaris selain hitam (kecuali jam olahraga).\n• Dilarang memakai model flatshoes atau slip-on santai tipis.\n• Dilarang memakai sepatu glossy (mengkilap mencolok) / boots.\n• Dilarang memakai sandal / selop di sekolah tanpa izin medis.\n• Dilarang kaos kaki pendek (< 15 cm) / semata kaki / kaos kaki bermotif.",
            details = "Untuk selain pelajaran olah raga, sepatu wajib hitam polos standar bertali.",
            sourceReference = "BAB 3 Poin 3.5 & Lampiran Contoh Halaman 11",
            pageNumber = 3
        ),
        VisualStandardEntity(
            id = "STD_RAMBUT_PUTRA",
            title = "Standar Penampilan Siswa Laki-Laki",
            subtitle = "Potongan rambut ukuran 2-2-1 dan kerapian wajah",
            category = "Rambut Putra",
            permittedRule = "Potongan rambut rapi sederhana ukuran 2-2-1 (2 cm samping, 2 cm belakang, 1 cm batas tepian/gradasi rapi natural).",
            prohibitedRule = "Dilarang gondrong, diwarnai/dicat, gundul bergaris, mohawk, mullet, undercut ekstrem, skin fade, alis dicukur/digaris, memakai anting/tindik/tato/kalung/gelang.",
            details = "Rambut bagian belakang tidak boleh panjang menyentuh kerah, bagian atas tidak boleh tebal berlebihan, dan depan tidak boleh keriting buatan/menutupi dahi.",
            sourceReference = "BAB 3 Poin 3.6 & Lampiran Contoh Halaman 12",
            pageNumber = 3
        ),
        VisualStandardEntity(
            id = "STD_RAMBUT_PUTRI",
            title = "Standar Penampilan Siswa Perempuan",
            subtitle = "Kerapian rambut non-hijab dan jilbab resmi",
            category = "Rambut Putri",
            permittedRule = "Bagi yang tidak berhijab, rambut berwarna asli/hitam disisir rapi dan diikat/dikuncir satu ke belakang.",
            prohibitedRule = "Dilarang mengecat warna rambut, mencurly buatan, memanjangkan poni samping terurai di wajah, menyambung rambut (hair extension), menggunakan aksesoris rambut berlebihan.",
            details = "Bagi siswi muslimah yang berjilbab, menggunakan jilbab kain segi empat polos sesuai warna seragam.",
            sourceReference = "BAB 3 Poin 3.6 & Lampiran Contoh Halaman 12",
            pageNumber = 3
        ),
        VisualStandardEntity(
            id = "STD_KUKU_MAKEUP",
            title = "Standar Make Up, Kuku & Kosmetik",
            subtitle = "Larangan kosmetik berlebihan, riasan wajah, kuku & aksesoris",
            category = "Kuku & Aksesoris",
            permittedRule = "• Wajah: Tampil alami, bersih, dan segar khas pelajar. Boleh menggunakan pelembab/sunscreen tipis dan bedak tabur transparan untuk merawat kulit.\n• Kuku: Dipotong pendek, rapi, dan bersih secara natural.",
            prohibitedRule = "• Dilarang menggunakan make up tebal / menor / kosmetik berlebihan.\n• Dilarang memakai lipstik merah/mencolok (hanya boleh pelembab/lip balm transparan).\n• Dilarang mencukur alis, membuat tato/sulam alis, atau menggambar pensil alis mencolok.\n• Dilarang memakai extension bulu mata (eyelash extension) atau bulu mata palsu.\n• Dilarang memakai cat kuku (kutek), nail art, henna berwarna, atau kuku palsu/sambung kuku.\n• Dilarang memakai hair extension / perhiasan emas berlebihan.",
            details = "Murid perempuan wajib menjaga penampilan natural, sopan, dan tidak berdandan layaknya ke pesta.",
            sourceReference = "BAB 3 Poin 3.6.E-H & Lampiran Halaman 12",
            pageNumber = 3
        ),
        VisualStandardEntity(
            id = "STD_GADGET_KENDARAAN",
            title = "Penggunaan HP & Aturan Kendaraan",
            subtitle = "Kotak penyimpanan smartphone dan SIM kendaraan",
            category = "Gadget & HP",
            permittedRule = "HP disimpan dan dikunci dalam kotak penyimpanan kelas selama KBM. Hanya dapat digunakan dengan izin guru pengajar untuk keperluan pembelajaran.",
            prohibitedRule = "Dilarang bermain HP tanpa izin guru saat KBM, live streaming tanpa tugas, dan merekam guru tanpa izin. Siswa Kelas X tanpa SIM dilarang mengendarai motor ke sekolah dan dilarang parkir di sekolah.",
            details = "Barang bawaan di luar kebutuhan belajar menjadi tanggung jawab pribadi jika terjadi kehilangan/kerusakan.",
            sourceReference = "BAB 2 Poin 2.9, BAB 3 Poin 3.7, BAB 4 Poin 4.3, 4.4, 4.8, 4.9",
            pageNumber = 3
        )
    )

    val learningTopics = listOf(
        LearningTopicEntity(
            id = "TOPIC_1",
            topicNumber = 1,
            title = "Hal Masuk Sekolah & Jam Belajar",
            category = "Kehadiran",
            summary = "Pahami waktu bel masuk, batas keterlambatan, jadwal KBM per hari, dan prosedur perizinan.",
            keyPoints = """• Bel masuk berbunyi pukul 06.45 WIB (Jumat pukul 07.30 WIB).
• Murid wajib tiba di sekolah 15 menit sebelum bel berbunyi (pukul 06.30 WIB).
• Jam belajar: Senin (06.45-15.05), Selasa-Kamis (06.45-15.15), Jumat (07.30-14.40).
• Terlambat wajib lapor ke piket tatib dan menerima pembinaan.
• Berhalangan hadir wajib disertai surat tertulis dari orang tua/wali.
• Keluar sekolah sebelum KBM selesai wajib izin guru kelas, piket, waka & dijemput orang tua.
• Berada di sekolah maksimal pukul 17.00 WIB.""",
            fullReference = "BAB 2. HAL MASUK SEKOLAH (Poin 2.1 s.d. 2.9)",
            sourceSection = "BAB 2 Poin 2.1 - 2.9",
            pageNumber = 1
        ),
        LearningTopicEntity(
            id = "TOPIC_2",
            topicNumber = 2,
            title = "Standar Seragam, Lengan & Dasi Sekolah",
            category = "Seragam",
            summary = "Ketahui jadwal seragam resmi dari Senin hingga Jumat, kelengkapan atribut, standar panjang dasi di bawah pusar, dan ketentuan khusus siswi.",
            keyPoints = """• Senin: Putih-Putih lengan panjang (putra & putri), dasi di bawah pusar + topi pet & atribut lengkap.
• Selasa: Putih lengan panjang (putra & putri wajib lengan panjang), celana/rok abu-abu + atribut lengkap.
• Rabu: Putih lengan pendek (putra) / baju putih lengan panjang (putri), celana/rok abu-abu + atribut.
• Kamis: Baju Batik SMANEKA lengan pendek (putra) / baju batik lengan panjang (putri), celana/rok putih.
• Jumat: Baju Pramuka lengan pendek (putra) / baju pramuka lengan panjang (putri) + atribut pramuka lengkap.
• Standar Panjang Dasi: Ujung dasi wajib terpasang rapi dengan standar panjang di bawah pusar.
• Ketentuan Khusus Siswi: SELURUH seragam atasan (Senin s.d. Jumat) wajib LENGAN PANJANG.
• Sepatu hitam polos/doff bertali atau pantovel (bukan glossy/flatshoes).
• Kaos kaki panjang 15 cm dari mata kaki: Senin-Kamis putih polos, Jumat hitam polos.
• Rok wajib menutup hingga mata kaki, pakaian dilarang ketat.""",
            fullReference = "BAB 3. KEWAJIBAN MURID (Poin 3.4 & 3.5)",
            sourceSection = "BAB 3 Poin 3.4 & 3.5",
            pageNumber = 2
        ),
        LearningTopicEntity(
            id = "TOPIC_3",
            topicNumber = 3,
            title = "Kerapian Rambut, Wajah, Kuku & Make Up",
            category = "Penampilan",
            summary = "Panduan potongan rambut 2-2-1 bagi putra, kuncir rapi bagi putri, dan standar kebersihan diri.",
            keyPoints = """• Siswa Laki-Laki: Potongan rambut rapi 2-2-1, tidak gondrong, tidak dicat, tidak mohawk/mullet/undercut ekstrem.
• Dilarang bertindik, anting, tato, kalung, atau gelang bagi siswa laki-laki.
• Siswa Perempuan: Rambut disisir rapi dan diikat satu (non-hijab), bebas pewarna/extension.
• Jilbab kain segi empat sesuai seragam sekolah.
• Alis dilarang dicukur/digaris/ditato.
• Kuku wajib dipotong pendek, dilarang cat kuku (kutek) atau nail art.
• Dilarang menggunakan make up dan aksesoris berlebihan.""",
            fullReference = "BAB 3. KEWAJIBAN MURID (Poin 3.6)",
            sourceSection = "BAB 3 Poin 3.6",
            pageNumber = 3
        ),
        LearningTopicEntity(
            id = "TOPIC_4",
            topicNumber = 4,
            title = "Tingkatan Pelanggaran & SOP Penanganan",
            category = "SOP Tatib",
            summary = "Pelajari pengelompokan pelanggaran (Ringan, Sedang, Berat) serta prinsip Restorative Justice.",
            keyPoints = """• Prinsip: Pendidikan, Pembinaan Edukatif, dan Keadilan Restoratif (Restorative Justice).
• Pelanggaran I (Ringan - 29 Bentuk): Teguran lisan -> Teguran tertulis & surat komitmen -> Panggilan orang tua jika > 5 kali.
• Pelanggaran II (Sedang - 6 Bentuk): Laporan Tatib -> Panggilan Resmi Wali Kelas -> Pertemuan 4 Pihak (Ortu, Wali Kelas, Tatib, BK) -> Surat pernyataan bermaterai.
• Pelanggaran III (Berat - 16 Bentuk): Konferensi Kasus Khusus dihadiri Kepala Sekolah, Waka Kesiswaan, Tatib, BK, Wali Kelas, Ortu & Siswa -> Keputusan Sanksi Tertinggi (skorsing/pengembalian ke ortu).
• Akumulasi pelanggaran dihitung untuk masa 1 tahun.""",
            fullReference = "BAB 5. PENANGANAN PELANGGARAN & SOP SMANEKA",
            sourceSection = "BAB 5 & SOP Halaman 9-10",
            pageNumber = 4
        ),
        LearningTopicEntity(
            id = "TOPIC_5",
            topicNumber = 5,
            title = "Etika Digital, HP & Hak-Hak Siswa",
            category = "Etika & Hak",
            summary = "Aturan penyimpanan HP saat KBM, larangan cyberbullying/live streaming, serta 5 hak utama murid.",
            keyPoints = """• HP wajib disimpan dalam kotak penyimpanan terkunci di kelas selama KBM.
• Dilarang melakukan live streaming di sekolah (kecuali tugas) dan dilarang merekam guru tanpa izin.
• Dilarang cyberbullying, penyebaran hoax, konten pornografi, atau konten pencemaran nama baik.
• Hak Murid: Presensi diatur kebijakan resmi, menggunakan sarpras berizin, mendapat perlakuan setara, mengikuti kegiatan sekolah, dan pelajaran agama sesuai keyakinan.""",
            fullReference = "BAB 3 Poin 3.7 & 3.13, BAB 4 Poin 4.8 & 4.12, BAB 6 HAK MURID",
            sourceSection = "BAB 3, 4 & 6",
            pageNumber = 3
        )
    )

    val quizQuestions = listOf(
        QuizQuestionEntity(
            id = "Q1",
            topicId = "TOPIC_1",
            questionType = "MULTIPLE_CHOICE",
            question = "Berapa menit sebelum bel masuk murid SMANEKA wajib hadir di sekolah?",
            optionA = "5 menit sebelum bel berbunyi",
            optionB = "10 menit sebelum bel berbunyi",
            optionC = "15 menit sebelum bel berbunyi",
            optionD = "30 menit sebelum bel berbunyi",
            correctOptionIndex = 2,
            explanation = "Menurut BAB 2 Poin 2.1, bel masuk dibunyikan pukul 06.45 dan murid hadir di sekolah 15 menit sebelum bel berbunyi.",
            sourceCitation = "Pedoman Tatib SMANEKA BAB 2 Poin 2.1 (Halaman 1)"
        ),
        QuizQuestionEntity(
            id = "Q2",
            topicId = "TOPIC_1",
            questionType = "MULTIPLE_CHOICE",
            question = "Pukul berapa jam belajar dimulai pada hari Jum'at di SMAN 1 Kepanjen?",
            optionA = "Pukul 06.45 WIB",
            optionB = "Pukul 07.00 WIB",
            optionC = "Pukul 07.15 WIB",
            optionD = "Pukul 07.30 WIB",
            correctOptionIndex = 3,
            explanation = "Menurut BAB 2 Poin 2.3, jam belajar hari Jum'at dimulai pukul 07.30 hingga 14.40 WIB.",
            sourceCitation = "Pedoman Tatib SMANEKA BAB 2 Poin 2.3 (Halaman 1)"
        ),
        QuizQuestionEntity(
            id = "Q3",
            topicId = "TOPIC_2",
            questionType = "MULTIPLE_CHOICE",
            question = "Seragam apa yang dikenakan murid pada hari Kamis di SMAN 1 Kepanjen?",
            optionA = "Seragam Pramuka lengkap",
            optionB = "Baju Batik Sekolah dengan celana/rok putih",
            optionC = "Baju putih lengan panjang dengan celana/rok abu-abu",
            optionD = "Baju olahraga sekolah",
            correctOptionIndex = 1,
            explanation = "Berdasarkan BAB 3 Poin 3.4.A butir d, pada hari Kamis murid mengenakan Baju Batik Sekolah lengan pendek (laki-laki), baju lengan panjang (perempuan), celana/rok putih.",
            sourceCitation = "Pedoman Tatib SMANEKA BAB 3 Poin 3.4.A (Halaman 2)"
        ),
        QuizQuestionEntity(
            id = "Q4",
            topicId = "TOPIC_2",
            questionType = "TRUE_FALSE",
            question = "Siswa diperbolehkan mengenakan sepatu warna hitam dengan model glossy (mengkilap) atau flatshoes pada hari belajar aktif.",
            optionA = "Benar",
            optionB = "Salah",
            optionC = "Hanya pada hari Jumat",
            optionD = "Hanya jika berhalangan",
            correctOptionIndex = 1,
            explanation = "SALAH. BAB 3 Poin 3.5 menyatakan sepatu model standar warna hitam polos atau doff, bukan glossy, serta tidak diperkenankan model flatshoes.",
            sourceCitation = "Pedoman Tatib SMANEKA BAB 3 Poin 3.5.A-B (Halaman 3 & 11)"
        ),
        QuizQuestionEntity(
            id = "Q5",
            topicId = "TOPIC_2",
            questionType = "MULTIPLE_CHOICE",
            question = "Berapa ketentuan panjang kaos kaki yang wajib digunakan dari mata kaki?",
            optionA = "Panjang 5 cm dari mata kaki",
            optionB = "Panjang 10 cm dari mata kaki",
            optionC = "Panjang 15 cm dari mata kaki",
            optionD = "Bebas asalkan tidak terlihat",
            correctOptionIndex = 2,
            explanation = "Berdasarkan BAB 3 Poin 3.5.C, kaos kaki harus polos dengan panjang 15 cm dari mata kaki (Senin-Kamis putih polos, Jumat hitam polos).",
            sourceCitation = "Pedoman Tatib SMANEKA BAB 3 Poin 3.5.C (Halaman 3)"
        ),
        QuizQuestionEntity(
            id = "Q6",
            topicId = "TOPIC_3",
            questionType = "MULTIPLE_CHOICE",
            question = "Berapa ukuran standar potongan model rambut sederhana bagi siswa laki-laki di SMANEKA?",
            optionA = "Ukuran 3-2-1",
            optionB = "Ukuran 2-2-1",
            optionC = "Ukuran 1-1-1 (Gundul)",
            optionD = "Ukuran 4-3-2",
            correctOptionIndex = 1,
            explanation = "Berdasarkan BAB 3 Poin 3.6.B, rambut siswa laki-laki adalah model sederhana dengan ukuran potongan rambut 2-2-1.",
            sourceCitation = "Pedoman Tatib SMANEKA BAB 3 Poin 3.6.B (Halaman 3)"
        ),
        QuizQuestionEntity(
            id = "Q7",
            topicId = "TOPIC_4",
            questionType = "SCENARIO",
            scenario = "Seorang murid telah melakukan Pelanggaran Kategori I (Ringan) lebih dari 5 kali dalam periode tahun ajaran berjalan.",
            question = "Apa langkah penanganan yang diambil pihak sekolah sesuai Standar Operasional Prosedur (SOP)?",
            optionA = "Langsung dikeluarkan dari sekolah secara sepihak",
            optionB = "Diberikan teguran lisan oleh guru piket tanpa catatan",
            optionC = "Guru Wali berkoordinasi dengan Wali Kelas melakukan pemanggilan Orang Tua/Wali didampingi Tim Tatib dan BK",
            optionD = "Membayar denda administrasi kepada Tim Tatib",
            correctOptionIndex = 2,
            explanation = "Sesuai SOP Penanganan Pelanggaran I butir 3: Jika pelanggaran > 5 kali, Guru Wali berkoordinasi dengan Wali Kelas melakukan pemanggilan Orang Tua/Wali Murid didampingi Tim Tatib dan Guru BK.",
            sourceCitation = "SOP Alur Penanganan Pelanggaran Tata Tertib SMAN 1 Kepanjen (Halaman 9)"
        ),
        QuizQuestionEntity(
            id = "Q8",
            topicId = "TOPIC_4",
            questionType = "MULTIPLE_CHOICE",
            question = "Membawa atau merokok vape saat masih mengenakan seragam sekolah termasuk dalam kategori pelanggaran apa?",
            optionA = "Pelanggaran I (Ringan)",
            optionB = "Pelanggaran II (Sedang)",
            optionC = "Pelanggaran III (Berat)",
            optionD = "Bukan merupakan pelanggaran",
            correctOptionIndex = 1,
            explanation = "Berdasarkan Lampiran Bentuk Pelanggaran Bagian B No. 5, membawa atau merokok baik vape saat masih mengenakan seragam sekolah termasuk Pelanggaran II (Sedang).",
            sourceCitation = "Bentuk Pelanggaran B. PELANGGARAN II No. 5 (Halaman 7)"
        ),
        QuizQuestionEntity(
            id = "Q9",
            topicId = "TOPIC_4",
            questionType = "MULTIPLE_CHOICE",
            question = "Siapa saja pihak yang menghadiri Konferensi Kasus Khusus untuk Pelanggaran Kategori III (Berat)?",
            optionA = "Hanya Kepala Sekolah dan Siswa",
            optionB = "Hanya Tim Tatib dan Guru BK",
            optionC = "Kepala Sekolah, Waka Kesiswaan, Tim Tatib, Guru BK, Wali Kelas, Orang Tua/Wali beserta Siswa",
            optionD = "Semua siswa satu angkatan",
            correctOptionIndex = 2,
            explanation = "Sesuai SOP Kategori III: Konferensi Kasus Khusus dihadiri oleh Kepala Sekolah, Waka Kesiswaan, Tim Tatib, Guru BK, Wali Kelas, serta Orang Tua/Wali Murid beserta Siswa yang bersangkutan.",
            sourceCitation = "SOP Kategori Pelanggaran III (Halaman 10)"
        ),
        QuizQuestionEntity(
            id = "Q10",
            topicId = "TOPIC_5",
            questionType = "MULTIPLE_CHOICE",
            question = "Bagaimana ketentuan penyimpanan smartphone/HP murid pada saat KBM berlangsung?",
            optionA = "Bebas diletakkan di atas meja masing-masing siswa",
            optionB = "Disimpan dalam kotak penyimpanan yang disediakan sekolah dan dikunci",
            optionC = "Wajib dititipkan di kantor kepala sekolah",
            optionD = "Boleh dimainkan secara bebas asalkan tanpa suara",
            correctOptionIndex = 1,
            explanation = "Menurut BAB 3 Poin 3.7, penggunaan HP pada saat KBM berlangsung disimpan dalam kotak penyimpanan yang disediakan sekolah dan dikunci. Penggunaan harus seijin guru pengajar.",
            sourceCitation = "Pedoman Tatib SMANEKA BAB 3 Poin 3.7 (Halaman 3)"
        )
    )
}
