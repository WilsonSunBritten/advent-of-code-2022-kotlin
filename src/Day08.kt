
fun main() {
    fun part1(input: List<String>): Int {
        val rows = input.size
        val columns = input.first().toList().size
        val map: List<List<Int>> = input.map { row -> row.toList().map { it.digitToInt() }}
        val visibleMap = mutableMapOf<Pair<Int, Int>, Boolean>()



        // visible from left
        (0 until rows)
            .forEach { row ->
                var max = -1
                (0 until columns)
                .toList()
                    .forEach { column ->
                        if (map[row][column] > max) {
                            max = map[row][column]
                            visibleMap[row to column] = true
                        }
                    }
            }
        //right
        (0 until rows)
            .forEach { row ->
                var max = -1
                (0 until columns).reversed()
                    .toList()
                    .forEach { column ->
                        if (map[row][column] > max) {
                            max = map[row][column]
                            visibleMap[row to column] = true
                        }
                    }
            }
        // top
        (0 until columns)
            .forEach { column ->
                var max = -1
                (0 until rows)
                    .toList()
                    .forEach { row ->
                        if (map[row][column] > max) {
                            max = map[row][column]
                            visibleMap[row to column] = true
                        }
                    }
            }
        // bottom
        (0 until columns)
            .forEach { column ->
                var max = -1
                (0 until rows).reversed()
                    .toList()
                    .forEach { row ->
                        if (map[row][column] > max) {
                            max = map[row][column]
                            visibleMap[row to column] = true
                        }
                    }
            }
        // border ones
        return visibleMap.values.filter { it }.count()
    }


    fun part2(input: List<String>): Int {
        val rows = input.size
        val columns = input.first().toList().size
        val map: List<List<Int>> = input.map { row -> row.toList().map { it.digitToInt() }}
        val scenicScores = mutableMapOf<Pair<Int, Int>, Int>()

        (0 until rows).forEach { row ->
            (0 until columns).forEach { column ->
                val currentTree = map[row][column]
                var leftCounter = 0
                var leftIndex = row - 1
                while (leftIndex >= 0) {
                    leftCounter++
                    if (map[leftIndex][column] < currentTree) {
                        leftIndex--
                    } else {
                        break
                    }
                }
                var rightCounter = 0
                var rightIndex = row + 1
                while (rightIndex < rows) {
                    rightCounter++
                    if (map[rightIndex][column] < currentTree) {
                        rightIndex++
                    } else {
                        break
                    }
                }
                var topCounter = 0
                var topIndex = column - 1
                while (topIndex >= 0) {
                    topCounter++
                    if (map[row][topIndex] < currentTree) {
                        topIndex--
                    }else {
                        break
                    }
                }
                var bottomCounter = 0
                var bottomIndex = column + 1
                while (bottomIndex < columns) {
                    bottomCounter++
                    if (map[row][bottomIndex] < currentTree) {
                        bottomIndex++
                    } else {
                        break
                    }
                }
                scenicScores[row to column] = topCounter * bottomCounter * leftCounter * rightCounter
            }
        }

        // border ones
        return scenicScores.values.max()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val input = readInput("Day08")
    val p1TestResult = part1(testInput)
    println(p1TestResult)
    check(p1TestResult == 21)
    println(part1(input))

    println("Part 2")
    val p2TestResult = part2(testInput)
    println(p2TestResult)
    check(part2(testInput) == 8)
    println(part2(input))
}