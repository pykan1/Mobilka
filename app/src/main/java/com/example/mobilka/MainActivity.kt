package com.example.mobilka

import android.app.Person
import android.app.Service
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilka.ui.theme.LightBlue
import com.example.mobilka.ui.theme.MobilkaTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobilkaTheme {
                DefaultPreview()
            }
        }
    }
}

val retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.147.245:8080/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ApiService {
    @GET("all_sports")
    suspend fun getSport(): String

    @GET("all_person")
    suspend fun getPerson(): String

    @GET("all_country")
    suspend fun getCountry(): String

    @GET("all_country/{sport}")
    suspend fun getCountryPerson(sport: String): String
}

val apiService = retrofit.create(ApiService::class.java)

@Composable
fun SportList() {
    var sports by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        sports = apiService.getSport()

    }
    Column(modifier = Modifier.fillMaxSize()) {
        for (s in sports.split(",").dropLast(1)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = s.replace("[", "").replace("'", "")
            )
        }
    }
}

@Composable
fun Country_sport(sport: String) {
    var sports by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        sports = apiService.getSport()

    }
    Column(modifier = Modifier.fillMaxSize()) {
        for (s in sports.split(",").dropLast(1)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = s.replace("[", "").replace("'", "")
            )
        }
    }
}

@Composable
fun PersonList() {
    var sports by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        sports = apiService.getPerson()

    }
    Column(modifier = Modifier.fillMaxSize()) {
        for (s in sports.split(",").dropLast(1)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = s.replace("[", "").replace("'", "")
            )
        }
    }
}


@Composable
fun CountryList() {
    var sports by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        sports = apiService.getCountry()

    }
    Column(modifier = Modifier.fillMaxSize()) {
        for (s in sports.split(",").dropLast(1)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = s.replace("[", "").replace("'", "")
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var national_b by remember { mutableStateOf(false) }
    var all_b by remember { mutableStateOf(false) }
    var sport_b by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    all_b = !all_b
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 100.dp, minHeight = 50.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Text(text = "Все участники")
            }
            if (all_b) {
                PersonList()
            }
            Button(
                onClick = { sport_b = !sport_b },
                modifier = Modifier
                    .defaultMinSize(minWidth = 100.dp, minHeight = 50.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Text(text = "Вид спорта")
            }
            if (sport_b) {
                SportList()
            }
            Button(
                onClick = {
                    national_b = !national_b
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 100.dp, minHeight = 50.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Text(text = "Национальность")
            }
            if (national_b){
                CountryList()
            }
        }
    }
}
