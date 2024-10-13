package com.example.lemonadeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonadeapp.ui.theme.LemonadeAppTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AndroidApp()
                }
            }
        }
    }
}
@Composable
fun AndroidApp(){
    val lemonTreeImage = painterResource(R.drawable.lemon_tree)
    val lemonImage = painterResource(R.drawable.lemon_squeeze)
    val LemonadeGlassImage = painterResource(R.drawable.lemon_drink)
    val emptyGlassImage = painterResource(R.drawable.lemon_restart)
    val lemonTreeText = stringResource(R.string.lemon_tree_text)
    val lemonText = stringResource(R.string.lemon_text)
    val lemonadeGlassText = stringResource(R.string.lemonade_glass_text)
    val emptyGlassText = stringResource(R.string.empty_glass_text)
    //Adım takip etme değişkenidir.
    /*
    * 1.Adım: Limon Ağacı
    * 2.Adım: Limon Sıkma,
    * 3.Adım: Limonata İçme
    * 4.Adım: Boş bardak.(Tekrar başa dönme adımı)
    * */
    val step = remember{mutableStateOf(1)}
    //Limon resminin üstüne kaç kez tıklandığını gösterir.
    val squeezeCount = remember{mutableStateOf(0)}
    //Limonun üstüne kaç kere tıklandıktan sonra sıkılacağını gösterir.
    val requiredSqueezes = remember { mutableStateOf(Random.nextInt(2, 5)) }
    when(step.value){
        1 -> LemonadeScreen(
            image = lemonTreeImage,
            text = lemonTreeText,
            onButtonClick = {step.value = 2},
            modifier = Modifier
        )
        2 -> LemonadeScreen(
            image = lemonImage,
            text = lemonText,
            onButtonClick = {
                squeezeCount.value++
                if (squeezeCount.value >= requiredSqueezes.value) {
                    step.value = 3 // Proceed to the next step after enough squeezes
                    squeezeCount.value = 0 // Reset for next use
                    requiredSqueezes.value = Random.nextInt(2, 5) // Generate a new random number for the next round
                }

            },
            modifier = Modifier,
        )
        3 -> LemonadeScreen(
            image = LemonadeGlassImage,
            text = lemonadeGlassText,
            onButtonClick = {step.value = 4},
            modifier = Modifier
        )
        else -> LemonadeScreen(
            image = emptyGlassImage,
            text = emptyGlassText,
            onButtonClick = {step.value = 1},
            modifier = Modifier
        )

    }


}
@Composable
fun LemonadeScreen(image: Painter, text:String, onButtonClick : ()-> Unit, modifier:Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            modifier = modifier
                .background(color = Color(0xFFF4D03F))
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.appName),
                fontWeight = FontWeight.Bold,
                lineHeight = 100.sp,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)

            )
        }
        Spacer(modifier = Modifier.height(200.dp))
        Button(
            onClick = { onButtonClick()},
            modifier = Modifier
                .size(300.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = (Color(0x80B2E0D4))),
            shape = RectangleShape,

        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)



            )
        }
        Text(
            text = text,
            modifier = Modifier
                .padding(16.dp)
        )
    }

}