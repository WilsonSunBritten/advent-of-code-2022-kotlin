fun main() {
    fun part1(input: List<String>): Int {
        return input.map { row ->
            val split = row.split(",").map {
                val split = it.split("-")
                split[0].toInt() to split[1].toInt()
            }
            val firstPair = split[0]
            val secondPair = split[1]
            if (firstPair.first <= secondPair.first && firstPair.second >= secondPair.second) {
                1
            } else if (secondPair.first <= firstPair.first && secondPair.second >= firstPair.second) {
                1
            } else {
                0
            }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map { row ->
            val split = row.split(",").map {
                val split = it.split("-")
                split[0].toInt() to split[1].toInt()
            }
            val firstPair = split[0]
            val secondPair = split[1]
            if (firstPair.first <= secondPair.first && firstPair.second >= secondPair.first) {
                1
            } else if (secondPair.first <= firstPair.first && secondPair.second >= firstPair.first) {
                1
            } else {
                0
            }
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")
    val p1TestResult = part1(testInput)
    println(p1TestResult)
    check(part1(testInput) == 2)
    println(part1(input))


    val p2TestResult = part2(testInput)
    println(p2TestResult)
    check(part2(testInput) == 4)
    println(part2(input))
}