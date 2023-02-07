package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case

import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow

class GetContactsWithActiveConvsUseCase(
    private val repository: ContactRepository
) {
    operator fun invoke(): Flow<List<Contact>> {
        // Order is fixed and set directly in repo implementation: berk
        return repository.getContactsWithExistingConversation()
    }
}