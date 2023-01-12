package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case

import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.repository.ContactRepository
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.ContactOrder
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetContactsUseCase(
    private val repository: ContactRepository
) {
    operator fun invoke(contactOrder: ContactOrder = ContactOrder.LastName(OrderType.Ascending)): Flow<List<Contact>> {
        return repository.getContacts().map { contacts ->
            when(contactOrder.orderType) {
                is OrderType.Ascending -> {
                    when(contactOrder) {
                        is ContactOrder.LastName -> contacts.sortedBy {it.lastName.lowercase()}
                        is ContactOrder.FirstName -> contacts.sortedBy {it.firstName.lowercase()}
                    }
                }
                is OrderType.Descending -> {
                    when(contactOrder) {
                        is ContactOrder.LastName -> contacts.sortedByDescending {it.lastName.lowercase()}
                        is ContactOrder.FirstName -> contacts.sortedByDescending {it.firstName.lowercase()}
                    }
                }
            }
        }
    }
}