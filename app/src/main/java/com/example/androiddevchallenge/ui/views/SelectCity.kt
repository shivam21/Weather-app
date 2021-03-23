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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.models.CityItem
import com.example.androiddevchallenge.viewmodels.HomeViewModel
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.toPaddingValues

@Composable
fun SelectCity(navController: NavHostController, viewModel: HomeViewModel) {
    navController.enableOnBackPressed(true)
    val cityList = viewModel.cityList
    Scaffold {
        Column {
            TopAppBar(
                Modifier.padding(
                    top = LocalWindowInsets.current.systemBars.toPaddingValues()
                        .calculateTopPadding()
                ),
                backgroundColor = Color.White,
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable {
                                navController.navigateUp()
                            },
                        tint = colorResource(id = R.color.black)
                    )
                    Text(
                        text = stringResource(R.string.select_city),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
            LazyColumn {
                items(cityList) { item ->
                    CityItem(item) {
                        viewModel.setCurrentCityItem(item)
                        navController.navigateUp()
                    }
                }
            }
        }
    }
}

@Composable
fun CityItem(cityItem: CityItem, onCityItemClicked: (cityItem: CityItem) -> Unit) {
    Card(
        Modifier.height(150.dp).padding(10.dp).shadow(5.dp).semantics(true) {
            contentDescription = "city:${cityItem.cityName}"
        }.clickable {
            onCityItemClicked(cityItem)
        }
    ) {
        WeatherItem(cityItem)
        Column {
            Text(
                cityItem.cityName,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                color = Color.White
            )
            Text(
                "${cityItem.temperature}° ${cityItem.weather}",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                color = Color.White
            )
        }
    }
}
