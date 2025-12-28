package pratheekv39.bridgelearn.io.data

// 1. Model Data untuk Satu Soal
data class QuizQuestion(
    val id: Int,
    val questionText: String,
    val options: List<String>, // Pilihan jawaban (A, B, C, D)
    val correctAnswerIndex: Int // Kunci jawaban (0=A, 1=B, 2=C, 3=D)
)

// 2. Objek Penyimpan Data Soal
object QuizRepository {

    fun getQuestionsForQuiz(subjectId: String, quizId: Int): List<QuizQuestion> {
        return when (subjectId) {
            "physics" -> getPhysicsQuestions(quizId)
            "chemistry" -> getChemistryQuestions(quizId)
            "biology" -> getBiologyQuestions(quizId)
            "math" -> getMathQuestions(quizId)
            else -> emptyList()
        }
    }

    // ==========================================
    // ⚛️ SOAL FISIKA (PHYSICS)
    // ==========================================
    private fun getPhysicsQuestions(quizId: Int): List<QuizQuestion> {
        return when (quizId) {
            1 -> listOf(
                // --- Quiz 1: Pengukuran (Original Format) ---
                QuizQuestion(
                    id = 1,
                    questionText = "Pengukuran dapat dilakukan ketika seseorang…",
                    options = listOf(
                        "Mengamati bentuk suatu benda",
                        "Membandingkan suatu besaran dengan satuan sejenis",
                        "Menggambar ukuran benda pada kertas",
                        "Menentukan warna benda"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Alat ukur panjang yang memiliki skala terkecil 0,1 cm atau 1 mm adalah…",
                    options = listOf(
                        "Mikrometer sekrup",
                        "Jangka sorong",
                        "Meteran",
                        "Mistar/penggaris"
                    ),
                    correctAnswerIndex = 3 // D
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Fungsi utama jangka sorong adalah untuk mengukur…",
                    options = listOf(
                        "Panjang kertas",
                        "Diameter luar, diameter dalam, dan kedalaman benda",
                        "Luas permukaan benda",
                        "Massa suatu benda"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Alat ukur yang paling tepat digunakan untuk mengukur ketebalan kertas atau plat tipis adalah…",
                    options = listOf(
                        "Mistar",
                        "Meteran",
                        "Mikrometer sekrup",
                        "Jangka sorong"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Meteran atau pita ukur umumnya digunakan untuk mengukur…",
                    options = listOf(
                        "Diameter kawat",
                        "Ketebalan aluminium tipis",
                        "Jarak atau objek yang panjang seperti tanah dan bangunan",
                        "Diameter dalam pipa kecil"
                    ),
                    correctAnswerIndex = 2 // C
                )
            )
            // --- QUIZ 2 SUDAH DIUBAH KE FORMAT VERTIKAL ---
            2 -> listOf(
                QuizQuestion(
                    id = 1,
                    questionText = "Besaran yang dapat diukur dan dinyatakan dengan angka serta satuan disebut…",
                    options = listOf(
                        "Fenomena",
                        "Gejala fisika",
                        "Besaran",
                        "Vektor"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Yang termasuk besaran pokok adalah…",
                    options = listOf(
                        "Luas, volume, kecepatan",
                        "Massa, panjang, waktu",
                        "Gaya, tekanan, percepatan",
                        "Kelajuan, massa jenis, volume"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Besaran yang disusun dari gabungan beberapa besaran pokok disebut…",
                    options = listOf(
                        "Besaran dasar",
                        "Besaran fisika",
                        "Besaran turunan",
                        "Besaran tetap"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Satuan m/s adalah contoh satuan dari…",
                    options = listOf(
                        "Kecepatan",
                        "Waktu",
                        "Massa jenis",
                        "Gaya"
                    ),
                    correctAnswerIndex = 0 // A
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Agar hasil pengukuran akurat, posisi mata harus…",
                    options = listOf(
                        "Dari samping agar lebih jelas",
                        "Sedikit miring agar tidak silau",
                        "Tegak lurus terhadap skala ukur",
                        "Dari atas agar semua angka terlihat"
                    ),
                    correctAnswerIndex = 2 // C
                )
            )
            // --- QUIZ 3 SUDAH DIUBAH KE FORMAT VERTIKAL ---
            3 -> listOf(
                QuizQuestion(
                    id = 1,
                    questionText = "Suhu suatu benda berkaitan dengan…",
                    options = listOf(
                        "Banyaknya kalor yang dimiliki",
                        "Energi kinetik rata-rata partikel",
                        "Banyaknya molekul dalam benda",
                        "Energi potensial benda"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Jika suhu tubuh manusia 37°C, maka dalam skala Fahrenheit adalah…",
                    options = listOf(
                        "70°F",
                        "98,6°F",
                        "100°F",
                        "120°F"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Termometer bekerja berdasarkan prinsip…",
                    options = listOf(
                        "Kalor jenis",
                        "Pemuaian zat cair",
                        "Tekanan udara",
                        "Pendinginan udara"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Titik tetap bawah skala Reamur adalah…",
                    options = listOf(
                        "32°R",
                        "80°R",
                        "0°R",
                        "–273°R"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Jika 176°F berapa dalam °C?",
                    options = listOf(
                        "50°C",
                        "60°C",
                        "70°C",
                        "80°C"
                    ),
                    correctAnswerIndex = 3 // D
                )
            )
            // --- QUIZ 4 SUDAH DIUBAH KE FORMAT VERTIKAL ---
            4 -> listOf(
                QuizQuestion(
                    id = 1,
                    questionText = "Zat padat memiliki sifat…",
                    options = listOf(
                        "Bentuk berubah-ubah",
                        "Volume tetap",
                        "Menyesuaikan bentuk wadah",
                        "Jarak partikel sangat renggang"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Zat cair memiliki sifat berikut, kecuali…",
                    options = listOf(
                        "Mengalir",
                        "Bentuk berubah mengikuti wadah",
                        "Volume tetap",
                        "Volume berubah-ubah"
                    ),
                    correctAnswerIndex = 3 // D
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Zat gas tidak memiliki volume tetap karena…",
                    options = listOf(
                        "Partikelnya diam",
                        "Gaya tarik partikelnya sangat kuat",
                        "Jarak antarpartikel sangat renggang",
                        "Jumlah partikelnya sedikit"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Contoh benda padat adalah…",
                    options = listOf(
                        "Air",
                        "Minyak",
                        "Uap air",
                        "Batu"
                    ),
                    correctAnswerIndex = 3 // D
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Air dalam gelas bentuknya mengikuti gelas karena…",
                    options = listOf(
                        "Volume tidak tetap",
                        "Jarak partikel sangat renggang",
                        "Partikel cair dapat bergerak bebas",
                        "Air tidak memiliki massa"
                    ),
                    correctAnswerIndex = 2 // C
                )
            )
            // --- QUIZ 5 SUDAH DIUBAH KE FORMAT VERTIKAL ---
            5 -> listOf(
                QuizQuestion(
                    id = 1,
                    questionText = "Pemuaian terjadi ketika benda…",
                    options = listOf(
                        "Dipanaskan",
                        "Didiamkan",
                        "Dibekukan",
                        "Dipukul"
                    ),
                    correctAnswerIndex = 0 // A
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Pemuaian panjang terjadi terutama pada…",
                    options = listOf(
                        "Benda bentuk kubus",
                        "Benda tipis seperti kaca",
                        "Benda yang panjangnya dominan",
                        "Benda cair"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Sambungan rel kereta dibuat renggang agar…",
                    options = listOf(
                        "Rel tidak bersuara",
                        "Tidak bengkok saat memuai",
                        "Kereta lebih cepat",
                        "Rel menjadi lebih kuat"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Pemuaian luas terjadi pada benda…",
                    options = listOf(
                        "Sangat tipis seperti pelat",
                        "Sangat panjang seperti kawat",
                        "Cairan dalam panci",
                        "Gas dalam ruangan"
                    ),
                    correctAnswerIndex = 0 // A
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Panci yang dipanaskan mengalami pemuaian volume karena…",
                    options = listOf(
                        "Partikel menyusut",
                        "Partikel diam",
                        "Semua sisi benda memuai",
                        "Panci kehilangan kalor"
                    ),
                    correctAnswerIndex = 2 // C
                )
            )
            else -> emptyList()
        }
    }

    // ==========================================
    // 🧪 SOAL KIMIA (CHEMISTRY)
    // ==========================================
    private fun getChemistryQuestions(quizId: Int): List<QuizQuestion> {
        return when (quizId) {
            1 -> listOf(
                // --- Topik 1: Zat Dan Perubahannya ---
                QuizQuestion(
                    id = 1,
                    questionText = "Perubahan wujud dari gas menjadi cair disebut …",
                    options = listOf(
                        "Mencair",
                        "Mengembun",
                        "Menguap",
                        "Menyublim"
                    ),
                    correctAnswerIndex = 1 // B: Mengembun
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Perubahan yang tidak menghasilkan zat baru adalah …",
                    options = listOf(
                        "Kayu dibakar",
                        "Telur digoreng",
                        "Es mencair",
                        "Besi berkarat"
                    ),
                    correctAnswerIndex = 2 // C: Es mencair
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Yang termasuk perubahan kimia adalah …",
                    options = listOf(
                        "Gula larut",
                        "Kertas diremas",
                        "Kertas dibakar",
                        "Es dihancurkan"
                    ),
                    correctAnswerIndex = 2 // C: Kertas dibakar
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Kapur barus yang lama-kelamaan habis mengalami …",
                    options = listOf(
                        "Menguap",
                        "Menyublim",
                        "Mencair",
                        "Mengembun"
                    ),
                    correctAnswerIndex = 1 // B: Menyublim
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Ciri perubahan kimia ditandai dengan …",
                    options = listOf(
                        "Perubahan warna saja",
                        "Terbentuk zat baru",
                        "Perubahan bentuk",
                        "Perubahan ukuran"
                    ),
                    correctAnswerIndex = 1 // B: Terbentuk zat baru
                )
            )
            2 -> listOf(
                // --- Topik 2: Unsur, Senyawa, Dan Campuran ---
                QuizQuestion(
                    id = 1,
                    questionText = "Zat tunggal yang tidak dapat diuraikan lagi disebut …",
                    options = listOf(
                        "Unsur",
                        "Senyawa",
                        "Campuran homogen",
                        "Campuran heterogen"
                    ),
                    correctAnswerIndex = 0 // A: Unsur
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Air (H2O) masuk kategori …",
                    options = listOf(
                        "Unsur",
                        "Senyawa",
                        "Campuran",
                        "Mineral"
                    ),
                    correctAnswerIndex = 1 // B: Senyawa
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Campuran homogen ditunjukkan oleh …",
                    options = listOf(
                        "Minyak dan air",
                        "Air dan pasir",
                        "Udara",
                        "Bebatuan"
                    ),
                    correctAnswerIndex = 2 // C: Udara
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Garam dapur merupakan contoh …",
                    options = listOf(
                        "Unsur",
                        "Senyawa",
                        "Campuran heterogen",
                        "Campuran homogen"
                    ),
                    correctAnswerIndex = 1 // B: Senyawa
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Campuran yang dapat dilihat bagian-bagiannya adalah …",
                    options = listOf(
                        "Larutan garam",
                        "Udara",
                        "Minyak dan air",
                        "Larutan gula"
                    ),
                    correctAnswerIndex = 2 // C: Minyak dan air
                )
            )
            3 -> listOf(
                // --- Topik 3: Struktur Atom ---
                QuizQuestion(
                    id = 1,
                    questionText = "Partikel yang bermuatan positif adalah …",
                    options = listOf(
                        "Elektron",
                        "Neutron",
                        "Proton",
                        "Ion"
                    ),
                    correctAnswerIndex = 2 // C: Proton
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Elektron berada pada …",
                    options = listOf(
                        "Kulit atom",
                        "Inti atom",
                        "Ruang hampa",
                        "Orbital proton"
                    ),
                    correctAnswerIndex = 0 // A: Kulit atom
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Nomor atom ditentukan oleh jumlah …",
                    options = listOf(
                        "Elektron",
                        "Neutron",
                        "Proton",
                        "Partikel netral"
                    ),
                    correctAnswerIndex = 2 // C: Proton
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Partikel yang tidak bermuatan listrik adalah …",
                    options = listOf(
                        "Proton",
                        "Elektron",
                        "Neutron",
                        "Ion"
                    ),
                    correctAnswerIndex = 2 // C: Neutron
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Proton dan neutron berada di bagian atom yang disebut …",
                    options = listOf(
                        "Kulit",
                        "Tingkat energi",
                        "Inti atom",
                        "Orbit luar"
                    ),
                    correctAnswerIndex = 2 // C: Inti atom
                )
            )
            4 -> listOf(
                // --- Topik 4: Ikatan Kimia ---
                QuizQuestion(
                    id = 1,
                    questionText = "Ikatan ion terjadi karena …",
                    options = listOf(
                        "Saling berbagi elektron",
                        "Perpindahan elektron",
                        "Membentuk molekul",
                        "Penggabungan inti atom"
                    ),
                    correctAnswerIndex = 1 // B: Perpindahan elektron
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Ikatan kovalen terjadi ketika atom …",
                    options = listOf(
                        "Melepas elektron",
                        "Menerima elektron",
                        "Berbagi elektron",
                        "Kehilangan neutron"
                    ),
                    correctAnswerIndex = 2 // C: Berbagi elektron
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "NaCl adalah contoh ikatan …",
                    options = listOf(
                        "Logam",
                        "Kovalen",
                        "Ion",
                        "Hidrogen"
                    ),
                    correctAnswerIndex = 2 // C: Ion
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Molekul air (H2O) terbentuk akibat ikatan …",
                    options = listOf(
                        "Ion",
                        "Kovalen",
                        "Logam",
                        "Nuklir"
                    ),
                    correctAnswerIndex = 1 // B: Kovalen
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Ikatan yang umum terjadi antara logam dan nonlogam adalah …",
                    options = listOf(
                        "Hidrogen",
                        "Kovalen",
                        "Ion",
                        "Metalik"
                    ),
                    correctAnswerIndex = 2 // C: Ion
                )
            )
            5 -> listOf(
                // --- Topik 5: Larutan Asam, Basa, Dan Garam ---
                QuizQuestion(
                    id = 1,
                    questionText = "Zat dengan pH < 7$ termasuk …",
                    options = listOf(
                        "Garam",
                        "Basa",
                        "Asam",
                        "Netral"
                    ),
                    correctAnswerIndex = 2 // C: Asam
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Lakmus merah menjadi biru menunjukkan zat tersebut adalah …",
                    options = listOf(
                        "Asam",
                        "Basa",
                        "Netral",
                        "Garam"
                    ),
                    correctAnswerIndex = 1 // B: Basa
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Reaksi antara asam dan basa menghasilkan …",
                    options = listOf(
                        "Asam",
                        "Garam",
                        "Basa",
                        "Logam"
                    ),
                    correctAnswerIndex = 1 // B: Garam
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Contoh zat bersifat asam adalah …",
                    options = listOf(
                        "Sabun",
                        "Air kapur",
                        "Cuka",
                        "Baking soda"
                    ),
                    correctAnswerIndex = 2 // C: Cuka
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Zat bersifat netral memiliki pH$ …",
                    options = listOf(
                        "1",
                        "3",
                        "7",
                        "12"
                    ),
                    correctAnswerIndex = 2 // C: 7
                )
            )
            else -> emptyList()
        }
    }

    // ==========================================
    // 🧬 SOAL BIOLOGI (BIOLOGY)
    // ==========================================
    private fun getBiologyQuestions(quizId: Int): List<QuizQuestion> {
        return when (quizId) {
            1 -> listOf(
                // --- Topik 1: Klasifikasi Makhluk Hidup ---
                QuizQuestion(
                    id = 1,
                    questionText = "Tingkatan klasifikasi tertinggi adalah …",
                    options = listOf("Genus", "Spesies", "Kingdom", "Kelas"),
                    correctAnswerIndex = 2 // C: Kingdom
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Makhluk hidup dikelompokkan berdasarkan …",
                    options = listOf("Warna saja", "Persamaan & perbedaan ciri", "Sifat fisik saja", "Ukuran tubuh saja"),
                    correctAnswerIndex = 1 // B: Persamaan & perbedaan ciri
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Manusia termasuk kingdom …",
                    options = listOf("Plantae", "Animalia", "Protista", "Fungi"),
                    correctAnswerIndex = 1 // B: Animalia
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Nama ilmiah terdiri dari …",
                    options = listOf("Kelas & ordo", "Genus & spesies", "Kingdom & filum", "Famili & genus"),
                    correctAnswerIndex = 1 // B: Genus & spesies
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Berikut contoh organisme uniseluler adalah …",
                    options = listOf("Manusia", "Jamur kuping", "Bakteri", "Burung"),
                    correctAnswerIndex = 2 // C: Bakteri
                )
            )
            2 -> listOf(
                // --- Topik 2: Ciri-ciri makhluk hidup ---
                QuizQuestion(
                    id = 1,
                    questionText = "Kemampuan makhluk hidup merespons cahaya merupakan ciri …",
                    options = listOf("Bernapas", "Peka terhadap rangsang", "Tumbuh", "Bereproduksi"),
                    correctAnswerIndex = 1 // B: Peka terhadap rangsang
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Berikut merupakan contoh ekskresi …",
                    options = listOf("Tumbuh tinggi", "Makan", "Mengeluarkan keringat", "Berjalan"),
                    correctAnswerIndex = 2 // C: Mengeluarkan keringat
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Tujuan makhluk hidup berkembang biak adalah …",
                    options = listOf("Memperbanyak makanan", "Menambah energi", "Menjaga kelestarian spesies", "Mengurangi populasi"),
                    correctAnswerIndex = 2 // C: Menjaga kelestarian spesies
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Tumbuhan bergerak dengan cara …",
                    options = listOf("Menggunakan kaki", "Membelokkan batang ke arah cahaya", "Terbang", "Berjalan"),
                    correctAnswerIndex = 1 // B: Membelokkan batang ke arah cahaya
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Pertambahan ukuran tubuh menunjukkan ciri …",
                    options = listOf("Bernapas", "Tumbuh", "Bergerak", "Ekskresi"),
                    correctAnswerIndex = 1 // B: Tumbuh
                )
            )
            3 -> listOf(
                // --- Topik 3: Sel sebagai Unit Struktural ---
                QuizQuestion(
                    id = 1,
                    questionText = "Unit terkecil penyusun makhluk hidup adalah …",
                    options = listOf("Jaringan", "Organ", "Sel", "Sistem organ"),
                    correctAnswerIndex = 2 // C: Sel
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Organel tempat fotosintesis adalah …",
                    options = listOf("Inti sel", "Kloroplas", "Mitokondria", "Ribosom"),
                    correctAnswerIndex = 1 // B: Kloroplas
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Organel yang berfungsi sebagai pusat pengatur kegiatan sel adalah …",
                    options = listOf("Sitoplasma", "Mitokondria", "Membran sel", "Inti sel"),
                    correctAnswerIndex = 3 // D: Inti sel
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Bagian sel tumbuhan yang tidak dimiliki sel hewan adalah …",
                    options = listOf("Membran sel", "Sitoplasma", "Dinding sel", "Mitokondria"),
                    correctAnswerIndex = 2 // C: Dinding sel
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Organel pembuat energi adalah …",
                    options = listOf("Ribosom", "Mitokondria", "Kloroplas", "Vakuola"),
                    correctAnswerIndex = 1 // B: Mitokondria
                )
            )
            4 -> listOf(
                // --- Topik 4: Jaringan pada hewan dan tumbuhan ---
                QuizQuestion(
                    id = 1,
                    questionText = "Jaringan yang mengangkut air pada tumbuhan adalah …",
                    options = listOf("Floem", "Parenkim", "Xilem", "Epidermis"),
                    correctAnswerIndex = 2 // C: Xilem
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Jaringan meristem berada pada …",
                    options = listOf("Daun", "Akar dan pucuk batang", "Bunga", "Buah"),
                    correctAnswerIndex = 1 // B: Akar dan pucuk batang
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Jaringan epitel pada hewan berfungsi sebagai …",
                    options = listOf("Penggerak", "Pelindung", "Penyimpanan", "Transportasi"),
                    correctAnswerIndex = 1 // B: Pelindung
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Jaringan saraf berfungsi untuk …",
                    options = listOf("Membawa air", "Menyimpan makanan", "Menghantarkan rangsang", "Menguatkan batang"),
                    correctAnswerIndex = 2 // C: Menghantarkan rangsang
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Floem mengangkut …",
                    options = listOf("Air dan mineral", "Hasil fotosintesis", "Oksigen", "Hormon"),
                    correctAnswerIndex = 1 // B: Hasil fotosintesis
                )
            )
            5 -> listOf(
                // --- Topik 5: Ekosistem dan Interaksi Makhluk Hidup ---
                QuizQuestion(
                    id = 1,
                    questionText = "Komponen yang tidak hidup dalam ekosistem disebut …",
                    options = listOf("Biotik", "Abiotik", "Produsen", "Konsumen"),
                    correctAnswerIndex = 1 // B: Abiotik
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Contoh hubungan mutualisme adalah …",
                    options = listOf("Serigala–domba", "Kutu–kucing", "Bunga–lebah", "Harimau–rusa"),
                    correctAnswerIndex = 2 // C: Bunga–lebah
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Organisme yang menghasilkan makanan sendiri disebut …",
                    options = listOf("Konsumen", "Produsen", "Pengurai", "Parasit"),
                    correctAnswerIndex = 1 // B: Produsen
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Dalam rantai makanan, hewan pemakan tumbuhan adalah …",
                    options = listOf("Konsumen I", "Konsumen II", "Produsen", "Pengurai"),
                    correctAnswerIndex = 0 // A: Konsumen I
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Peran bakteri pengurai adalah …",
                    options = listOf("Memburu mangsa", "Membuat makanan", "Menguraikan sisa makhluk hidup", "Memakan tumbuhan"),
                    correctAnswerIndex = 2 // C: Menguraikan sisa makhluk hidup
                )
            )
            else -> emptyList()
        }
    }

    // ==========================================
    // ➕ SOAL MATEMATIKA (MATH)
    // ==========================================
    private fun getMathQuestions(quizId: Int): List<QuizQuestion> {
        return when (quizId) {
            // --- Quiz 1: Perbandingan ---
            1 -> listOf(
                QuizQuestion(
                    id = 1,
                    questionText = "Umur A 30 tahun dan umur B 24 tahun. Perbandingan umur A : B adalah …",
                    options = listOf(
                        "5 : 4", // A
                        "6 : 5",
                        "3 : 2",
                        "4 : 3"
                    ),
                    correctAnswerIndex = 0 // A
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Harga 6 buku Rp 30.000. Jika harganya berbanding lurus dengan jumlah buku, harga 10 buku adalah …",
                    options = listOf(
                        "45.000",
                        "50.000", // B
                        "55.000",
                        "60.000"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "8 pekerja dapat menyelesaikan pekerjaan dalam 12 hari. Jika jumlah pekerja ditambah menjadi 16, lama waktu penyelesaian adalah …",
                    options = listOf(
                        "24 hari",
                        "6 hari", // B
                        "3 hari",
                        "12 hari"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Uang Rini Rp 24.000 dan uang Rena Rp 18.000. Selisih uang mereka adalah …",
                    options = listOf(
                        "4.000",
                        "5.000",
                        "6.000", // C
                        "8.000"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Berat 3 karung beras 45 kg. Jika berbanding lurus, berat 5 karung adalah …",
                    options = listOf(
                        "60 kg",
                        "70 kg",
                        "75 kg", // C
                        "90 kg"
                    ),
                    correctAnswerIndex = 2 // C
                )
            )

            // --- Quiz 2: Aritmetika Sosial ---
            2 -> listOf(
                QuizQuestion(
                    id = 1,
                    questionText = "Harga 1 lusin pensil Rp 36.000. Harga 1 pensil adalah …",
                    options = listOf(
                        "2.000",
                        "2.500",
                        "3.000", // C
                        "3.500"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Pak Dodi membeli barang seharga Rp 150.000 lalu menjual seharga Rp 180.000. Keuntungan yang diperoleh adalah …",
                    options = listOf(
                        "20.000",
                        "25.000",
                        "30.000", // C
                        "35.000"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Harga pembelian suatu barang Rp 80.000 dan rugi 25%. Harga jual barang tersebut adalah …",
                    options = listOf(
                        "50.000",
                        "55.000",
                        "60.000", // C
                        "65.000"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Sebuah tas harga awalnya Rp 200.000 diberi diskon 20%. Harga setelah diskon adalah …",
                    options = listOf(
                        "140.000",
                        "150.000",
                        "160.000", // C
                        "180.000"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Ani membeli permen Rp 400 dan menjual Rp 520. Persentase untung adalah …",
                    options = listOf(
                        "20%",
                        "25%",
                        "30%", // C
                        "40%"
                    ),
                    correctAnswerIndex = 2 // C
                )
            )

            // --- Quiz 3: Garis dan Sudut ---
            3 -> listOf(
                QuizQuestion(
                    id = 1,
                    questionText = "Dua garis dikatakan sejajar apabila …",
                    options = listOf(
                        "Berpotongan di satu titik",
                        "Berimpit",
                        "Tidak memiliki titik persekutuan", // C
                        "Memiliki dua titik persekutuan"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Besar sudut lurus adalah …",
                    options = listOf(
                        "45°",
                        "90°",
                        "120°",
                        "180°" // D
                    ),
                    correctAnswerIndex = 3 // D
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Jika ∠A dan ∠B berjumlah 90°, maka kedua sudut tersebut adalah …",
                    options = listOf(
                        "Bertolak belakang",
                        "Berpelurus",
                        "Berpenyiku", // C
                        "Bersisian"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Sudut luar berseberangan pada dua garis sejajar memiliki sifat …",
                    options = listOf(
                        "Jumlahnya 180°",
                        "Besarnya sama", // B
                        "Selalu bertemu di satu titik",
                        "Keduanya sudut lancip"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Sudut 135° termasuk jenis sudut …",
                    options = listOf(
                        "Lancip",
                        "Siku-siku",
                        "Tumpul", // C
                        "Refleks"
                    ),
                    correctAnswerIndex = 2 // C
                )
            )

            // --- Quiz 4: Segiempat dan Segitiga ---
            4 -> listOf(
                QuizQuestion(
                    id = 1,
                    questionText = "Segitiga memiliki jumlah besar sudut …",
                    options = listOf(
                        "90°",
                        "120°",
                        "180°", // C
                        "360°"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Segitiga yang ketiga sisinya berbeda panjang adalah …",
                    options = listOf(
                        "Sama sisi",
                        "Sama kaki",
                        "Sebarang", // C
                        "Siku-siku"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Luas jajar genjang dengan alas 12 cm dan tinggi 8 cm adalah …",
                    options = listOf(
                        "40 cm²",
                        "64 cm²",
                        "80 cm²",
                        "96 cm²" // D
                    ),
                    correctAnswerIndex = 3 // D
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Persegi panjang dengan panjang 10 cm dan lebar 6 cm memiliki keliling …",
                    options = listOf(
                        "16 cm",
                        "20 cm",
                        "26 cm",
                        "32 cm" // D
                    ),
                    correctAnswerIndex = 3 // D
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Luas belah ketupat dengan diagonal 1 = 12 cm dan diagonal 2 = 10 cm adalah …",
                    options = listOf(
                        "40 cm²",
                        "50 cm²",
                        "60 cm²", // C
                        "70 cm²"
                    ),
                    correctAnswerIndex = 2 // C
                )
            )

            // --- Quiz 5: Penyajian Data ---
            5 -> listOf(
                QuizQuestion(
                    id = 1,
                    questionText = "Data tinggi badan siswa yang diukur langsung termasuk …",
                    options = listOf(
                        "Data sekunder",
                        "Data kuantitatif",
                        "Data primer", // C
                        "Data kualitatif"
                    ),
                    correctAnswerIndex = 2 // C
                ),
                QuizQuestion(
                    id = 2,
                    questionText = "Golongan darah siswa termasuk jenis data …",
                    options = listOf(
                        "Kuantitatif",
                        "Kualitatif", // B
                        "Primer",
                        "Sekunder"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 3,
                    questionText = "Diagram yang cocok untuk perkembangan data tiap tahun adalah …",
                    options = listOf(
                        "Diagram batang",
                        "Diagram garis", // B
                        "Diagram lingkaran",
                        "Tabel kontingensi"
                    ),
                    correctAnswerIndex = 1 // B
                ),
                QuizQuestion(
                    id = 4,
                    questionText = "Tabel yang menyajikan dua variabel (misal: kelas dan jenis kelamin) disebut …",
                    options = listOf(
                        "Tabel garis",
                        "Tabel batang",
                        "Tabel distribusi frekuensi",
                        "Tabel kontingensi" // D
                    ),
                    correctAnswerIndex = 3 // D
                ),
                QuizQuestion(
                    id = 5,
                    questionText = "Sektor 120° pada diagram lingkaran menyatakan 120/360 bagian dari total data, yaitu …",
                    options = listOf(
                        "1/2",
                        "1/3", // B
                        "1/4",
                        "1/6"
                    ),
                    correctAnswerIndex = 1 // B
                )
            )
            else -> emptyList()
        }
    }

}
