package com.example.nln.AuthView

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.nln.ui.theme.cooperRegular

@Composable
fun TextField(
    label: String,
    value: String,
    isPassword: Boolean,
    OnValueChanged: (String) -> Unit
) {

    Spacer(modifier = Modifier.height(15.dp))
    OutlinedTextField(
        value = value,
        onValueChange = {
            OnValueChanged(it)
        },
        label = {
            Text(text = label, color = Color.Black)
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
        shape = RoundedCornerShape(30.dp),
        textStyle = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontFamily = cooperRegular
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
    )
}