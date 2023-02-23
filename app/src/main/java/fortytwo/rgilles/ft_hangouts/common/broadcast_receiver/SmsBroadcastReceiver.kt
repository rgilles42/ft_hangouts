package fortytwo.rgilles.ft_hangouts.common.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony.Sms.Intents.getMessagesFromIntent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SmsBroadcastReceiver : BroadcastReceiver() {
    lateinit var receiveModel: SmsBroadcastReceiveModel

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("DWEBUG", "SMS received")
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