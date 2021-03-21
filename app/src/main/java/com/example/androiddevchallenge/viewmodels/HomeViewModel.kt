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
package com.example.androiddevchallenge.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.models.CityItem
import com.example.androiddevchallenge.repository.CityRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class HomeViewModel : ViewModel() {

    val cityList: List<CityItem>
        get() = CityRepository.getCityList()

    private val _currentCityItem = MutableLiveData(cityList[0])
    val currentCityItem: LiveData<CityItem> = _currentCityItem

    fun setCurrentCityItem(cityItem: CityItem) {
        _currentCityItem.postValue(cityItem)
    }

    fun getDay(index: Int): String {
        return if (index == 0) "Today" else {
            val gc = GregorianCalendar()
            gc.add(Calendar.DATE, index)
            return SimpleDateFormat("EEE", Locale.ROOT).format(gc.time)
        }
    }

    fun getDate(index: Int): String {
        val gc = GregorianCalendar()
        gc.add(Calendar.DATE, index)
        return SimpleDateFormat("MMM dd", Locale.ROOT).format(gc.time)
    }

    init {
        Log.d("TAGVIEWMPDEL", "new instance created: ")
    }
}
