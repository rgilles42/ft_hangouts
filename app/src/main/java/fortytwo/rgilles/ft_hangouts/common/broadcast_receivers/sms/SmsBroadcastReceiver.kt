package fortytwo.rgilles.ft_hangouts.common.broadcast_receivers.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony.Sms.Intents.getMessagesFromIntent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SmsBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var receiveModel: SmsBroadcastReceiveModel

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) {
            return
        } else {
            var messageContent = ""
            val messages = getMessagesFromIntent(intent)
            val messageSender = messages[0].originatingAddress
            for (message in messages) {
                messageContent += message.messageBody
            }
            receiveModel.onEvent(SmsBroadcastEvent.ReceivedMessage(messageContent, messageSender))
        }
    }
}