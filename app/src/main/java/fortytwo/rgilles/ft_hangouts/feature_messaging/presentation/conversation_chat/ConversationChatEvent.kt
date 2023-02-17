package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_chat

sealed class ConversationChatEvent {
    data class TypedMessage(val value: String): ConversationChatEvent()
    object SendMessage: ConversationChatEvent()
}