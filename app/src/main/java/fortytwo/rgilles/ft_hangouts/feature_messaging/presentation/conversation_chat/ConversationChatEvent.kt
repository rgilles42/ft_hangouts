package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_chat

import android.content.Context

sealed class ConversationChatEvent {
    data class TypedMessage(val value: String): ConversationChatEvent()
    data class SendMessage(val context: Context): ConversationChatEvent()
}