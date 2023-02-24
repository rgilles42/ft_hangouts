package fortytwo.rgilles.ft_hangouts.common.broadcast_receivers.sms

sealed class SmsBroadcastEvent {
    data class ReceivedMessage(val messageContent: String, val messageSender: String?): SmsBroadcastEvent()
}