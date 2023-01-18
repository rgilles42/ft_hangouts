package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list

import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.ContactOrder
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.OrderType

data class ContactListState(
    val contacts: List<Contact> = emptyList(),
    val contactOrder: ContactOrder = ContactOrder.LastName(OrderType.Ascending),
    val isOrderSelectorDisplayed: Boolean = false
)
