package com.example.myapplication.ui.components

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.myapplication.utils.RequestResult
import kotlinx.coroutines.delay


@Composable
fun OperationResultHandler(
    result: RequestResult?,
    onSuccess: suspend () -> Unit,
    onFailure: suspend () -> Unit,
){
    when(result){
        is RequestResult.Loading -> {
            LinearProgressIndicator()
        }
        is RequestResult.Success -> {
            Text(
                text = result.message
            )
        }
        is RequestResult.Failure -> {
            Text(
                text = result.errorMessage
            )
        }
        else -> {}
    }
    LaunchedEffect(result){
        when(result){
            is RequestResult.Success -> {
                delay(800)
                onSuccess()
            }
            is RequestResult.Failure -> {
                delay(2000)
                onFailure()
            }
            else -> {}
        }
    }

}