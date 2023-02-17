package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message

@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    message: Message
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isIncoming) Alignment.Start else Alignment.End
    ) {
        Box(
            modifier = modifier
                .background(
                    color = if (message.isIncoming) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small
                )
                .padding(16.dp, 8.dp)
        ) {
            Text(
                text = message.content,
                color = if (message.isIncoming) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = getFormattedDate(message.timestamp),
            fontSize = 12.sp,
            modifier = modifier.padding(start = 8.dp)
        )
    }
}

fun getFormattedDate(timestamp : Long): String {
    return timestamp.toString()
}