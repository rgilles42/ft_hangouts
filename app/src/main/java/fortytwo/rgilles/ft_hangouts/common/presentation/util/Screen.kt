package fortytwo.rgilles.ft_hangouts.common.presentation.util

sealed class Screen(val route: String) {
    object ContactListScreen: Screen("contact_list_screen")
    object DispAddEditContactScreen: Screen("disp_add_edit_contact_screen")
    object ConversationListScreen: Screen("conversation_list_screen")
    object ConversationChatScreen: Screen("conversation_chat_screen")
}