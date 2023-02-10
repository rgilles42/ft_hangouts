package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case

import fortytwo.rgilles.ft_hangouts.common.domain.relations.ContactWithMessages
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetContactsWithActiveConvsUseCase(
    private val repository: ContactRepository
) {
    operator fun invoke(): Flow<List<ContactWithMessages>> {
        // Order is fixed and set directly in repo implementation: berk
        return repository.getContactsWithExistingConversation().map { ContactWithExistingConvsList ->
            ContactWithExistingConvsList.sortedByDescending { ContactWithMessages ->
                ContactWithMessages.messages
                    .sortedByDescending { message ->
                        message.timestamp
                    }[0].timestamp
            }
        }
    }
}