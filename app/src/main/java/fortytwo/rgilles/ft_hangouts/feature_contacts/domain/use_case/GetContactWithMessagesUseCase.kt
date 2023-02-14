package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case

import fortytwo.rgilles.ft_hangouts.common.domain.relations.ContactWithMessages
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetContactWithMessagesUseCase(
    private val repository: ContactRepository
) {
    operator fun invoke(id: Int): Flow<ContactWithMessages> {

        return repository.getContactWithMessages(id).map { ContactWithMessages ->
            ContactWithMessages(
                contact = ContactWithMessages.contact,
                messages = ContactWithMessages.messages.sortedByDescending { message ->
                    message.timestamp
                }
            )
        }
    }
}