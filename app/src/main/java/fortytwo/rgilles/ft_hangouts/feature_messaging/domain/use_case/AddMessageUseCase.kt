package fortytwo.rgilles.ft_hangouts.feature_messaging.domain.use_case

import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.repository.MessageRepository

class AddMessageUseCase(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(message: Message) {
        repository.insertMessage(message)
    }
}