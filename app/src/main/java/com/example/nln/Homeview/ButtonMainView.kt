package com.example.nln.Homeview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nln.R
import com.example.nln.ui.theme.cooperRegular

@Composable
fun ButtonMainViewRight(income: () -> Unit) {
    Row(
        Modifier
            .padding(end = 10.dp)
            .shadow(
                elevation = 5.dp,
                ambientColor = Color.Gray,
                spotColor = Color.Black,
                shape = RoundedCornerShape(50.dp),
            )
    ) {
        Box(
            modifier = Modifier

//                .size(175.dp, 100.dp)
                .height(100.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFF28263), Color(0xFFF58458)),
                        startX = 100f,
                        endX = 220f
                    ), shape = RoundedCornerShape(50.dp)
                )
                .clickable {
                    income()
                },
            contentAlignment = Alignment.Center
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .size(80.dp, 80.dp)
                        .background(
                            Color(0xFFFEA37E), shape = RoundedCornerShape(50.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_outward_24),
                        contentDescription = "",
                        Modifier
                            .size(50.dp)
                            .rotate(180.0F),
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "INC",
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                    fontFamily = cooperRegular,
                    fontSize = 28.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }

}

@Composable
fun ButtonMainViewLeft(expenditure: () -> Unit) {
    Row(
        Modifier
            .padding(start = 10.dp)
            .shadow(
                elevation = 5.dp,
                ambientColor = Color.Gray,
                spotColor = Color.Black,
                shape = RoundedCornerShape(50.dp),
            )
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
//                .size(175.dp, 100.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFF28263), Color(0xFFF58458)),
                        startX = 100f,
                        endX = 220f
                    ), shape = RoundedCornerShape(50.dp)
                )
                .clickable {
                    expenditure()
                },
            contentAlignment = Alignment.Center
        ) {
            Row {
                Text(
                    text = "EXP", fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                    fontFamily = cooperRegular,
                    fontSize = 28.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp, 80.dp)
                        .background(
                            Color(0xFFFEA37E), shape = RoundedCornerShape(50.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_outward_24),
                        contentDescription = "",
                        Modifier.size(50.dp),
                        tint = Color.Red
                    )
                }

            }

        }
    }
}