package com.zj.weather.view.weather.widget

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zj.model.WeatherModel
import com.zj.model.room.entity.CityInfo
import com.zj.weather.R

@Composable
fun WeatherContent(
    modifier: Modifier = Modifier,
    cityInfo: CityInfo,
    weatherModel: WeatherModel?,
    isLand: Boolean = false,
    scrollState: ScrollState = rememberScrollState(),
    toCityMap: (Double, Double) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (!isLand) {
            // 天气头部
            HeaderWeather(
                cityInfo, weatherModel?.nowBaseBean, false, scrollState,toCityMap
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (!isLand) {
                // 天气动画
                WeatherAnimation(weatherModel?.nowBaseBean?.icon)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 当前空气质量
            AirQuality(weatherModel?.airNowBean)

            // 未来24小时天气预报
            HourWeather(weatherModel?.hourlyBeanList)

            // 未来7天天气预报
            WeekDayWeather(weatherModel?.dailyBeanList)

            // 当天具体天气数值
            DayWeatherContent(weatherModel)

            // 日出日落
            SunriseSunsetContent(weatherModel?.dailyBean)

            // 当天生活指数
            LifeWeatherContent(weatherModel?.weatherLifeList)

            // 数据源
            Text(
                text = stringResource(id = R.string.data_source),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 35.dp)
            )
        }
    }
}