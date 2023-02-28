package fortytwo.rgilles.ft_hangouts.feature_messaging.domain.use_case

import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.InvalidMessageException
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.repository.MessageRepository

class AddMessageUseCase(
    private val repository: MessageRepository
) {
    @Throws(InvalidMessageException::class)
    suspend operator fun invoke(message: Message) {
        if (message.content.isBlank()){
            throw InvalidMessageException("EXCEPTION_MESSAGE_EMPTY")
        }
        repository.insertMessage(message)
    }
}