package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.viewmodel.LoginViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import com.example.myapplication.ui.components.CustomTextField
import com.example.myapplication.ui.components.CustomButton
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToCreateUser: () -> Unit
) {
    val username by loginViewModel.username.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val loginResult by loginViewModel.loginResult.collectAsState()
    val isLoginSuccessful by loginViewModel.isLoginSuccessful.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.gps2),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(48.dp))


        NeumorphicBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            CustomTextField(
                value = username,
                onValueChange = { loginViewModel.onUsernameChange(it) },
                placeholder = stringResource(id = R.string.username_hint) // 👈 CAMBIO
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        NeumorphicBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            CustomTextField(
                value = password,
                onValueChange = { loginViewModel.onPasswordChange(it) },
                placeholder = stringResource(id = R.string.password_hint), // 👈 CAMBIO
                visualTransformation = PasswordVisualTransformation()
            )
        }

        TextButton(
            onClick = { /* Lógica para olvidar contraseña */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.forgot_password),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        val context = LocalContext.current

        // Botón de login
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            CustomButton(
                onClick = { loginViewModel.validateLogin(context) },
                text = stringResource(id = R.string.login_button),
            )
        }

        LaunchedEffect(isLoginSuccessful) {
            if (isLoginSuccessful) {
                onNavigateToHome()
            }
        }

        TextButton(onClick = { onNavigateToCreateUser() }) {
            Text(
                text = stringResource(id = R.string.create_account),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = loginResult, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
fun NeumorphicBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .drawBehind {
                val cornerRadius = 24.dp.toPx()
                val shadowColor = Color.Black.copy(alpha = 0.15f).toArgb()
                val shadowElevation = 8.dp.toPx()

                val paint = Paint().apply {
                    color = Color.White
                    asFrameworkPaint().apply {
                        this.color = shadowColor
                        setShadowLayer(
                            shadowElevation,
                            0f,
                            shadowElevation / 4f,
                            shadowColor
                        )
                    }
                }

                drawIntoCanvas {
                    it.drawRoundRect(
                        left = 0f,
                        top = 0f,
                        right = size.width,
                        bottom = size.height,
                        radiusX = cornerRadius,
                        radiusY = cornerRadius,
                        paint
                    )
                }
            }
            .background(Color.White, RoundedCornerShape(24.dp)),
        content = content
    )
}
