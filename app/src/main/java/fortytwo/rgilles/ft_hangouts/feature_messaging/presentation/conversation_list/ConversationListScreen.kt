package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationListScreen(
    navController: NavController,
    //TODO:viewModel: ConversationListViewModel = hiltViewModel()
) {
    //TODO:val state = viewModel.state.value
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
 //       floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    navController.navigate(Screen.ConversationScreen.route)*/
//                }
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Edit,
//                    contentDescription = "New conversation",
//                    tint = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            }
 //       },

        ) { values ->
        val vals = values
    }
}