/**
 * https://techdevguide.withgoogle.com/paths/foundational/hangman-challenge-archetypal/#!
 */

import acm.program.GraphicsProgram
import java.util.*


class Hangman : GraphicsProgram() {
    private val TOTAL_LIFE = 8

    private var count = TOTAL_LIFE
    private var inputCharacter : String? = ""

    private val HELLO_MESSAGE = "Welcome to Hangman!"
    private val CURRENT_STATUS_MESSAGE = "The word now looks like this: "
    private val GUESSES_LEFT_MESSAGE = "You have %count% guesses left."
    private val GUESSES_ONLY_ONE_LEFT_MESSAGE = "You have only one guess left."
    private val YOUR_GUESS_MESSAGE = "Your guess: "
    private val CORRECT_MESSAGE = "The guess is correct."
    private val INCORRECT_MESSAGE = "There are no %inputCharacter%'s in the word."

    //Win message
    private val WIN_ANSWER_MESSAGE = "You guessed the word: "
    private val WIN_MESSAGE = "You win."

    //Lose message
    private val HANG_MESSAGE = "You're completely hung"
    private val LOSE_ANSWER_MESSAGE = "The word was: "
    private val LOSE_MESSAGE = "You lose."

    private val COUNT = "%count%"
    private val INPUTCHARACTER = "%inputCharacter%"

    //Members
    private val index : Int = Math.round(Math.random()*(getWordCount() - 1)).toInt()
    private val answer : String = getWord(index)
    private var currentStatus = initializeCurrentStatus()

    private val canvas = HangmanCanvas()

    /** Runs the program  */
    override fun run() {
        canvas.reset()
        printHelloMessage()
        while (getCount() > 0) {
            printCurrentStatusMessage()
            canvas.displayWord(currentStatus)
            val input = getUserInputFromCommandLine()

            if (isCharacterInWord(input)) {
                printCorrectMessage()
            } else {
                printIncorrectMessage(input)
                decreaseCount()
                canvas.noteIncorrectGuess(input)
            }
            updateStatus(input)

            if (isRightAnswer()) {
                printWinnerMessage()
                canvas.displayWord(currentStatus)
                break
            }
        }
        if (isGameOver()) {
            printLoseMessage()
        }
    }

    override fun init() {
        add(canvas)
    }

    //Views Function
    fun printHelloMessage() {
        println(HELLO_MESSAGE)
    }

    fun printCurrentStatusMessage() {
        println(CURRENT_STATUS_MESSAGE+currentStatus)
        if (count == 1) {
            println(GUESSES_ONLY_ONE_LEFT_MESSAGE)
        } else {
            println(GUESSES_LEFT_MESSAGE.replace(COUNT, count.toString()))
        }
    }


    fun printCorrectMessage() {
        println(CORRECT_MESSAGE)
    }

    fun printIncorrectMessage(c : Char) {
        println(INCORRECT_MESSAGE.replace(INPUTCHARACTER, c.toString()))
    }

    fun printWinnerMessage() {
        println(WIN_ANSWER_MESSAGE+getWord(index))
        println(WIN_MESSAGE)
    }

    fun printLoseMessage() {
        println(HANG_MESSAGE)
        println(LOSE_ANSWER_MESSAGE+answer)
        println(LOSE_MESSAGE)
    }

    /**Return the character from command line*/
    fun getUserInputFromCommandLine() : Char{
        var validInput = false
        while (!validInput) {
            val scanner = Scanner(System.`in`)

            print(YOUR_GUESS_MESSAGE)

            inputCharacter = scanner.next()
            validInput = isSingleAlphabet(inputCharacter)
        }
        return inputCharacter!!.toCharArray()[0].toUpperCase()
    }

    private fun isSingleAlphabet(input : String?) : Boolean {
        if (input.isNullOrBlank())
            return false
        if (input!!.length != 1)
            return false
        if (input.matches("[a-zA-Z]".toRegex())) {
            return true
        }
        return false
    }


    /** Returns the number of words in the lexicon. */
    private fun getWordCount() : Int = 10

    /** Returns the word at the specified index. */
    private fun getWord(index : Int) : String {
        return when (index) {
            0 -> "BUOY"
            1 -> "COMPUTER"
            2 -> "CONNOISSEUR"
            3 -> "DEHYDRATE"
            4 -> "FUZZY"
            5 -> "HUBBUB"
            6 -> "KEYHOLE"
            7 -> "QUAGMIRE"
            8 -> "SLITHER"
            9 -> "ZIRCON"
            else -> {
                throw IllegalArgumentException("getWord: Illegal index")
            }
        }
    }

    /**Initialize current status to "------..."*/
    private fun initializeCurrentStatus() : String {
        return String().padStart(answer.length, '-')
    }

    /**Update the character (if any) to current status. i.e. : "A---A---" for input 'A'*/
    fun updateStatus(input: Char) {
        val currentStatusArray = currentStatus.toCharArray()
        for ((index, c) in answer.withIndex()) {
            if (input.toUpperCase() == c) {
                currentStatusArray[index] = input.toUpperCase()
            }
        }
        currentStatus = String(currentStatusArray)
    }

    /**Return true if c in answer*/
    fun isCharacterInWord(c : Char) : Boolean = answer.contains(c, true)

    /**Return if user guess the right answer*/
    fun isRightAnswer() : Boolean = (answer == currentStatus)

    fun getCount() : Int = count

    fun decreaseCount() {
        count--
    }

    fun isGameOver() : Boolean = (count == 0)
}

fun main(args: Array<String>) {
    Hangman().start()
}
