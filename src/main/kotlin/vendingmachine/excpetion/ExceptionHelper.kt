package vendingmachine.excpetion

import java.lang.Exception
import kotlin.reflect.KClass

private const val ERROR_MESSAGE = "[ERROR]"
fun convertExceptionMessage(message: String) : String{
    return "$ERROR_MESSAGE $message"
}

inline fun (() -> Unit).multiCatch(vararg exceptions: KClass<out Throwable>, thenDo: (ex : Exception) -> Unit) {
    try {
        this()
    } catch (ex: Exception) {
        if (ex::class in exceptions)
            thenDo(ex)
    }
}