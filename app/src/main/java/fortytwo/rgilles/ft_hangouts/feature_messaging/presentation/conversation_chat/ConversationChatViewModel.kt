package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_chat

import android.Manifest
import android.content.pm.PackageManager
import android.telephony.SmsManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fortytwo.rgilles.ft_hangouts.common.domain.relations.ContactWithMessages
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.ContactUseCases
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.TransmissionStatus
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.use_case.MessageUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationChatViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases,
    private val messageUseCases: MessageUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _contactWithMessagesState = mutableStateOf(
        ContactWithMessages(
            Contact(null, "", "", "", "", ""),
            emptyList()
        )
    )
    val contactWithMessagesState: State<ContactWithMessages> = _contactWithMessagesState
    private val _currentlyTypedMessage = mutableStateOf("")
    val currentlyTypedMessage : State<String> = _currentlyTypedMessage
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private var getContactWithMessagesJob: Job? = null

    init {
        savedStateHandle.get<Int>("contactId")?.let { contactId ->
            getContactWithMessages(contactId)
        }
    }

    fun onEvent(event: ConversationChatEvent) {
        when (event) {
            is ConversationChatEvent.TypedMessage -> {
                _currentlyTypedMessage.value = event.value
            }
            is ConversationChatEvent.SendMessage -> {
                viewModelScope.launch {
                    try {
                        if (event.context.checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
                            throw Exception("EXCEPTION_PERMISSION_DENIED")
                        }
                            messageUseCases.addMessage(
                                Message(
                                    id = null,
                                    recipientPhoneNumber = contactWithMessagesState.value.contact.phoneNumber,
                                    recipientId = contactWithMessagesState.value.contact.id,
                                    content = currentlyTypedMessage.value,
                                    isIncoming = false,
                                    hasTransmitted = TransmissionStatus.SENDING,
                                    timestamp = System.currentTimeMillis()
                                )
                            )
                            event.context.getSystemService(SmsManager::class.java).sendTextMessage(
                                contactWithMessagesState.value.contact.phoneNumber,
                                null,
                                currentlyTypedMessage.value,
                                null,
                                null
                            )

                        _eventFlow.emit(UiEvent.SendMessage)
                    } catch (e: java.lang.Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "EXCEPTION_GENERIC_SEND_MESSAGE"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getContactWithMessages(contactId: Int) {
        getContactWithMessagesJob?.cancel()
        getContactWithMessagesJob = contactUseCases.getContactWithMessages(contactId)
            .onEach { contactWithMessages ->
                _contactWithMessagesState.value = contactWithMessages
            }
            .launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SendMessage: UiEvent()
    }
}