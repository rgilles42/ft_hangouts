package fortytwo.rgilles.ft_hangouts.feature_messaging.domain.repository

import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    //fun         getMessages(): Flow<List<Message>>
    fun         getMessagesByContact(contactId: Int): Flow<List<Message>>
    suspend fun getMessageById(id: Int): Message?
    suspend fun insertMessage(message: Message)
    suspend fun deleteMessage(message: Message)
}