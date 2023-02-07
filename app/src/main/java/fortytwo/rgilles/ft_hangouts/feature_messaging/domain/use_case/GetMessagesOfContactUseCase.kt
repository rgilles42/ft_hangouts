package fortytwo.rgilles.ft_hangouts.feature_messaging.domain.use_case

import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMessagesOfContactUseCase(
    private val repository: MessageRepository
) {
    operator fun invoke(contactId: Int): Flow<List<Message>> {
        return repository.getMessagesOfContact(contactId).map { MessagesList ->
            MessagesList.sortedByDescending { message ->
                message.timestamp
            }
        }
    }
}