package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fortytwo.rgilles.ft_hangouts.R
import fortytwo.rgilles.ft_hangouts.common.colourMap
import fortytwo.rgilles.ft_hangouts.common.presentation.MainActivity
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.components.ContactItem
import fortytwo.rgilles.ft_hangouts.common.presentation.util.Screen
import fortytwo.rgilles.ft_hangouts.common.presentation.util.getFormattedDate
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    navController: NavController,
    timestampEventFlow: SharedFlow<MainActivity.ShowTimestampEvent>,
    viewModel: ContactListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val expanded = remember { mutableStateOf(false) }
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
                ),
                actions = {
                    IconButton(onClick = { expanded.value = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.alt_text_settings_buttonIcon))
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        colourMap.forEach { (colourName, colourValue) ->
                            DropdownMenuItem(
                                text = { Text(colourName) },
                                onClick = { viewModel.onEvent(ContactListEvent.ChangedColour(context, colourValue)) }
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(imageVector = Icons.Filled.Contacts, contentDescription = null) },
                    label = { Text(stringResource(R.string.navigation_contacts)) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.ConversationListScreen.route) },
                    icon = { Icon(imageVector = Icons.Filled.Message, contentDescription = null) },
                    label = { Text(stringResource(R.string.navigation_conversations)) }
                )
            }
                    },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.DispAddEditContactScreen.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.alt_text_add_contact),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },

        ) { values ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
                .padding(5.dp),
        ) {
            items(state.contacts) { contact ->
                ContactItem(
                    contact = contact,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                Screen.DispAddEditContactScreen.route +
                                        "?contactId=${contact.id}"
                            )
                        },
                    navController = navController
                )
            }
        }
    }
}