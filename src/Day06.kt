fun main() {
    fun part1(input: List<String>): Int {
        return input.first().windowedSequence(4, 1).mapIndexed { index, window ->
            index to window
        }.first { (_, window) -> window.toList().distinct().size == 4 }.first + 4
    }


    fun part2(input: List<String>): Int {
        return input.first().windowedSequence(14, 1).mapIndexed { index, window ->
            index to window
        }.first { (_, window) -> window.toList().distinct().size == 14 }.first + 14
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val input = readInput("Day06")
    val p1TestResult = part1(testInput)
    println(p1TestResult)
    check(p1TestResult == 7)
    println(part1(input))

    println("Part 2")
    val p2TestResult = part2(testInput)
    println(p2TestResult)
    check(part2(testInput) == 19)
    println(part2(input))
}