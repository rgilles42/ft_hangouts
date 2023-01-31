package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.util

sealed class Screen(val route: String) {
    object ContactListScreen: Screen("contact_list_screen")
    object DispAddEditContactScreen: Screen("disp_add_edit_contact_screen")
}