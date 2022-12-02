import java.lang.Thread.yield

fun main() {
    fun part1(input: List<String>): Long {
        return input.splitBy { it.isBlank() }.map { it.sumOf { it.toLong() } }.max()
    }

    fun part2(input: List<String>): Long {
        return input
            .splitBy { it.isBlank() }
            .map { it.sumOf { it.toLong() } }
            .sortedDescending()
            .take(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000L)
    check(part2(testInput) == 45000L)
    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
