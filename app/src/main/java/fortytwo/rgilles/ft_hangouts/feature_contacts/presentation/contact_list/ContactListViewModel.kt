package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fortytwo.rgilles.ft_hangouts.common.dataStore
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.ContactUseCases
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.ContactOrder
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases
) : ViewModel() {
    //store list of contacts
    //store order of contacts
    //store if order selector is displayed or not
    private val _state = mutableStateOf(ContactListState())
    val state: State<ContactListState> = _state

    private var getContactsJob: Job? = null

    init {
        getContacts(ContactOrder.LastName(OrderType.Ascending))
    }

    fun onEvent(event: ContactListEvent) {
        when (event) {
            is ContactListEvent.ChangedColour -> {
                viewModelScope.launch {
                    event.context.dataStore.edit { preferences ->
                        preferences[fortytwo.rgilles.ft_hangouts.common.PreferencesKeys.COLOUR_ID] = event.colourId
                    }
                }
            }
        }
    }

    private fun getContacts(contactOrder: ContactOrder) {
        getContactsJob?.cancel()
        getContactsJob = contactUseCases.getContacts(contactOrder)
            .onEach { contacts ->
                _state.value = state.value.copy(
                    contacts = contacts,
                    contactOrder = contactOrder
                )
            }
            .launchIn(viewModelScope)
    }
}