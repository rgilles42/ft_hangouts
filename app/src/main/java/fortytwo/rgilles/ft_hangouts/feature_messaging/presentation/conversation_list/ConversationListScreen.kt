package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.util.Screen
import fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_list.ConversationListViewModel
import fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_list.components.ConversationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationListScreen(
    navController: NavController,
    viewModel: ConversationListViewModel = hiltViewModel()
) {
    val contactListWithConvs = viewModel.contactListWithConvsState.value
    //TODO: Delete contact val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
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
                    onClick = { navController.navigate(Screen.ContactListScreen.route) },
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    /*navController.navigate(Screen.ConversationScreen.route)*/
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "New conversation",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },

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
                            //TODO
                        }
                )
            }
        }
    }
}