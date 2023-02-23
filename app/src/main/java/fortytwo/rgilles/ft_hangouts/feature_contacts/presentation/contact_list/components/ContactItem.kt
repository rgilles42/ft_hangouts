package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Message
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
import androidx.navigation.NavController
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.util.Screen
import java.io.File

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
                if (contact.picturePath.isNotBlank() && File(context.filesDir, contact.picturePath).exists()) {
                    val source = ImageDecoder.createSource(File(context.filesDir, contact.picturePath))
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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                modifier = Modifier,
                text = formContactName(contact),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            if (contact.email.isNotBlank()) {
                IconButton(onClick = {
                    //TODO
                }) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp),
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            if (contact.phoneNumber.isNotBlank()) {
                IconButton(onClick = {
                    navController.navigate(
                        Screen.ConversationChatScreen.route +
                                "?contactId=${contact.id}"
                    )
                }) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp),
                        imageVector = Icons.Outlined.Message,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(0.8f)
                )
            }
        }
    }
}