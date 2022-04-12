package ru.nifontbus.lc6_firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.nifontbus.lc6_firebase.screen.navigation.Navigate
import ru.nifontbus.lc6_firebase.ui.theme.LC6_FirebaseTheme

@ExperimentalComposeUiApi
@ExperimentalFoundationApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LC6_FirebaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigate()
                }
            }
        }
    }
}