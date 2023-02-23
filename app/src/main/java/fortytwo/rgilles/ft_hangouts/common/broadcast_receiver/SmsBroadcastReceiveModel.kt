package fortytwo.rgilles.ft_hangouts.common.broadcast_receiver

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.ContactUseCases
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.TransmissionStatus
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.use_case.MessageUseCases
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SmsBroadcastReceiveModel @Inject constructor(
    private val contactUseCases: ContactUseCases,
    private val messagesUseCases: MessageUseCases
) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _contactListState = mutableStateOf<List<Contact>>(emptyList())
    val contactListState : State<List<Contact>> = _contactListState

    private var getContactsJob: Job? = null

    init {
        getContacts()
    }

    fun onEvent(event: SmsBroadcastEvent) {
        when (event) {
            is SmsBroadcastEvent.ReceivedMessage -> {
                if (event.messageSender == null) { return }
                coroutineScope.launch {
                    if (contactListState.value.any {  it.phoneNumber == event.messageSender }) {
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
                    }
                    messagesUseCases.addMessage(
                        Message(
                            id = null,
                            recipientPhoneNumber = event.messageSender,
                            recipientId = contactListState.value.filter { it.phoneNumber == event.messageSender }[0].id,
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

    private fun getContacts() {
        getContactsJob?.cancel()
        getContactsJob = contactUseCases.getContacts()
            .onEach { contacts ->
                _contactListState.value = contacts
            }
            .launchIn(coroutineScope)
    }
}