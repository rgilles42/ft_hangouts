package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fortytwo.rgilles.ft_hangouts.common.domain.relations.ContactWithMessages
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.ContactUseCases
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.use_case.MessageUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConversationChatViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases,
    private val messageUseCases: MessageUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _contactWithMessagesState = mutableStateOf(
        ContactWithMessages(
            Contact(null, "", "", "", ""),
            emptyList()
        )
    )
    val contactWithMessagesState: State<ContactWithMessages> = _contactWithMessagesState
    private var getContactWithMessagesJob: Job? = null

    init {
        savedStateHandle.get<Int>("contactId")?.let { contactId ->
            getContactWithMessages(contactId)
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
}