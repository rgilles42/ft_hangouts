package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.ContactOrder
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.OrderType

@Composable
fun OrderSelector(
    modifier: Modifier = Modifier,
    contactOrder: ContactOrder = ContactOrder.LastName(OrderType.Ascending),
    onOrderChange: (ContactOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Last Name",
                selected = contactOrder is ContactOrder.LastName,
                onSelect = { onOrderChange(ContactOrder.LastName(contactOrder.orderType)) }
            )
            Spacer(modifier = modifier.width(8.dp))
            DefaultRadioButton(
                text = "First Name",
                selected = contactOrder is ContactOrder.FirstName,
                onSelect = { onOrderChange(ContactOrder.FirstName(contactOrder.orderType)) }
            )
        }
        Spacer(modifier = modifier.height(16.dp))
        Row(modifier = modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                selected = contactOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(contactOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = contactOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(contactOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}