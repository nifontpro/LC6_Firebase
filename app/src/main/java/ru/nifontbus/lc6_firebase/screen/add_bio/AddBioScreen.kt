package ru.nifontbus.lc6_firebase.screen.add_bio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.nifontbus.lc6_firebase.R
import ru.nifontbus.lc6_firebase.ui.theme.Transparent
import ru.nifontbus.lc6_firebase.ui.theme.bigPadding
import ru.nifontbus.lc6_firebase.ui.theme.normalPadding

@ExperimentalComposeUiApi
@Composable
fun AddBioScreen() {
    val viewModel: AddViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()

    val day = viewModel.day
    val month = viewModel.month
    val year = viewModel.year

    val hour = viewModel.hour
    val minute = viewModel.minute

    val sys = viewModel.sys
    val dia = viewModel.dia
    val pulse = viewModel.pulse

    LaunchedEffect(scaffoldState) {
        viewModel.message.collect { message ->
            scaffoldState.snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Image(
            painter = painterResource(id = R.drawable.pressione),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = normalPadding),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val focusRequester = FocusRequester()

            DateCard(day, month, year, focusRequester)
            TimeCard(hour, minute, focusRequester)
            BioCard(sys, dia, pulse, focusRequester)

            Button(
                onClick = { viewModel.addBio() },
                modifier = Modifier.padding(top = bigPadding),
                enabled = viewModel.isEnabledSave()
            ) {
                Text(text = "Сохранить", style = MaterialTheme.typography.h6)
            }
        }
    }
}

@Composable
private fun DateCard(
    day: MutableState<Int?>,
    month: MutableState<Int?>,
    year: MutableState<Int?>,
    focusRequester: FocusRequester

) {
    val focusManager = LocalFocusManager.current

    val editMod = Modifier
        .width(90.dp)
        .clip(MaterialTheme.shapes.small)
        .focusRequester(focusRequester = focusRequester)

    Column(
        modifier = Modifier
            .padding(bigPadding)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(surfaceBrush())
            .padding(bigPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Дата",
            style = MaterialTheme.typography.h4,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IntTextField(
                field = day,
                modifier = editMod,
                placeholder = "День",
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                max = 31
            )

            Text(text = ".", modifier = Modifier.padding(top = normalPadding))

            IntTextField(
                field = month,
                modifier = editMod,
                placeholder = "Месяц",
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                max = 12
            )

            Text(text = ".", modifier = Modifier.padding(top = normalPadding))

            IntTextField(
                field = year,
                modifier = editMod,
                placeholder = "Год",
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                max = 2070
            )
        }
    }
}

@Composable
private fun TimeCard(
    hour: MutableState<Int?>,
    minutes: MutableState<Int?>,
    focusRequester: FocusRequester

) {
    val focusManager = LocalFocusManager.current

    val editMod = Modifier
        .width(70.dp)
        .clip(MaterialTheme.shapes.small)
        .focusRequester(focusRequester = focusRequester)

    Column(
        modifier = Modifier
            .padding(bigPadding)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(surfaceBrush())
            .padding(bigPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Время",
            style = MaterialTheme.typography.h4,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IntTextField(
                field = hour,
                modifier = editMod,
                placeholder = "Ч",
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                min = 0,
                max = 23
            )

            Text(text = ":")

            IntTextField(
                field = minutes,
                modifier = editMod,
                placeholder = "М",
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                min = 0,
                max = 59
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun BioCard(
    sys: MutableState<Int?>,
    dia: MutableState<Int?>,
    pulse: MutableState<Int?>,
    focusRequester: FocusRequester

) {
    val focusManager = LocalFocusManager.current

    val editMod = Modifier
        .width(90.dp)
        .clip(MaterialTheme.shapes.small)
        .focusRequester(focusRequester = focusRequester)

    Column(
        modifier = Modifier
            .padding(bigPadding)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(surfaceBrush())
            .padding(bigPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Показания",
            style = MaterialTheme.typography.h4,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IntTextField(
                field = sys,
                modifier = editMod,
                placeholder = "SYS",
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                min = 0,
                max = 300
            )

            Text(text = "/")

            IntTextField(
                field = dia,
                modifier = editMod,
                placeholder = "DIA",
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                min = 0,
                max = 200
            )

            Text(text = "-")

            val keyboardController = LocalSoftwareKeyboardController.current

            IntTextField(
                field = pulse,
                modifier = editMod,
                placeholder = "Пульс",
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
                min = 0,
                max = 200
            )
        }
    }
}

@Composable
private fun IntTextField(
    field: MutableState<Int?>,
    modifier: Modifier,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions,
    min: Int = 1,
    max: Int = 31
) {
    TextField(
        value = if (field.value != null) field.value.toString() else "",
        onValueChange = { newText ->
            field.value = try {
                val tmp = newText.toInt()
                if (tmp in min..max) tmp else field.value
            } catch (e: Exception) {
                null
            }
        },
        modifier = modifier,
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        maxLines = 1,
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        colors = textFieldColors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
    )
}

@Composable
private fun textFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = MaterialTheme.colors.onBackground,
    backgroundColor = Transparent,
    cursorColor = MaterialTheme.colors.secondary,
    focusedIndicatorColor = MaterialTheme.colors.secondary,
    unfocusedIndicatorColor = MaterialTheme.colors.onSurface,
    placeholderColor = MaterialTheme.colors.onSurface,
    leadingIconColor = MaterialTheme.colors.onSurface,
    trailingIconColor = MaterialTheme.colors.onSurface,
)

@Composable
fun surfaceBrush() = Brush.linearGradient(
    colors = listOf(
        MaterialTheme.colors.surface.copy(alpha = 0.9f),
        MaterialTheme.colors.onError.copy(alpha = 0.9f)
    )
)