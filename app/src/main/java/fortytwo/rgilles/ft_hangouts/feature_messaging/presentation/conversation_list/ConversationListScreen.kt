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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fortytwo.rgilles.ft_hangouts.R
import fortytwo.rgilles.ft_hangouts.common.presentation.MainActivity
import fortytwo.rgilles.ft_hangouts.common.presentation.util.Screen
import fortytwo.rgilles.ft_hangouts.common.presentation.util.getFormattedDate
import fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_list.components.ConversationItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationListScreen(
    navController: NavController,
    timestampEventFlow: SharedFlow<MainActivity.ShowTimestampEvent>,
    viewModel: ConversationListViewModel = hiltViewModel()
) {
    val contactListWithConvs = viewModel.contactListWithConvsState.value
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        timestampEventFlow.collectLatest { showTimestampEvent ->
            snackbarHostState.showSnackbar(
                context.getString(R.string.snackbar_resume) + getFormattedDate(
                    showTimestampEvent.timestamp
                )
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                    icon = { Icon(imageVector = Icons.Filled.Contacts, contentDescription = null) },
                    label = { Text(stringResource(R.string.navigation_contacts)) }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(imageVector = Icons.Filled.Message, contentDescription = null) },
                    label = { Text(stringResource(R.string.navigation_conversations)) }
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { values ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
                .padding(horizontal = 3.dp)
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