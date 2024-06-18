package com.ivandjr.listcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ivandjr.listcompose.ui.screens.listTask.ListTask
import com.ivandjr.listcompose.ui.screens.listTask.viewmodel.ListTaskViewModel
import com.ivandjr.listcompose.ui.screens.listTask.viewmodel.ListTaskViewModelFactory
import com.ivandjr.listcompose.ui.screens.saveTask.SaveTask
import com.ivandjr.listcompose.ui.screens.saveTask.viewModel.SaveTaskViewModel
import com.ivandjr.listcompose.ui.screens.saveTask.viewModel.SaveTaskViewModelFactory
import com.ivandjr.listcompose.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "listTask") {
                    composable(route = "listTask") {
                        val viewModel: ListTaskViewModel = viewModel(
                            factory = ListTaskViewModelFactory(),
                        )
                        ListTask(
                            state = viewModel.viewState.value,
                            effectFlow = viewModel.effect,
                            onEventSent = { event -> viewModel.setEvent(event) },
                            navController = navController,
                        )
                    }
                    composable(route = "saveTask") {
                        val viewModel: SaveTaskViewModel = viewModel(
                            factory = SaveTaskViewModelFactory(),
                        )

                        SaveTask(
                            state = viewModel.viewState.value,
                            effectFlow = viewModel.effect,
                            onEventSent = { event -> viewModel.setEvent(event) },
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}
