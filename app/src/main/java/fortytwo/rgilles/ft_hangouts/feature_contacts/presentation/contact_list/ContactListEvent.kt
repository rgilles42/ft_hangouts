package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list

import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.ContactOrder

sealed class ContactListEvent {
    data class Order(val contactOrder: ContactOrder): ContactListEvent()
    object ToggleOrderSection: ContactListEvent()
}
