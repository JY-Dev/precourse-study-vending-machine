package vendingmachine.excpetion

private const val ERROR_MESSAGE = "[ERROR]"
fun convertExceptionMessage(message: String) : String{
    return "$ERROR_MESSAGE $message"
}