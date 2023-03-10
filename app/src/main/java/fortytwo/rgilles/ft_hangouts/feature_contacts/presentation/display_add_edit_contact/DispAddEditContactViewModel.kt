package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.display_add_edit_contact

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.InvalidContactException
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.ContactUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class DispAddEditContactViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _contactFirstName = mutableStateOf("")
    val contactFirstName: State<String> = _contactFirstName

    private val _contactLastName = mutableStateOf("")
    val contactLastName: State<String> = _contactLastName

    private val _contactPhoneNumber = mutableStateOf("")
    val contactPhoneNumber: State<String> = _contactPhoneNumber

    private val _contactEmail = mutableStateOf("")
    val contactEmail: State<String> = _contactEmail

    private val _contactPicturePath = mutableStateOf("")
    val contactPicturePath: State<String> = _contactPicturePath

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentContactId: Int? = null

    init {
        savedStateHandle.get<Int>("contactId")?.let { contactId ->
            if (contactId != -1) {
                viewModelScope.launch {
                    contactUseCases.getContact(contactId)?.also {
                        currentContactId = it.id
                        _contactFirstName.value = it.firstName
                        _contactLastName.value = it.lastName
                        _contactEmail.value = it.email
                        _contactPhoneNumber.value = it.phoneNumber
                        _contactPicturePath.value = it.picturePath
                    }
                }
            }
        }
    }

    fun onEvent(event: DispAddEditContactEvent) {
        when(event) {
            is DispAddEditContactEvent.EnteredFirstName -> {
                _contactFirstName.value = event.value
            }
            is DispAddEditContactEvent.EnteredEmail -> {
                _contactEmail.value = event.value
            }
            is DispAddEditContactEvent.EnteredLastName -> {
                _contactLastName.value = event.value
            }
            is DispAddEditContactEvent.EnteredPhoneNumber -> {
                _contactPhoneNumber.value = event.value
            }
            is DispAddEditContactEvent.ChangedPicture -> {
                var filename = ""
                if (event.uri != null) {
                    val contentResolver = event.context.contentResolver
                    var mimeType = contentResolver.getType(event.uri)
                    if (mimeType != null) {
                        mimeType = mimeType.substring(mimeType.lastIndexOf("/") + 1)
                    }
                    filename = "${System.currentTimeMillis()}.$mimeType"
                    val inputStream = contentResolver.openInputStream(event.uri)
                    val file = File(event.context.filesDir, filename)
                    val outputStream = FileOutputStream(file)
                    inputStream?.copyTo(outputStream)
                    inputStream?.close()
                    outputStream.close()
                }
                _contactPicturePath.value = filename
            }
            is DispAddEditContactEvent.SaveContact -> {
                viewModelScope.launch {
                    try {
                        contactUseCases.addContact(
                            Contact(
                                firstName = contactFirstName.value,
                                lastName = contactLastName.value,
                                phoneNumber = contactPhoneNumber.value,
                                email = contactEmail.value,
                                picturePath = contactPicturePath.value,
                                id = currentContactId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveContact)
                    } catch(e: InvalidContactException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "EXCEPTION_GENERIC_SAVE_CONTACT"
                            )
                        )
                    }
                }
            }
            is DispAddEditContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    try {
                        contactUseCases.deleteContact(
                            Contact(
                                firstName = contactFirstName.value,
                                lastName = contactLastName.value,
                                phoneNumber = contactPhoneNumber.value,
                                email = contactEmail.value,
                                picturePath = contactPicturePath.value,
                                id = currentContactId
                            )
                        )
                        _eventFlow.emit(UiEvent.DeletedContact)
                    } catch (e: java.lang.Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message?: "EXCEPTION_GENERIC_DELETE_CONTACT"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveContact: UiEvent()
        object DeletedContact: UiEvent()
    }

}