import acm.graphics.*

class HangmanCanvas : GCanvas() {
    /** Resets the display so that only the scaffold appears  */
    fun reset() {
        removeAll()
        add(scaffold)
    }

    /**
     * Updates the word on the screen to correspond to the current
     * state of the game. The argument string shows what letters have
     * been guessed so far; unguessed letters are indicated by hyphens.
     */
    fun displayWord(word: String) {
        currentStatus.label = word
        currentStatus.setFont("SansSerif-50")

        add(currentStatus, 0.0, 480.0)
    }

    /**
     * Updates the display to correspond to an incorrect guess by the
     * user. Calling this method causes the next body part to appear
     * on the scaffold and adds the letter to the list of incorrect
     * guesses that appears at the bottom of the window.
     */
    fun noteIncorrectGuess(letter: Char) {
        incorrectWords.setFont("SansSerif-20")
        incorrectString += letter
        incorrectWords.label = incorrectString
        add(incorrectWords, 0.0, 500.0)
        count++
        when (count) {
            1 -> addBeam()
            2 -> addRope()
            3 -> addHead()
            4 -> addBody()
            5 -> addLeftArm()
            6 -> addRightArm()
            7 -> addLeftLeg()
            8 -> addRightLeg()
        }
    }

    private fun addBeam() {
        add(beam)
    }

    private fun addRope() {
        add(rope)
    }

    private fun addHead() {
        add(head)
    }

    private fun addBody() {
        add(body)
    }

    private fun addLeftArm() {
        leftArm.add(leftUpperArm)
        leftArm.add(leftLowerArm)
        add(leftArm)
    }

    private fun addRightArm() {
        rightArm.add(rightUpperArm)
        rightArm.add(rightLowerArm)
        add(rightArm)
    }

    private fun addLeftLeg() {
        leftLeg.add(leftHip)
        leftLeg.add(leftLowerLeg)
        leftLeg.add(leftFoot)
        add(leftLeg)
    }

    private fun addRightLeg() {
        rightLeg.add(rightHip)
        rightLeg.add(rightLowerLeg)
        rightLeg.add(rightFoot)
        add(rightLeg)
    }

    /* Constants for the simple version of the picture (in pixels) */
    private val SCAFFOLD_HEIGHT = 360
    private val BEAM_LENGTH = 144
    private val ROPE_LENGTH = 18
    private val HEAD_RADIUS = 36
    private val BODY_LENGTH = 144
    private val ARM_OFFSET_FROM_HEAD = 28
    private val UPPER_ARM_LENGTH = 72
    private val LOWER_ARM_LENGTH = 44
    private val HIP_WIDTH = 36
    private val LEG_LENGTH = 108
    private val FOOT_LENGTH = 28

    private val originalPoint = Pair(0.0, 420.0)

    private val scaffold = GLine(originalPoint.first, originalPoint.second,
            originalPoint.first,    (originalPoint.second - SCAFFOLD_HEIGHT))

    private val beam = GLine(scaffold.endPoint.x, scaffold.endPoint.y,
            scaffold.endPoint.x + BEAM_LENGTH, scaffold.endPoint.y)

    private val rope = GLine(beam.endPoint.x, beam.endPoint.y,
            beam.endPoint.x, beam.endPoint!!.y + ROPE_LENGTH)

    private val head = GOval(rope.endPoint.x - HEAD_RADIUS, rope.endPoint.y,
            HEAD_RADIUS*2.0,  HEAD_RADIUS*2.0)

    private val body = GLine(head.x + HEAD_RADIUS, head.y  + HEAD_RADIUS*2,
            head.x + HEAD_RADIUS, head.y + HEAD_RADIUS*2 + BODY_LENGTH)

    private val leftUpperArm = GLine(body.x, body.y + HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
            body.x - UPPER_ARM_LENGTH, body.y + HEAD_RADIUS + ARM_OFFSET_FROM_HEAD)

    private val leftLowerArm = GLine(leftUpperArm.endPoint.x, leftUpperArm.endPoint.y,
            leftUpperArm.endPoint.x, leftUpperArm.endPoint.y + LOWER_ARM_LENGTH)


    private val rightUpperArm = GLine(body.x, body.y + HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
            body.x + UPPER_ARM_LENGTH, body.y + HEAD_RADIUS + ARM_OFFSET_FROM_HEAD)

    private val rightLowerArm = GLine(rightUpperArm.endPoint.x, rightUpperArm.endPoint.y,
            rightUpperArm.endPoint.x, rightUpperArm.endPoint.y + LOWER_ARM_LENGTH)

    private val leftHip = GLine(body.endPoint.x, body.endPoint.y,
            body.endPoint.x - HIP_WIDTH, body.endPoint.y)

    private val leftLowerLeg = GLine(leftHip.endPoint.x, leftHip.endPoint.y,
            leftHip.endPoint.x, leftHip.endPoint.y + LEG_LENGTH)

    private val leftFoot = GLine(leftLowerLeg.endPoint.x, leftLowerLeg.endPoint.y,
            leftLowerLeg.endPoint.x - FOOT_LENGTH, leftLowerLeg.endPoint.y)

    private val rightHip = GLine(body.endPoint.x, body.endPoint.y,
            body.endPoint.x + HIP_WIDTH, body.endPoint.y)

    private val rightLowerLeg = GLine(rightHip.endPoint.x, rightHip.endPoint.y,
            rightHip.endPoint.x, rightHip.endPoint.y + LEG_LENGTH)

    private val rightFoot = GLine(rightLowerLeg.endPoint.x, rightLowerLeg.endPoint.y,
            rightLowerLeg.endPoint.x + FOOT_LENGTH, rightLowerLeg.endPoint.y)

    private val leftArm = GCompound()
    private val rightArm = GCompound()
    private val leftLeg = GCompound()
    private val rightLeg = GCompound()

    private val currentStatus = GLabel("")
    private val incorrectWords = GLabel("")

    private var incorrectString : String = ""
    private var count = 0
}