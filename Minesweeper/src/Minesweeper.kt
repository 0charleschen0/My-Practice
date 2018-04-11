/**
 * https://techdevguide.withgoogle.com/paths/foundational/coding-question-minesweeper/#!
 */

class Minesweeper(private val row: Int, private val col: Int, private val numberOfMine: Int) {

    private var gameField : GameField = GameField(row, col)
    init {
        if (shouldInitializeWithMine()) {
            gameField.initialize(true)
        } else {
            gameField.initialize()
        }
    }

    fun deployMines() {
        val iterationCount: Int
        = when (shouldInitializeWithMine()) {
            true -> row * col - numberOfMine
            false -> numberOfMine
        }

        if (shouldInitializeWithMine()) {
            for (i in (0..iterationCount)) {
                var x = Math.round(Math.random() * (row - 1)).toInt()
                var y = Math.round(Math.random() * (col - 1)).toInt()
                while (!gameField.isMine(x, y)) {
                    x = Math.round(Math.random() * (row - 1)).toInt()
                    y = Math.round(Math.random() * (col - 1)).toInt()
                }
                gameField.removeMine(x, y)
            }
        } else {
            for (i in (0..iterationCount)) {
                var x = Math.round(Math.random() * (row - 1)).toInt()
                var y = Math.round(Math.random() * (col - 1)).toInt()
                while (gameField.isMine(x, y)) {
                    x = Math.round(Math.random() * (row - 1)).toInt()
                    y = Math.round(Math.random() * (col - 1)).toInt()
                }
                gameField.deployMine(x, y)
            }
        }
    }

    fun initField() {
        for (i in (0..(row-1))) {
            for (j in (0..(col-1))) {
                if (gameField.isMine(i, j)) {
                    continue
                }
                gameField.set(i, j, gameField.getAdjacentMineNumber(i, j))
            }
        }
    }

    fun dumpCurrentStatus() {
        gameField.dumpCurrentStatus()
    }

    fun shouldInitializeWithMine() : Boolean = numberOfMine > row*col/2

    private class GameField(private val row: Int, private val col: Int) {

        private val MINE = 9
        private val LAND = 0

        private val COL = true
        private val ROW = false

        private var field : IntArray = IntArray(row*col)

        fun initialize(withMines : Boolean = false) {
            if (withMines) {
                field.fill(MINE)
            } else {
                field.fill(0)
            }
        }

        fun deployMine(pair : Pair<Int, Int>) {
            try {
                field[index(pair)] = MINE
            } catch (e : ArrayIndexOutOfBoundsException) {
                println("deployMine():" + pair.toString())
                throw e
            }
        }

        fun deployMine(x : Int, y : Int) {
            deployMine(Pair(x, y))
        }

        fun removeMine(pair : Pair<Int, Int>) {
            field[index(pair)] = LAND
        }

        fun removeMine(x : Int, y : Int) {
            removeMine(Pair(x, y))
        }

        fun isMine(x : Int, y : Int) = isMine(Pair(x, y))

        private fun isMine(pair : Pair<Int, Int>) : Boolean{
            try {
                return field[index(pair)] == MINE
            } catch (e : ArrayIndexOutOfBoundsException ) {
                println("isMine():" + pair.toString())
                throw e
            }
        }

        fun isBoundary(x : Int, y : Int) : Boolean {
            return x < 0 ||  x > row
                || y < 0 || y > col
        }

        fun getAdjacentMineNumber(x : Int, y : Int) : Int{
            var count = 0
            for (i in (sanitizeRow(x - 1)..sanitizeRow(x + 1))) {
                for (j in (sanitizeCol(y - 1)..sanitizeCol(y + 1))) {
                    if(isMine(i, j)) {
                        count++
                    }
                }
            }
            return count
        }

        fun sanitizeRow(index : Int) = sanitize(index, COL)

        fun sanitizeCol(index : Int) = sanitize(index, COL)

        private fun sanitize(index : Int, flag : Boolean) : Int {
            if (index < 0)
                return 0

            if (flag == ROW && index > row-1) {
                return row-1
            }


            if (flag == COL && index > col-1) {
                return col-1
            }

            return index
        }

        fun get(pair : Pair<Int, Int>) : Int{
            return field[index(pair)]
        }

        fun set(x: Int, y: Int, value: Int) {
            set(Pair(x, y), value)
        }

        fun set(pair : Pair<Int, Int>, value : Int) {
            field[index(pair)] = value
        }

        fun index(pair : Pair<Int, Int>) : Int = pair.first + col*pair.second

        fun dumpCurrentStatus() {
            println("===== Current Status: =====")
            for (i in (0..(row-1))) {
                for (j in (0..(col-1))) {
                    if (isMine(i, j)) {
                        print("* ")
                    } else {
                        print(field[index(Pair(i, j))].toString() + " ")
                    }
                }
                println()
            }
        }
    }
}


fun main(args : Array<String>) {
    if (args.size != 3) {
        return
    }

    val row = args[0].toInt()
    val col = args[1].toInt()
    val mines = args[2].toInt()

    val game = Minesweeper(row, col, mines)

    game.deployMines()
    game.initField()
    game.dumpCurrentStatus()
}