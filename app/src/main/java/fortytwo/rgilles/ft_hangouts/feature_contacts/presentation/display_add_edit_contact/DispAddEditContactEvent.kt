package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.display_add_edit_contact

import java.time.LocalDate

sealed class DispAddEditContactEvent {
    data class EnteredFirstName(val value: String): DispAddEditContactEvent()
    data class EnteredLastName(val value: String): DispAddEditContactEvent()
    data class EnteredPhoneNumber(val value: String): DispAddEditContactEvent()
    data class EnteredEmail(val value: String): DispAddEditContactEvent()
    data class ChangeBirthday(val date: LocalDate?): DispAddEditContactEvent()
    object SaveContact: DispAddEditContactEvent()
}
