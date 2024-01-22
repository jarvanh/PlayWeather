package com.zj.weather.view.weather.widget

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zj.model.room.entity.CityInfo
import com.zj.model.weather.WeatherNowBean
import com.zj.weather.R
import com.zui.animate.placeholder.placeholder

@Composable
fun HeaderWeather(
    cityInfo: CityInfo,
    weatherNow: WeatherNowBean.NowBaseBean?,
    isLand: Boolean = false,
    scrollState: ScrollState? = null,
    toCityMap: (Double, Double) -> Unit,
) {
    val fontSize = if (scrollState == null || isLand) {
        50.sp
    } else {
        (50f / (scrollState.value / 2) * 70).coerceAtLeast(20f).coerceAtMost(45f).sp
    }
    val topPadding = if (scrollState == null || isLand) {
        50.dp
    } else {
        (50f / (scrollState.value / 2) * 70).coerceAtLeast(0f).coerceAtMost(45f).dp
    }
    val cityName = if (cityInfo.city.length > 5 || cityInfo.name.length > 5) {
        cityInfo.name
    } else {
        "${cityInfo.city} ${cityInfo.name}"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = topPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .width(200.dp)
                .clickable {
                    toCityMap(cityInfo.lat.toDouble(), cityInfo.lon.toDouble())
                },
            text = cityName,
            fontSize = 30.sp,
            color = MaterialTheme.colors.primary,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "${weatherNow?.text ?: stringResource(id = R.string.default_weather)}  ${weatherNow?.temp ?: "0"}℃",
            modifier = Modifier
                .padding(top = 5.dp, bottom = 10.dp)
                .placeholder(weatherNow),
            fontSize = fontSize,
            color = MaterialTheme.colors.primary
        )
    }
}

@Preview(showBackground = false, name = "未收起时的头部")
@Composable
fun HeaderWeatherPreview() {
    val nowBean = WeatherNowBean.NowBaseBean()
    nowBean.text = "多云"
    nowBean.temp = "25"
    HeaderWeather(CityInfo(name = "测试"), nowBean) { _, _ ->
    }
}