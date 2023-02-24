package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fortytwo.rgilles.ft_hangouts.common.domain.relations.ContactWithMessages
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.ContactUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConversationListViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases,
) : ViewModel() {
    private val _contactListWithConvsState = mutableStateOf(emptyList<ContactWithMessages>())
    val contactListWithConvsState: State<List<ContactWithMessages>> = _contactListWithConvsState
    private var getContactsWithConvsJob: Job? = null

    init {
        getContactsWithConvs()
    }

    private fun getContactsWithConvs() {
        getContactsWithConvsJob?.cancel()
        getContactsWithConvsJob = contactUseCases.getContactsWithActiveConvs()
            .onEach { contactsWithConvs ->
                _contactListWithConvsState.value = contactsWithConvs
            }
            .launchIn(viewModelScope)
    }
}

