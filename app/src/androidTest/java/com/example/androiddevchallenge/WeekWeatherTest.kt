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
package com.example.androiddevchallenge

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androiddevchallenge.repository.CityRepository
import com.example.androiddevchallenge.ui.views.WeekWeather
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeekWeatherTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun isTodayExist() {
        composeTestRule.setContent {
            WeekWeather(CityRepository.getCityList()[0].weekWeatherList)
        }

        composeTestRule.onAllNodesWithText("Today")[0].assertExists()
    }

    @Test
    fun isTomorrowDateExistExist() {
        composeTestRule.setContent {
            WeekWeather(CityRepository.getCityList()[0].weekWeatherList)
        }

        composeTestRule.onAllNodesWithText("Mar 24")[0].assertExists()
    }
}
