package ru.nifontbus.lc6_firebase.screen.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.nifontbus.lc6_firebase.model.Bio
import ru.nifontbus.lc6_firebase.screen.navigation.TemplateSwipeToDismiss
import ru.nifontbus.lc6_firebase.ui.theme.DeleteColor
import ru.nifontbus.lc6_firebase.ui.theme.normalPadding
import ru.nifontbus.lc6_firebase.ui.theme.smallPadding

@ExperimentalMaterialApi
@ExperimentalFoundationApi

@Composable
fun MainScreen(
    onAddScreen: () -> Unit
) {
    val viewModel: MainViewModel = hiltViewModel()
    val bioList = viewModel.bioList.collectAsState().value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var job: Job by remember {
        mutableStateOf(Job())
    }

    LaunchedEffect(scaffoldState) {
        viewModel.message.collect { message ->
            job.cancel()
            job = scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddScreen,
                backgroundColor = DeleteColor
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Add bio",
                    tint = MaterialTheme.colors.onSecondary
                )
            }
        },
    ) {
        val grouped = bioList.groupBy { it.date }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = normalPadding, vertical = smallPadding)
        ) {

            grouped.forEach { (initial, listForInitial) ->
                stickyHeader {
                    Card(
                        elevation = 2.dp,
                        modifier = Modifier
                            .padding(bottom = normalPadding)
                    ) {
                        Text(
                            text = "$initial ",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxWidth()
//                                .background(MaterialTheme.colors.surface.copy(alpha = 0.8f))
                                .padding(vertical = normalPadding, horizontal = normalPadding),
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }

                items(listForInitial) { bio ->
                    TemplateSwipeToDismiss(
                        modifier = Modifier.fillMaxWidth(),
                        onDelete = { viewModel.deleteBio(bio.id) }
                    ) {
                        BioCard(bio)
                    }

                }

                item {
                    Spacer(modifier = Modifier.height(smallPadding))
                }
            }
        }
    }
}

@Composable
private fun BioCard(bio: Bio) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(sysBrushColor(bio.sys ?: 100))
        ) {

            Text(
                text = bio.time ?: "-",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = normalPadding),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )

            Text(
                text = bio.sys?.run { toString() } ?: "-",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(Center)
                    .offset(x = (-45).dp)
            )
            Text(
                text = "/",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(Center)
            )
            Text(
                text = bio.dia?.run { toString() } ?: "-",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(Center)
                    .offset(x = 45.dp)
            )

            Row(
                verticalAlignment = CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = normalPadding)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = bio.pulse?.run { toString() } ?: "-",
                    modifier = Modifier
                        .padding(horizontal = smallPadding)
                        .width(50.dp),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
        Divider()
    }
}

@Composable
fun sysBrushColor(dia: Int): Brush {
    val color = when (dia) {
        in 90..110 -> Color(0xFFFFFF99)
        in 111..130 -> Color(0xFF99FFDD)
        in 131..150 -> Color(0xFFFFFF99)
        else -> Color(0xFFFFCCAA)
    }
    return Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colors.surface,
            color,
            MaterialTheme.colors.surface,
        )
    )
}