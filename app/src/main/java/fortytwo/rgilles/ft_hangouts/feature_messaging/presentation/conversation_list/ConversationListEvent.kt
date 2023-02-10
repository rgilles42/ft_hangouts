package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_list

import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message

sealed class ConversationListEvent {
    data class ReceivedMessage(val message: Message): ConversationListEvent()
}