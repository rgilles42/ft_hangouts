package fortytwo.rgilles.ft_hangouts.feature_messaging.data.repository

import fortytwo.rgilles.ft_hangouts.feature_messaging.data.data_source.MessageDao
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessageRepositoryImpl(
    private val dao: MessageDao
): MessageRepository {
    override fun getMessagesOfContact(contactId: Int): Flow<List<Message>> {
        return dao.getContactWithMessagesByContactId(contactId).map { ContactWithMessages ->
            ContactWithMessages.messages
        }
    }

    override suspend fun getMessageById(id: Int): Message? {
        return dao.getMessageById(id)
    }

    override suspend fun insertMessage(message: Message) {
        return dao.insertMessage(message)
    }

    override suspend fun deleteMessage(message: Message) {
        return dao.deleteMessage(message)
    }
}