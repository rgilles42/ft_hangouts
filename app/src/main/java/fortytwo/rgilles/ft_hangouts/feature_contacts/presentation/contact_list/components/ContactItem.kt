package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import java.time.LocalDate

@Composable
@Preview
fun ContactItem(
    modifier: Modifier = Modifier,
    contact: Contact = Contact(
        null,
        "Jean",
        "Roulin",
        "+33696969696",
        "jean.roulin98015@gmail.com",
        LocalDate.parse("2023-10-27")
    ),
//    onClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .height(20.dp)
    )
}