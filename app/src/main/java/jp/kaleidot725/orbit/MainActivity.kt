package jp.kaleidot725.orbit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.kaleidot725.orbit.ui.pages.details.DetailsPage
import jp.kaleidot725.orbit.ui.pages.library.LibraryPage
import jp.kaleidot725.orbit.ui.theme.OrbitTheme
import org.koin.core.parameter.parametersOf


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            OrbitTheme {
                window.statusBarColor = MaterialTheme.colors.primaryVariant.toArgb()
                Box(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController, startDestination = "library") {
                        composable("library") {
                            LibraryPage(
                                viewModel = getComposeViewModel(),
                                onShowDetail = { id ->
                                    navController.navigate("details/${id}") // FIXME
                                }
                            )
                        }
                        composable("details/{id}") {
                            val id = parametersOf(it.arguments?.getString("id")?.toInt() ?: 0)
                            DetailsPage(
                                viewModel = getComposeViewModel(parameters = { id }),
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}