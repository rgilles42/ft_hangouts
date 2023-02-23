package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fortytwo.rgilles.ft_hangouts.common.presentation.util.Screen
import fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_list.components.ConversationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationListScreen(
    navController: NavController,
    viewModel: ConversationListViewModel = hiltViewModel()
) {
    val contactListWithConvs = viewModel.contactListWithConvsState.value
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("ft_hangouts") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate(Screen.ContactListScreen.route) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                              },
                    icon = { Icon(imageVector = Icons.Filled.Contacts, contentDescription = "Contacts") },
                    label = { Text("Contacts") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(imageVector = Icons.Filled.Message, contentDescription = "Conversations") },
                    label = { Text("Conversations") }
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { values ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
                .padding(5.dp)
        ) {
            items(contactListWithConvs) { contactWithMessages ->
                ConversationItem(
                    contactWithMessages = contactWithMessages,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                Screen.ConversationChatScreen.route +
                                        "?contactId=${contactWithMessages.contact.id}"
                            )
                        }
                )
            }
        }
    }
}