@file:OptIn(ExperimentalMaterial3Api::class)

package pratheekv39.bridgelearn.io.ui.screens.topic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pratheekv39.bridgelearn.io.data.TopicData

@Composable
fun TopicDetailScreen(
    navController: NavController,
    topicId: Int
) {
    val topic = TopicData.findTopicById(topicId)

    if (topic == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Topik tidak ditemukan.")
        }
        return
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text(topic.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0) // ⭐ Hilangkan padding scaffold
    ) { _ ->

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {

            // ⭐ FULLSCREEN WIDTH IMAGE TANPA PADDING
            Image(
                painter = painterResource(id = topic.imageRes),
                contentDescription = topic.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth // Gambar panjang tetap full
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = topic.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = topic.description,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}
