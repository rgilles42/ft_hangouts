package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.util.Screen

fun formContactName(contact: Contact): String {
    if (contact.firstName.isNotBlank() && contact.lastName.isNotBlank()) {
        return contact.firstName + " " + contact.lastName
    }
    return contact.firstName.ifBlank { contact.lastName }
}

@Composable
fun ContactItem(
    modifier: Modifier = Modifier,
    contact: Contact,
    navController: NavController
) {
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
                    modifier = modifier.fillMaxSize()
                        .padding(5.dp),
                    imageVector = Icons.Default.PersonOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )

            }
            Spacer(modifier = modifier.width(30.dp))
            Text(
                modifier = modifier,
                text = formContactName(contact),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = modifier.weight(1f))
            if (contact.phoneNumber.isNotBlank()) {
                Icon(
                    modifier = modifier
                        .padding(5.dp)
                        .fillMaxHeight()
                        .clickable {
                            navController.navigate(
                                Screen.ConversationChatScreen.route +
                                        "?contactId=${contact.id}"
                            )
                        },
                    imageVector = Icons.Outlined.Message,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (contact.email.isNotBlank()) {
                Icon(
                    modifier = modifier
                        .padding(5.dp)
                        .fillMaxHeight()
                        .clickable {
                            //TODO
                        },
                    imageVector = Icons.Outlined.Email,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}