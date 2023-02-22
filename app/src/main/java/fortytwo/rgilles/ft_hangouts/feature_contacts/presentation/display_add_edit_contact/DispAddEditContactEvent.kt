package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.display_add_edit_contact

import android.content.Context
import android.net.Uri

sealed class DispAddEditContactEvent {
    data class EnteredFirstName(val value: String): DispAddEditContactEvent()
    data class EnteredLastName(val value: String): DispAddEditContactEvent()
    data class EnteredPhoneNumber(val value: String): DispAddEditContactEvent()
    data class EnteredEmail(val value: String): DispAddEditContactEvent()
    data class ChangedPicture(val uri: Uri?, val context: Context): DispAddEditContactEvent()
    //data class ChangedBirthday(val date: LocalDate?): DispAddEditContactEvent()
    object SaveContact: DispAddEditContactEvent()
}
