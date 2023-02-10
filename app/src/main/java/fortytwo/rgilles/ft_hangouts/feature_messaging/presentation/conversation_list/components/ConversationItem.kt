package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fortytwo.rgilles.ft_hangouts.common.domain.relations.ContactWithMessages
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.components.formContactName
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.TransmissionStatus

@Preview
@Composable
fun ConversationItem(
    modifier: Modifier = Modifier,
    contactWithMessages: ContactWithMessages
        = ContactWithMessages(
            Contact(
                null,
                "Jean",
                "Roulin",
                "+33696969696",
                "jean.roulin98015@gmail.com"
            ),
            listOf(
                Message(
                    id = null,
                    recipientPhoneNumber = "+33696969696",
                    recipientId = null,
                    content = "Salut à toi camarade Jean Roulin",
                    isIncoming = false,
                    hasTransmitted = TransmissionStatus.RECEIVED,
                    timestamp = System.currentTimeMillis()
                )
            )
        )
){
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
                modifier.size(50.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors()
            ) {
                //TODO: if contact has picture, else
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    imageVector = Icons.Default.PersonOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )

            }
            Spacer(modifier = modifier.width(30.dp))
            Column {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = formContactName(contactWithMessages.contact),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    modifier = modifier.fillMaxWidth(),
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
