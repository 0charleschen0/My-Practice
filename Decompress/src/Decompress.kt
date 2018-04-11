import java.util.*
import kotlin.collections.ArrayList

/**
 * https://techdevguide.withgoogle.com/paths/advanced/compress-decompression#!
 **/

class Decompress(private var string: String) {
    private val INIT_STATE          = 1 shl 0
    private val DIGIT_STATE         = 1 shl 1
    private val LETTER_STATE        = 1 shl 2

    private var currentState = INIT_STATE
    private var previousState = currentState
    private val stack : Stack<Pair<String, Int>> = Stack()

    private lateinit var stringList : List<String>

    init {
        preProcess()

    }

    private fun preProcess() {
        string = "1[$string]"
        stringList = string.split("[","]")
        stringList = stringList.dropLastWhile { s -> s.isEmpty() }
    }

    fun parse() : String {

        for (s in stringList) {
            currentState = if (s.isEmpty()) {
                LETTER_STATE
            } else {
                when {
                    s[0].isLetter() -> LETTER_STATE
                    s[0].isDigit() -> DIGIT_STATE
                    else -> LETTER_STATE
                }
            }

            when (currentState) {
                DIGIT_STATE -> {
                    stack.push(Pair(s, DIGIT_STATE))
                }
                LETTER_STATE -> {
                    if (previousState == DIGIT_STATE) {
                        val number = stack.pop().first.toInt()
                        stack.push(Pair(s.repeat(number), LETTER_STATE))
                    } else {

                        var tempString = s
                        while (stack.peek().second == LETTER_STATE) {
                            tempString = stack.pop().first + tempString
                        }
                        stack.push(Pair(tempString, LETTER_STATE))
                    }
                }
            }

            previousState = currentState
        }

        var result = ""

        while (stack.isNotEmpty()) {
            val remainingStack = stack.pop()
            if (remainingStack.second == DIGIT_STATE) {
                val number = remainingStack.first.toInt()
                result = result.repeat(number)
            } else {
                result += remainingStack.first
            }
        }
        return result
    }
}

fun main(args : Array<String>) {
    val decompress = Decompress(args[0])
    println(decompress.parse())
}