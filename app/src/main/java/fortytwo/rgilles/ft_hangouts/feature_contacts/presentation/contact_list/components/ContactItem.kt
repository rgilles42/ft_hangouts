package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import java.time.LocalDate

fun formContactName(contact: Contact): String {
    if (contact.firstName.isNotBlank() && contact.lastName.isNotBlank()) {
        return contact.firstName + " " + contact.lastName
    }
    return contact.firstName.ifBlank { contact.lastName }
}

@Composable
@Preview(
    showBackground = true,
    //showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    device = "id:pixel_5",
    apiLevel = 33
)
fun ContactItem(
    modifier: Modifier = Modifier,
    contact: Contact = Contact(
        null,
        "Jean",
        "Roulin",
        "+33696969696",
        "jean.roulin98015@gmail.com",
        LocalDate.parse("2023-10-27")
    )
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            modifier = modifier
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
                modifier = modifier.fillMaxWidth(),
                text = formContactName(contact),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}