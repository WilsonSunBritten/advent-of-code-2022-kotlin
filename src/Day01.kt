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

fun <T> Collection<T>.splitBy(inclusionType: InclusionType = InclusionType.NONE, splitCondition: (T) -> Boolean): Sequence<List<T>> {
    return sequence {
        var currentList = mutableListOf<T>()
        this@splitBy.forEach { entry ->
            if (splitCondition(entry)) {
                when (inclusionType) {
                    InclusionType.NONE -> {
                        // ignore this entry
                        yield(currentList)
                        currentList = mutableListOf()
                    }
                    InclusionType.LEFT -> {
                        currentList.add(entry)
                        yield(currentList)
                        currentList = mutableListOf()
                    }
                    InclusionType.RIGHT -> {
                        yield(currentList)
                        currentList = mutableListOf(entry)
                    }
                }
            } else {
                currentList.add(entry)
            }
        }
        if (currentList.isNotEmpty()) {
            yield(currentList)
        }
    }
}

enum class InclusionType {
    NONE,
    LEFT,
    RIGHT;
}
