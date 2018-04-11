fun distributeCandies(candies: IntArray): Int {
    val candyNum = candies.size/2
    val candyTypeNum = candies.toSet().size

    return Math.min(candyNum, candyTypeNum)
}

fun main(args: Array<String>) {
    val candies = IntArray(6)
    candies[0] = 1
    candies[1] = 1
    candies[2] = 1
    candies[3] = 2
    candies[4] = 2
    candies[5] = 3
    print(distributeCandies(candies))
}