class State {
    companion object {
        const val INIT = 0
        const val SEARCH_HILL = 1
        const val CALCULATE_LAKE_VALUE = 2
    }
}

fun main(args: Array<String>) {

    val island = args[0].split(",").map { it -> it.toInt() }

    var currentState = State.INIT
    var lastState = currentState
    var highestHill = 0
    var totalLakeVolume = 0
    var currentLakeVolume = 0

    for ((index, bar) in island.withIndex()) {

        val lastBar = when(index) {
            0 -> 0
            else -> island[index - 1]
        }

        when(lastState) {
            (State.SEARCH_HILL)-> {
                currentState =
                    if (bar >= lastBar) {
                        State.SEARCH_HILL
                    } else {
                        State.CALCULATE_LAKE_VALUE
                    }
            }
            (State.CALCULATE_LAKE_VALUE)-> {
                if (bar > highestHill) {
                    currentState = State.SEARCH_HILL
                    totalLakeVolume += currentLakeVolume
                    currentLakeVolume = 0
                }
            }
            else -> {
                currentState = State.SEARCH_HILL
            }
        }

        when(currentState) {
            (State.SEARCH_HILL)-> {
                highestHill = Math.max(highestHill, bar)
            }
            (State.CALCULATE_LAKE_VALUE) -> {
                currentLakeVolume += (highestHill - bar)
            }
        }

        lastState = currentState
    }

    //Reset Value
    currentState = State.INIT
    lastState = currentState
    highestHill = 0
    currentLakeVolume = 0

    for ((index, bar) in island.reversed().withIndex()) {

        val lastBar = when(index) {
            0 -> 0
            else -> island[index - 1]
        }

        when(lastState) {
            (State.SEARCH_HILL)-> {
                currentState =
                        if (bar >= lastBar) {
                            State.SEARCH_HILL
                        } else {
                            State.CALCULATE_LAKE_VALUE
                        }
            }
            (State.CALCULATE_LAKE_VALUE)-> {
                if (bar > highestHill) {
                    currentState = State.SEARCH_HILL
                    totalLakeVolume += currentLakeVolume
                    currentLakeVolume = 0
                }
            }
            else -> {
                currentState = State.SEARCH_HILL
            }
        }

        when(currentState) {
            (State.SEARCH_HILL)-> {
                highestHill = Math.max(highestHill, bar)
            }
            (State.CALCULATE_LAKE_VALUE) -> {
                currentLakeVolume += (highestHill - bar)
            }
        }

        lastState = currentState
    }

    print(totalLakeVolume)
}