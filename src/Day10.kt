fun main() {
    fun part1(input: List<String>): Int {
        val xIterator = input.map {
            if (it == "noop") {
                NOOP()
            } else {
                ADD(it.split(" ")[1].toInt())
            }
        }.flatMap { command ->
            when(command) {
                is NOOP -> { listOf({ x: Int -> x}) }
                is ADD -> { listOf({x: Int -> x}, {x: Int -> x + command.value })}
            }
        }.iterator()

        val cycleStream = generateSequence(1) { it + 1 }

        val cyclePoints = cycleStream.runningFold(1) { currentXRegister, cycleIndex ->
            val nextCommandValueChange = xIterator.next()
            nextCommandValueChange.invoke(currentXRegister)
        }.takeWhile { xIterator.hasNext() }.toList()

        return listOf(
            20, 60, 100, 140, 180, 220
        ).map { it * cyclePoints[it - 1] }
            .sum()
    }


    fun part2(input: List<String>): String {
        val xIterator = input.map {
            if (it == "noop") {
                NOOP()
            } else {
                ADD(it.split(" ")[1].toInt())
            }
        }.flatMap { command ->
            when(command) {
                is NOOP -> { listOf({ x: Int -> x}) }
                is ADD -> { listOf({x: Int -> x}, {x: Int -> x + command.value })}
            }
        }.iterator()

        val cycleStream = generateSequence(1) { it + 1 }

        val cyclePointsIterator = cycleStream.runningFold(1) { currentXRegister, cycleIndex ->
            val nextCommandValueChange = xIterator.next()
            nextCommandValueChange.invoke(currentXRegister)
        }.takeWhile { xIterator.hasNext() }.toList().iterator()


        val stringBuilder = StringBuilder()
        // 40 by 6
        (0 until 240).forEach { pixel ->
            val position = pixel % 40

            if (position == 0 && pixel != 0) {
                stringBuilder.append('\n')
            }


            val cyclePointValue = cyclePointsIterator.next()
            if (position in setOf(cyclePointValue - 1, cyclePointValue, cyclePointValue + 1)) {
                stringBuilder.append('#')
            } else {
                stringBuilder.append('.')
            }
        }

        return stringBuilder.toString()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val input = readInput("Day10")
    val p1TestResult = part1(testInput)
    println(p1TestResult)
    check(p1TestResult == 13140)
    println(part1(input))

    println("Part 2")
    val p2TestResult = part2(testInput)
    println(p2TestResult)
    check(p2TestResult == """##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######.....""")
    println(part2(input))
}

sealed interface Command {
    val cycles: Int
}
class NOOP : Command {
    override val cycles = 1
}
data class ADD(val value: Int) : Command {
    override val cycles = 1
}