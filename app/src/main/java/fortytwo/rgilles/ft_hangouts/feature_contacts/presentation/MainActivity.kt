package fortytwo.rgilles.ft_hangouts.feature_contacts.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.contact_list.ContactListScreen
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.display_add_edit_contact.DispAddEditContactScreen
import fortytwo.rgilles.ft_hangouts.feature_contacts.presentation.util.Screen
import fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_chat.ConversationChatScreen
import fortytwo.rgilles.ft_hangouts.feature_messaging.presentation.conversation_list.ConversationListScreen
import fortytwo.rgilles.ft_hangouts.ui.theme.Ft_hangoutsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}
