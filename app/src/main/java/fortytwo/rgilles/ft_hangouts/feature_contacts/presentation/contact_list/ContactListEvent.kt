package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list

import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.ContactOrder

sealed class ContactListEvent {
    data class Order(val contactOrder: ContactOrder): ContactListEvent()
    data class DeleteContact(val contact: Contact): ContactListEvent()
    object  RestoreContact: ContactListEvent()
    object ToggleOrderSection: ContactListEvent()
}
