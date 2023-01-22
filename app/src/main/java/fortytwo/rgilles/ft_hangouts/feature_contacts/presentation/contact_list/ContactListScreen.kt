package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list

import android.content.res.Configuration
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.components.ContactItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    navController: NavController,
    viewModel: ContactListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
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
            FloatingActionButton(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "Button has been pressed"
                    )
                }
            }) {
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
                    modifier = Modifier.clickable {
                },
                    contact = contact)

            }
        }
    }
    }