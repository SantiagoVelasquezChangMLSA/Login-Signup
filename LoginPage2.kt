package com.example.simplelogin.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.simplelogin.R
import com.example.simplelogin.data.LoginUserRequest
import androidx.compose.ui.graphics.Color


import com.example.simplelogin.service.UserService
import com.example.simplelogin.viewmodel.UserViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginPage(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val userviewModel = UserViewModel(UserService.instance)


    val name = remember {
        mutableStateOf("")
    }

    val matricula = remember {
        mutableStateOf("")
    }

    val loginState = userviewModel.login.observeAsState()


    LaunchedEffect(key1 = loginState.value?.message){

        loginState.value?.let {
            if(loginState.value!!.login){
                navController.navigate("WelcomeScreenPage")
                snackbarHostState.showSnackbar(it.message)
            }
            else {
                snackbarHostState.showSnackbar(it.message)
            }



        }
    }

    Scaffold(

        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            )
        }

    ) {
        val configuration = LocalConfiguration.current
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val backgroundImage = if (isPortrait) {
            painterResource(id = R.drawable.na_portrait)
        } else {
            painterResource(id = R.drawable.na_landscape)
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = backgroundImage,
                contentDescription = null, // Decorative image doesn't require a content description
                contentScale = ContentScale.FillBounds, // This will make the image fill the Box
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("INICIO DE SESION", fontSize = 35.sp, fontWeight = FontWeight.Bold, color = Color.White)

            OutlinedTextField(
                value = name.value,
                onValueChange = {
                name.value = it
                },
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                shape = RoundedCornerShape(45.dp),
                placeholder = {
                Text("Nombre")
                },
                //visualTransformation = NameVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(
                value = matricula.value,
                onValueChange = {
                    matricula.value = it
                },
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                shape = RoundedCornerShape(45.dp),
                placeholder = {
                    Text("Matrícula")
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )

            Button(onClick = {

                val user = LoginUserRequest(name.value, matricula.value)
                userviewModel.loginUser(user)


            }) {
                Text(text = "Ingresar")
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { navController.navigate("RegisterPage") }
                    .padding(8.dp)
            ) {
                Text(
                    text = "¿No tienes cuenta? porfa",
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Text(
                    text = "Regístrate",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(100.dp))


        }

    }
}
