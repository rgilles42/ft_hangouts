package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.components.ContactItem
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    navController: NavController,
    viewModel: ContactListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.DispAddEditContactScreen.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },

        ) { values ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(values),
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
                        }
                )
            }
        }
    }
}