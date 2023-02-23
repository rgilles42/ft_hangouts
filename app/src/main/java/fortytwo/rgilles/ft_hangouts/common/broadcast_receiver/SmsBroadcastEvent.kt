package fortytwo.rgilles.ft_hangouts.common.broadcast_receiver

sealed class SmsBroadcastEvent {
    data class ReceivedMessage(val messageContent: String, val messageSender: String?): SmsBroadcastEvent()
}