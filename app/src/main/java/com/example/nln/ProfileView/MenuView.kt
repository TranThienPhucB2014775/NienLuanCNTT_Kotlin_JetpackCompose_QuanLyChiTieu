package com.example.nln.ProfileView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nln.R
import com.example.nln.ViewModels.AuthTokenViewModel
import com.example.nln.data.AuthToken
import com.example.nln.ui.theme.cooperRegular

@Composable
fun MenuView(
    authTokenViewModel: AuthTokenViewModel,
    logout: () -> Unit,
    isGetPicture: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
    ) {
        Row(
            Modifier
                .padding(top = 30.dp, start = 10.dp, end = 10.dp, bottom = 30.dp)

        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.clickable { isGetPicture() }
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(84.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_account_box_24),
                        contentDescription = "",
                        tint = Color(0xFFFA6CB4),
                        modifier = Modifier.height(40.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Avatar", fontWeight = FontWeight.Normal,
                        fontFamily = cooperRegular,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(35.dp))

            Box(
                contentAlignment = Alignment.Center,
//                modifier = Modifier.padding(start = 25.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(84.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_notifications_none_24),
                        contentDescription = "",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Notifications", fontWeight = FontWeight.Normal,
                        fontFamily = cooperRegular,
                        fontSize = 14.sp,
                    )
                }


            }

            Spacer(modifier = Modifier.width(35.dp))

            Box(
                contentAlignment = Alignment.Center,
//                modifier = Modifier.padding(start = 25.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(84.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_privacy_tip_24),
                        contentDescription = "",
                        tint = Color(0xFFF9971EE)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Privacy", fontWeight = FontWeight.Normal,
                        fontFamily = cooperRegular,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Divider(
            modifier = Modifier.height(1.dp),
            color = Color.Black
        )

        Row(
            Modifier.padding(top = 30.dp, start = 10.dp, end = 10.dp, bottom = 40.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
//                modifier = Modifier.padding(start = 25.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(84.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_info_24),
                        contentDescription = "",
                        tint = Color(0xFFF9971EE)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "About", fontWeight = FontWeight.Normal,
                        fontFamily = cooperRegular,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(35.dp))

            Box(
                contentAlignment = Alignment.Center,
//                modifier = Modifier.padding(start = 25.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(84.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_settings_24),
                        contentDescription = "",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Setting", fontWeight = FontWeight.Normal,
                        fontFamily = cooperRegular,
                        fontSize = 14.sp
                    )
                }


            }

            Spacer(modifier = Modifier.width(35.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.clickable {
                    logout()
                }
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(84.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_logout_24),
                        contentDescription = "",
                        tint = Color(0xFFFA6CB4)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Log out",
                        fontWeight = FontWeight.Normal,
                        fontFamily = cooperRegular,
                        fontSize = 14.sp
                    )
                }


            }
        }

    }
}