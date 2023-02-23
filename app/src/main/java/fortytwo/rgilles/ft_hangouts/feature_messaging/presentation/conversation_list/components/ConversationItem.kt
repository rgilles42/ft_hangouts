package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_list.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fortytwo.rgilles.ft_hangouts.common.domain.relations.ContactWithMessages
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.components.formContactName
import java.io.File

@Composable
fun ConversationItem(
    modifier: Modifier = Modifier,
    contactWithMessages: ContactWithMessages
){
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxHeight()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                Modifier.size(50.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors()
            ) {
                if (contactWithMessages.contact.picturePath.isNotBlank() && File(context.filesDir, contactWithMessages.contact.picturePath).exists()) {
                    val source = ImageDecoder.createSource(File(context.filesDir, contactWithMessages.contact.picturePath))
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                    bitmap.value?.let {  btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                } else {
                    Icon(
                        modifier = Modifier.fillMaxSize()
                            .padding(5.dp),
                        imageVector = Icons.Default.PersonOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            Spacer(modifier = Modifier.width(30.dp))
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formContactName(contactWithMessages.contact),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formMessageText(contactWithMessages),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

fun formMessageText(contactWithMessages: ContactWithMessages): String {
    var res = ""
    if (!contactWithMessages.messages[0].isIncoming) {
        res = "You: "
    }
    res += contactWithMessages.messages[0].content
    return res
}
