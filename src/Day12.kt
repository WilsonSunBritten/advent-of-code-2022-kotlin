fun Position2.neighbors(maxRow: Int, maxColumn: Int, elevationMap: List<List<Int>>): Set<Position2> {
    return setOf(
        // up a position
        Position2((this.row - 1).coerceAtLeast(0), this.column),
        // down a position
        Position2((this.row + 1).coerceAtMost(maxRow), this.column),
        // left
        Position2(this.row, (this.column - 1).coerceAtLeast(0)),
        // right
        Position2(this.row, (this.column + 1).coerceAtMost(maxColumn))
    ).filter {
        elevationMap[it.row][it.column] <= elevationMap[this.row][this.column] + 1
    }.toSet()
}

data class Position2(val row: Int, val column: Int)

fun main() {
    fun part1(input: List<String>): Int {
        // bfs solution... just need a proper navigation scheme...
        val elevationMap = input.map { line ->
            line.map { character ->
                when(character) {
                    'S' -> 0
                    'E' -> 25
                    else -> character.minus('a')
                }

            }
        }
        val maxColumn = input.first().length - 1
        val maxRow = input.size - 1
        val startingPosition = input.indexOfFirst { it.contains('S') }.let { it to input[it].indexOfFirst { it == 'S' }}.let { Position2(it.first, it.second)}
        val endingPosition = input.indexOfFirst { it.contains('E') }.let { it to input[it].indexOfFirst { it == 'E' }}.let { Position2(it.first, it.second)}

        // map of positions to length to get there
        val visited = mutableSetOf(startingPosition)
        // to visit also includes distance
        val toVisit = startingPosition.neighbors(maxRow, maxColumn, elevationMap).toMutableList().map { it to 1 }.toMutableList()
        while(toVisit.isNotEmpty()) {
            val (current, distance) = toVisit.removeFirst()
            if (current == endingPosition) {
                return distance
            }
            val previouslyVisited = visited.contains(current)
            if (previouslyVisited) {
                continue
            } else {
                visited.add(current)
                toVisit.addAll(current.neighbors(maxRow, maxColumn, elevationMap).map { it to distance + 1 })
            }
        }
        return -1
    }


    fun part2(input: List<String>): Int {
        // bfs solution... just need a proper navigation scheme...
        val elevationMap = input.map { line ->
            line.map { character ->
                when(character) {
                    'S' -> 0
                    'E' -> 25
                    else -> character.minus('a')
                }

            }
        }

        val startingPositions = elevationMap.flatMapIndexed { rowIndex, row ->
            row.mapIndexed { index, value -> index to value }.filter { it.second == 0 }.map { rowIndex to it.first }
        }.map { Position2(it.first, it.second)}
        val endingPosition = input.indexOfFirst { it.contains('E') }.let { it to input[it].indexOfFirst { it == 'E' }}.let { Position2(it.first, it.second)}


        return startingPositions.asSequence().minOf {
            distanceToEnd(elevationMap, it, endingPosition = endingPosition)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    val input = readInput("Day12")
    val p1TestResult = part1(testInput)
    println(p1TestResult)
    check(p1TestResult == 31)
    println(part1(input))

    println("Part 2")
    val p2TestResult = part2(testInput)
    println(p2TestResult)
    check(p2TestResult == 29)
    println(part2(input))
}


fun distanceToEnd(elevationMap: List<List<Int>>, startingPosition: Position2, endingPosition: Position2): Int {

    val maxColumn = elevationMap.first().size - 1
    val maxRow = elevationMap.size - 1
    // map of positions to length to get there
    val visited = mutableSetOf(startingPosition)
    // to visit also includes distance
    val toVisit = startingPosition.neighbors(maxRow, maxColumn, elevationMap).toMutableList().map { it to 1 }.toMutableList()
    while(toVisit.isNotEmpty()) {
        val (current, distance) = toVisit.removeFirst()
        if (current == endingPosition) {
            return distance
        }
        val previouslyVisited = visited.contains(current)
        if (previouslyVisited) {
            continue
        } else {
            visited.add(current)
            toVisit.addAll(current.neighbors(maxRow, maxColumn, elevationMap).map { it to distance + 1 })
        }
    }
    return 999999
}