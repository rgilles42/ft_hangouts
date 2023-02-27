package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.display_add_edit_contact

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fortytwo.rgilles.ft_hangouts.common.presentation.MainActivity
import fortytwo.rgilles.ft_hangouts.common.presentation.util.getFormattedDate
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DispAddEditContactScreen(
    navController: NavController,
    timestampEventFlow: SharedFlow<MainActivity.ShowTimestampEvent>,
    viewModel: DispAddEditContactViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val firstNameState = viewModel.contactFirstName.value
    val lastNameState = viewModel.contactLastName.value
    val phoneNumberState = viewModel.contactPhoneNumber.value
    val emailState = viewModel.contactEmail.value
    val picturePathState = viewModel.contactPicturePath.value

    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        viewModel.onEvent(DispAddEditContactEvent.ChangedPicture(uri, context))
    }

    val snackbarHostState = remember { SnackbarHostState()}

    LaunchedEffect(key1 = true) {
        timestampEventFlow.collectLatest { showTimestampEvent ->
            snackbarHostState.showSnackbar("Resuming from pause at ${getFormattedDate(showTimestampEvent.timestamp)}")
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is DispAddEditContactViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is DispAddEditContactViewModel.UiEvent.SaveContact -> {
                    navController.navigateUp()
                }
                DispAddEditContactViewModel.UiEvent.DeletedContact -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit contact") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Go back to contact list"
                        )
                    }
                },
                actions = {
                    if (viewModel.currentContactId != null) {
                        IconButton(onClick = {
                            viewModel.onEvent(DispAddEditContactEvent.DeleteContact)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete contact"
                            )
                        }
                    }
                }
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
                    if (picturePathState.isNotBlank() && File(context.filesDir, picturePathState).exists()) {
                        val source = ImageDecoder.createSource(File(context.filesDir, picturePathState))
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                        bitmap.value?.let {  btm ->
                            Image(
                                bitmap = btm.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    } else {
                        Icon(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(30.dp),
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(5.dp))
            if (picturePathState.isNotBlank()) {
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Text(
                        text = "Change",
                        modifier = Modifier.clickable {
                            imagePickerLauncher.launch("image/*")
                        }
                    )
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = "Remove",
                        modifier = Modifier.clickable {
                            viewModel.onEvent(DispAddEditContactEvent.ChangedPicture(null, context))
                        }
                    )
                }
            } else {
                Text(
                    text = "Add picture",
                    modifier = Modifier.clickable {
                        imagePickerLauncher.launch("image/*")
                    }
                )
            }
            Spacer(modifier = Modifier.size(35.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Person,
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
                    label = { Text("First Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.Words
                    ),
                    singleLine = true
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
                    label = { Text("Last Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        capitalization = KeyboardCapitalization.Words
                    ),
                    singleLine = true
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Phone,
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
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone
                    ),
                    singleLine = true
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Email,
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
                    label = { Text("Email Address") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}