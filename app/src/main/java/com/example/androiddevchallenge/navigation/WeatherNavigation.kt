/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.views

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.navigation.Screens
import com.example.androiddevchallenge.viewmodels.HomeViewModel

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    val viewModel: HomeViewModel = viewModel()
    NavHost(navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) { Home(navController = navController, viewModel) }
        composable(Screens.SelectCity.route) { SelectCity(navController = navController, viewModel) }
    }
}
