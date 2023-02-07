package fortytwo.rgilles.ft_hangouts.feature_messaging.domain.use_case

data class MessageUseCases(
    val getMessagesOfContact: GetMessagesOfContactUseCase,
    val getMessage: GetMessageUseCase,
    val deleteMessage: DeleteMessageUseCase,
    val addMessage: AddMessageUseCase
)
