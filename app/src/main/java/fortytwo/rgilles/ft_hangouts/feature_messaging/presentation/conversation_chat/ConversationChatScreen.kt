package fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_chat

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.components.formContactName
import fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_chat.components.ChatItem
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationChatScreen(
    navController: NavController,
    viewModel: ConversationChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    val contactWithMessagesState = viewModel.contactWithMessagesState.value
    val currentlyTypedMessageState = viewModel.currentlyTypedMessage.value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ConversationChatViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is ConversationChatViewModel.UiEvent.SendMessage -> {
                    viewModel.onEvent(ConversationChatEvent.TypedMessage(""))
                }
            }
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title =
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (contactWithMessagesState.contact.picturePath.isNotBlank() && File(context.filesDir, contactWithMessagesState.contact.picturePath).exists()) {
                            Card(
                                Modifier.size(65.dp)
                                    .padding(8.dp),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors()
                            ) {
                                val source = ImageDecoder.createSource(File(context.filesDir, contactWithMessagesState.contact.picturePath))
                                bitmap.value = ImageDecoder.decodeBitmap(source)
                                bitmap.value?.let {  btm ->
                                    Image(
                                        bitmap = btm.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxHeight(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                        Text(formContactName(contactWithMessagesState.contact))
                    }
                },
//                colors = TopAppBarDefaults.smallTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
//                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
//                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        /*TODO*/
                    }) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Call contact"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(16.dp),
                reverseLayout = true
            ) {
                items(contactWithMessagesState.messages) { message ->
                    ChatItem(message = message)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(),
                elevation = CardDefaults.elevatedCardElevation(10.dp)
            ) {
                OutlinedTextField(
                    value = currentlyTypedMessageState,
                    onValueChange = {
                        viewModel.onEvent(ConversationChatEvent.TypedMessage(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    placeholder = {
                        Text("Message...")
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send message",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(ConversationChatEvent.SendMessage)
                            }
                        )
                    },
                    shape = MaterialTheme.shapes.medium
                )
            }
        }
    }
}