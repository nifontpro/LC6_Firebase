package ru.nifontbus.lc6_firebase.screen.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.nifontbus.lc6_firebase.model.Bio
import ru.nifontbus.lc6_firebase.ui.theme.normalPadding
import ru.nifontbus.lc6_firebase.ui.theme.smallPadding

@ExperimentalFoundationApi

@Composable
fun MainScreen(
    onAddScreen: () -> Unit
) {
    val viewModel: MainViewModel = hiltViewModel()
    val bioList = viewModel.bioList.collectAsState().value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(scaffoldState) {
        viewModel.message.collect { message ->
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddScreen,
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
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
                    BioCard(bio)
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
                .background(diaBrushColor(bio.dia ?: 0))
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
                text = bio.dia.toString(),
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
                text = bio.sys.toString(),
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
                    text = bio.pulse.toString(),
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
fun diaBrushColor(dia: Int): Brush {
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