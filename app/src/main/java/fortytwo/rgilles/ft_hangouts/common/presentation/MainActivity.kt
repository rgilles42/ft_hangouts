package fortytwo.rgilles.ft_hangouts.common.presentation

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.core.app.ActivityCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import fortytwo.rgilles.ft_hangouts.common.broadcast_receiver.SmsBroadcastReceiver
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.ContactListScreen
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.display_add_edit_contact.DispAddEditContactScreen
import fortytwo.rgilles.ft_hangouts.common.presentation.util.Screen
import fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_chat.ConversationChatScreen
import fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_list.ConversationListScreen
import fortytwo.rgilles.ft_hangouts.ui.theme.Ft_hangoutsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (areSmsPermissionsGranted()) {
            registerSmsReceiver()
        } else {
            requestPermissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.SEND_SMS
                )
            )
        }
        setContent {
            Ft_hangoutsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ContactListScreen.route
                    ) {
                        composable(
                            route = Screen.ContactListScreen.route
                        ) {
                            ContactListScreen(navController = navController)
                        }
                        composable(
                            route = Screen.DispAddEditContactScreen.route +
                                    "?contactId={contactId}",
                            arguments = listOf(
                                navArgument(
                                    name = "contactId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            DispAddEditContactScreen(navController = navController)
                        }
                        composable(route = Screen.ConversationListScreen.route) {
                            ConversationListScreen(navController = navController)
                        }
                        composable(
                            route = Screen.ConversationChatScreen.route +
                                    "?contactId={contactId}",
                            arguments = listOf(
                                navArgument(
                                    name = "contactId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            ConversationChatScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }

    private fun areSmsPermissionsGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PERMISSION_GRANTED
    }

    private fun registerSmsReceiver() {
        ActivityCompat.registerReceiver(
            this,
            SmsBroadcastReceiver(),
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION),
            ActivityCompat.RECEIVER_NOT_EXPORTED
        )
        Log.d("DEBUG", "SMS receiver registered")
    }

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { mapOfGrants ->
        if (mapOfGrants[Manifest.permission.RECEIVE_SMS] == true) {
            registerSmsReceiver()
        }
    }
}
