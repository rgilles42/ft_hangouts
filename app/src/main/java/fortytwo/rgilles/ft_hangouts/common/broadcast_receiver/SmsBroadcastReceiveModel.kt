package fortytwo.rgilles.ft_hangouts.common.broadcast_receiver

import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.ContactUseCases
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.TransmissionStatus
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.use_case.MessageUseCases
import kotlinx.coroutines.*
import javax.inject.Inject

class SmsBroadcastReceiveModel @Inject constructor(
    private val contactUseCases: ContactUseCases,
    private val messagesUseCases: MessageUseCases
) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun onEvent(event: SmsBroadcastEvent) {
        var contactList : List<Contact>
        when (event) {
            is SmsBroadcastEvent.ReceivedMessage -> {
                if (event.messageSender == null) { return }
                coroutineScope.launch {
                    contactList = contactUseCases.getInstantaneousContacts()
                    if (!contactList.any {  it.phoneNumber == event.messageSender }) {
                        contactUseCases.addContact(
                            Contact(
                                firstName = event.messageSender,
                                lastName = "",
                                phoneNumber = event.messageSender,
                                email = "",
                                picturePath = "",
                                id = null
                            )
                        )
                        contactList = contactUseCases.getInstantaneousContacts()
                    }
                    messagesUseCases.addMessage(
                        Message(
                            id = null,
                            recipientPhoneNumber = event.messageSender,
                            recipientId = contactList.filter { it.phoneNumber == event.messageSender }[0].id,
                            content = event.messageContent,
                            isIncoming = true,
                            hasTransmitted = TransmissionStatus.RECEIVED,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                }
            }
        }
    }
}