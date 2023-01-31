package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.display_add_edit_contact

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview(
//    showBackground = true,
//    showSystemUi = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
//    device = "id:pixel_5",
//    apiLevel = 33
//)
fun DispAddEditContactScreen(
    navController: NavController,
    viewModel: DispAddEditContactViewModel = hiltViewModel()
) {
    val firstNameState = viewModel.contactFirstName.value
    val lastNameState = viewModel.contactLastName.value
    val phoneNumberState = viewModel.contactPhoneNumber.value
    val emailState = viewModel.contactEmail.value
    val birthdayState = viewModel.contactBirthday.value

//    val firstNameState = "Jean"
//    val lastNameState = "Roulin"
//    val phoneNumberState = "+33642466193"
//    val emailState = "jean.roulin823674@gmail.com"
//    val birthdayState: LocalDate? = null

    val snackbarHostState = remember { SnackbarHostState()}

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is DispAddEditContactViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is DispAddEditContactViewModel.UiEvent.SaveContact -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                //TODO: Add cancel button
                title = { Text("Edit contact") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(DispAddEditContactEvent.SaveContact)
            }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save contact",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.size(150.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors()
                ) {
                    //TODO: if contact has picture
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(30.dp),
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    //TODO: else maybe add picture label?
                }
            }
            Spacer(modifier = Modifier.size(5.dp))
            //TODO: if picture
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                Text(text = "Change")
                Spacer(Modifier.size(10.dp))
                Text(text = "Remove")
            }
            Spacer(modifier = Modifier.size(35.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "",
                    Modifier.size(30.dp)
                )
                Spacer(Modifier.size(10.dp))
                OutlinedTextField(
                    value = firstNameState,
                    onValueChange = {
                        viewModel.onEvent(DispAddEditContactEvent.EnteredFirstName(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("First Name") }
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.size(40.dp))
                OutlinedTextField(
                    value = lastNameState,
                    onValueChange = {
                        viewModel.onEvent(DispAddEditContactEvent.EnteredLastName(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Last Name") }
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "",
                    Modifier.size(30.dp)
                )
                Spacer(Modifier.size(10.dp))
                OutlinedTextField(
                    value = phoneNumberState,
                    onValueChange = {
                        viewModel.onEvent(DispAddEditContactEvent.EnteredPhoneNumber(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Phone Number") }
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "",
                    Modifier.size(30.dp)
                )
                Spacer(Modifier.size(10.dp))
                OutlinedTextField(
                    value = emailState,
                    onValueChange = {
                        viewModel.onEvent(DispAddEditContactEvent.EnteredEmail(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email Address") }
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            //TODO: Birthday
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    imageVector = Icons.Default.Cake,
//                    contentDescription = "",
//                    Modifier.size(30.dp)
//                )
//                Spacer(Modifier.size(10.dp))
//                DatePicker()
//            }
        }
    }
}