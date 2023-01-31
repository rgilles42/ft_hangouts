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
                        composable(route = Screen.ContactListScreen.route) {
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
                    }
//                    val mySnackbarHostState = remember { SnackbarHostState() }
//                    val scope = rememberCoroutineScope()
//                    Scaffold(snackbarHost = { SnackbarHost(hostState = mySnackbarHostState) },
//                        floatingActionButton = {
//                            FloatingActionButton(onClick = {
//                                scope.launch {
//                                    mySnackbarHostState.showSnackbar(
//                                        "Button has been pressed"
//                                    )
//                                }
//                            }) {
//                                Icon(
//                                    imageVector = Icons.Default.Add,
//                                    contentDescription = null,
//                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
//                                )
//                            }
//                        },
//                        topBar = {
//                            TopAppBar(
//                                title = { Text("ft_hangouts") },
//                                colors = TopAppBarDefaults.smallTopAppBarColors(
//                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
//                                    titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
//                                )
//                            )
//                        }) { values ->
//                        Column(
//                            modifier = Modifier
//                                .padding(values)
//                                .padding(16.dp)
//                        ) {
//                            Box {
//                                val imageToDraw = remember {
//                                    mutableStateOf(R.drawable.capture_d_cran_de_2022_01_14_02_19_12)
//                                }
//                                ImageCard(title = "C le jeu du zelda",
//                                    painter = painterResource(id = imageToDraw.value),
//                                    altText = "je suis vieux",
//                                    modifier = Modifier.clickable {
//                                        imageToDraw.value = listOf(
//                                            R.drawable.its_dangerous_to_go_alone_take_this_2,
//                                            R.drawable.capture_d_cran_de_2022_01_14_02_19_12
//                                        )[if (Random.nextInt() < 0) {
//                                            0
//                                        } else {
//                                            1
//                                        }]
//                                    })
//                            }
//                            LazyColumn{
//                                itemsIndexed(
//                                    listOf("Je", "Mange", "Des", "Chips", "Je", "Mange", "Des", "Chips", "Je", "Mange", "Des", "Chips", "Je", "Mange", "Des", "Chips", "Je", "Mange", "Des", "Chips", )
//                                ) { index, string ->
//                                    Text("$index $string")
//                                }
//                            }
//                        }
//                    }
                }
            }
        }
    }
}

//@Composable
//fun ImageCard(
//    title: String, painter: Painter, altText: String, modifier: Modifier = Modifier
//
//) {
//    Card(
//        modifier = modifier.width(200.dp),
//        shape = MaterialTheme.shapes.large,
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surface,
//
//            ),
//        elevation = CardDefaults.cardElevation(5.dp)
//    ) {
//        Box(modifier = modifier.height(200.dp)) {
//            Image(
//                painter = painter, contentDescription = altText, contentScale = ContentScale.Crop
//            )
//            Box(
//                modifier = modifier
//                    .fillMaxSize()
//                    .background(
//                        Brush.verticalGradient(
//                            colors = listOf(
//                                Color.Transparent, Color.Black
//                            ), startY = 300f
//                        )
//                    )
//            )
//            Box(
//                modifier = modifier
//                    .fillMaxSize()
//                    .padding(12.dp),
//                contentAlignment = Alignment.BottomStart
//            ) {
//                Text(text = title, color = Color.White, fontSize = 16.sp)
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(
//    showBackground = true,
//    showSystemUi = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
//    device = "id:pixel_5",
//    apiLevel = 33
//)
//@Composable
//fun DefaultPreview() {
//    Ft_hangoutsTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
//        ) {
//            val snackbarHostState = remember { SnackbarHostState() }
//            val scope = rememberCoroutineScope()
//            Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
//                floatingActionButton = {
//                    FloatingActionButton(onClick = { scope.launch { snackbarHostState.showSnackbar("P R O U T") } }) {
//                        Icon(
//                            imageVector = Icons.Default.Add,
//                            contentDescription = null,
//                            tint = MaterialTheme.colorScheme.onPrimaryContainer
//                        )
//                    }
//                },
//                topBar = {
//                    TopAppBar(
//                        title = { Text("ft_hangouts") },
//                        colors = TopAppBarDefaults.smallTopAppBarColors(
//                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
//                            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
//                        )
//                    )
//                }) { values ->
//                Column(
//                    Modifier
//                        .padding(values)
//                        .padding(16.dp)
//                ) {
//                    Box {
//                        val imageToDraw = remember {
//                            mutableStateOf(R.drawable.capture_d_cran_de_2022_01_14_02_19_12)
//                        }
//                        ImageCard(title = "C le jeu du zelda",
//                            painter = painterResource(id = imageToDraw.value),
//                            altText = "je suis vieux",
//                            modifier = Modifier.clickable {
//                                imageToDraw.value = listOf(
//                                    R.drawable.its_dangerous_to_go_alone_take_this_2,
//                                    R.drawable.capture_d_cran_de_2022_01_14_02_19_12
//                                )[if (Random.nextInt() < 0) {
//                                    0
//                                } else {
//                                    1
//                                }]
//                            })
//                    }
//                    LazyColumn {
//                        items(500) {
//                            Text("prout $it")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}