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

package com.zj.weather

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zj.weather.common.PlayActions
import com.zj.weather.common.PlayDestinations
import com.zj.weather.common.setComposable
import com.zj.weather.ui.view.city.CityListPage
import com.zj.weather.ui.view.city.viewmodel.CityListViewModel
import com.zj.weather.ui.view.list.WeatherListPage
import com.zj.weather.ui.view.list.viewmodel.WeatherListViewModel
import com.zj.weather.ui.view.weather.WeatherViewPager
import com.zj.weather.ui.view.weather.viewmodel.WeatherViewModel


@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun NavGraph(
    startDestination: String = PlayDestinations.HOME_PAGE_ROUTE,
) {
    val navController = rememberAnimatedNavController()
    val actions = remember(navController) { PlayActions(navController) }
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        setComposable(PlayDestinations.HOME_PAGE_ROUTE) {
            val weatherViewModel = hiltViewModel<WeatherViewModel>()
            weatherViewModel.refreshCityList()
            WeatherViewPager(
                weatherViewModel = weatherViewModel,
                toCityList = actions.toCityList,
                toWeatherList = actions.toWeatherList
            )
        }
        setComposable(PlayDestinations.WEATHER_LIST_ROUTE) {
            val weatherListViewModel = hiltViewModel<WeatherListViewModel>()
            weatherListViewModel.getGeoTopCity()
            WeatherListPage(
                weatherListViewModel = weatherListViewModel,
                onBack = actions.upPress,
                toWeatherDetails = actions.upPress
            )
        }
        setComposable(PlayDestinations.CITY_LIST_ROUTE) {
            val cityListViewModel = hiltViewModel<CityListViewModel>()
            cityListViewModel.refreshCityList()
            CityListPage(
                cityListViewModel = cityListViewModel,
                onBack = actions.upPress,
                toWeatherDetails = actions.upPress
            )
        }
    }
}