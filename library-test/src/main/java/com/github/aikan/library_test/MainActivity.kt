package com.github.aikan.library_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.aikan.library_test.ui.theme.QuickjsandroidTheme
import com.hippo.quickjs.android.JSContext
import com.hippo.quickjs.android.JSRuntime
import com.hippo.quickjs.android.JSString
import com.hippo.quickjs.android.QuickJS
import kotlin.math.log

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
                    Greeting("Android"){
                        testClass()
                    }
                }
            }
        }
    }

    private fun testClass() {
        val qjs = QuickJS.Builder().build()
        val jsRt = qjs.createJSRuntime()
        val jsCtx = jsRt.createJSContext()
        val testFunc = jsCtx.createJSFunction { context, args ->
            println("Hello, world!")
            return@createJSFunction jsCtx.createJSUndefined()
        }
        createConsole(jsCtx)
        jsCtx.globalObject.setProperty("testFunc",testFunc)
        jsCtx.evaluate("""
            console.log("test")
            testFunc();
            """.trimIndent(),"main.js")
    }

    private fun createConsole(jsCtx:JSContext){
        val console = jsCtx.createJSObject()
        val logFunc = jsCtx.createJSFunction { context, args ->
            println(args[0].cast(JSString::class.java).string)
            return@createJSFunction jsCtx.createJSUndefined()
        }
        console.setProperty("log", logFunc)
        jsCtx.globalObject.setProperty("console",console)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onClick:()->Unit) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = { onClick.invoke() }) {
            Text(text = "Test")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuickjsandroidTheme {
//        Greeting("Android")
    }
}