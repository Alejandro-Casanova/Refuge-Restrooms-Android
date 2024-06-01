package com.example.refugerestrooms.ui.navigation

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.*

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}

///**
// * This [Composable] can be used with a [LocalBackPressedDispatcher] to intercept a back press.
// *
// * @param onBackPressed (Event) What to do when back is intercepted
// *
// */
//@Composable
//fun BackPressHandler(onBackPressed: () -> Unit) {
//    // Safely update the current `onBack` lambda when a new one is provided
//    val currentOnBackPressed by rememberUpdatedState(onBackPressed)
//
//    // Remember in Composition a back callback that calls the `onBackPressed` lambda
//    val backCallback = remember {
//        object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                currentOnBackPressed()
//            }
//        }
//    }
//
//    val backDispatcher = LocalBackPressedDispatcher.current
//
//    // Whenever there's a new dispatcher set up the callback
//    DisposableEffect(backDispatcher) {
//        backDispatcher.addCallback(backCallback)
//        // When the effect leaves the Composition, or there's a new dispatcher, remove the callback
//        onDispose {
//            backCallback.remove()
//        }
//    }
//}
//
///**
// * This [CompositionLocal] is used to provide an [OnBackPressedDispatcher]:
// *
// * ```
// * CompositionLocalProvider(
// *     LocalBackPressedDispatcher provides requireActivity().onBackPressedDispatcher
// * ) { }
// * ```
// *
// * and setting up the callbacks with [BackPressHandler].
// */
//val LocalBackPressedDispatcher =
//    staticCompositionLocalOf<OnBackPressedDispatcher> { error("No Back Dispatcher provided") }