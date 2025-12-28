package pratheekv39.bridgelearn.io.ui.screens.quiz



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pratheekv39.bridgelearn.io.R
import pratheekv39.bridgelearn.io.ui.screens.home.Subject

@Composable
fun QuizScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val subjectList = listOf(
        Subject(
            id = "physics",
            name = "Physics",
            description = "",
            drawableResId = R.drawable.magnet_straight
        ),
        Subject(id = "chemistry",
            name = "Chemistry",
            description = "",
            drawableResId = R.drawable.flask),
        Subject(
            id = "biology",
            name = "Biology",
            description = "",
            drawableResId = R.drawable.flower
        ),
        Subject(
            id = "math",
            name = "Math",
            description = "",
            drawableResId = R.drawable.math_operations
        )
    )

    val colors = listOf(
        Color(0xFF694D72),
        Color(0xFF694D72),
        Color(0xFF694D72),
        Color(0xFF694D72)
    )

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {

        Text(
            text = "QUIZ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Choose a Subject",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(subjectList) { index, subject ->

                val color = colors[index % colors.size]

                SubjectCardQuiz(
                    subject = subject,
                    backgroundColor = color,
                    onClick = {
                        navController.navigate("quiz_list/${subject.id}")
                    }
                )
            }
        }
    }
}

