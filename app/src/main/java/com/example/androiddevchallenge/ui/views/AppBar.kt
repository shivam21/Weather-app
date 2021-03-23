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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.models.CityItem

@Composable
fun HomeTopAppBar(cityItemState: CityItem?, navigateToSelectCity: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(12.dp).padding(top = 30.dp)
    ) {
        Row(Modifier) {
            Icon(
                painterResource(R.drawable.ic_placeholder),
                "",
                tint = Color.White,
                modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
            )
            Column {
                Text(
                    cityItemState?.cityName.orEmpty(),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.White
                )
                Text(
                    cityItemState?.lastUpdated.orEmpty(),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.White
                )
            }
        }

        Icon(
            painterResource(R.drawable.ic_building), "",
            Modifier.align(Alignment.CenterEnd)
                .padding(end = 20.dp)
                .size(20.dp)
                .semantics(true) {
                    contentDescription = "selectCityIcon"
                }
                .clickable { navigateToSelectCity() },
            tint = Color.White
        )
    }
}
