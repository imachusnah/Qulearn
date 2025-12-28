package pratheekv39.bridgelearn.io.data

import pratheekv39.bridgelearn.io.R

data class Topic(
    val id: Int,
    val subjectId: Int,
    val title: String,
    val description: String,
    val imageRes: Int
)

object TopicData {

    private val topics = listOf(
        // ---------------- PHYSICS = subjectId 1 ----------------
        Topic(
            id = 1,
            subjectId = 1,
            title = "Alat ukur panjang",
            description = "",
            imageRes = R.drawable.fisika1
        ),
        Topic(
            id = 2,
            subjectId = 1,
            title = "Besaran pokok dan Besaran turunan",
            description = "",
            imageRes = R.drawable.fisika2
        ),

        // ---------------- CHEMISTRY = subjectId 2 ----------------
        Topic(
            id = 3,
            subjectId = 2,
            title = "Perubahan zat",
            description = "",
            imageRes = R.drawable.kimia3
        ),
        Topic(
            id = 4,
            subjectId = 2,
            title = "Klasifikasi Mahluk hidup",
            description = "",
            imageRes = R.drawable.biologi1
        ),

        // ---------------- BIOLOGY = subjectId 3 ----------------
        Topic(
            id = 5,
            subjectId = 3,
            title = "Aritmatika sosial",
            description = "",
            imageRes = R.drawable.math1
        ),
        Topic(
            id = 6,
            subjectId = 3,
            title = "Ciri-ciri makhluk hidup",
            description = "",
            imageRes = R.drawable.biologi2
        ),

        // ---------------- MATHEMATICS = subjectId 4 ----------------
        Topic(
            id = 7,
            subjectId = 4,
            title = "Perbandingan",
            description = "",
            imageRes = R.drawable.math1
        ),
        Topic(
            id = 8,
            subjectId = 4,
            title = "Aritmatika sosial",
            description = "",
            imageRes = R.drawable.math1
        ),
    )

    fun getTopicsBySubjectId(subjectId: Int): List<Topic> =
        topics.filter { it.subjectId == subjectId }

    fun findTopicById(id: Int): Topic? =
        topics.find { it.id == id }
}
