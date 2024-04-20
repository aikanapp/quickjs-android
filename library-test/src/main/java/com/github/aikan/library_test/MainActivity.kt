package com.github.aikan.library_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.aikan.library_test.ui.theme.QuickjsandroidTheme
import com.hippo.quickjs.android.QuickJS

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickjsandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    fun testClass() {
        val qjs = QuickJS.Builder().build()
        val jsRt = qjs.createJSRuntime()
        val jsCtx = jsRt.createJSContext()
        jsCtx.createJSFunction { context, args ->
            println("Hello, world!")
            return@createJSFunction jsCtx.createJSUndefined()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuickjsandroidTheme {
        Greeting("Android")
    }
}