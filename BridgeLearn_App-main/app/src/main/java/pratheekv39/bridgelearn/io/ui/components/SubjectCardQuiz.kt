package pratheekv39.bridgelearn.io.ui.screens.quiz

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pratheekv39.bridgelearn.io.R
import pratheekv39.bridgelearn.io.ui.screens.home.Subject
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectCardQuiz(
    subject: Subject,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = subject.drawableResId),
                contentDescription = "${subject.name} Icon",
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp),
                tint = Color.Unspecified
            )
            Text(
                text = subject.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}