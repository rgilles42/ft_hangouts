package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.ContactUseCases
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.ContactOrder
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.util.OrderType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
            is ContactListEvent.Order -> {
                if (state.value.contactOrder::class == event.contactOrder::class &&
                        state.value.contactOrder.orderType == event.contactOrder.orderType) {
                    return
                }
                getContacts(event.contactOrder)
            }
            is ContactListEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSelectorDisplayed = !state.value.isOrderSelectorDisplayed
                )
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