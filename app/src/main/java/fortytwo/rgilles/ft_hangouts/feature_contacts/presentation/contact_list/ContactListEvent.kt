package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list

import android.content.Context

sealed class ContactListEvent {
    data class ChangedColour(val context: Context, val colourValue: Long): ContactListEvent()
}
