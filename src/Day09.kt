import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val moves = input.map {
            val split = it.split(" ")
            Move(positions = split[1].toInt(), direction = directionMap[split[0].first()]!!)
        }
        val tailPositions = mutableSetOf(Position(0, 0))
        var headPosition = Position(0, 0)
        var tailPosition = Position(0, 0)
        moves.forEach { move ->
            repeat(move.positions) {
                val currentHeadPosition = headPosition
                headPosition = move.direction.move(currentHeadPosition)
                if (move.direction.moveCondition(currentHeadPosition, tailPosition)) {
                    tailPosition = move.direction.followPosition(headPosition)
                }
                tailPositions.add(tailPosition)
            }
        }

        return tailPositions.count()
    }


    fun part2(input: List<String>): Int {
        val moves = input.map {
            val split = it.split(" ")
            Move(positions = split[1].toInt(), direction = directionMap[split[0].first()]!!)
        }
        val tailPositions = mutableSetOf(Position(0, 0))
        var chain = (0 until 9).map { Position(0, 0) }
        var headPosition = Position(0, 0)
        moves.forEach { move ->
            repeat(move.positions) {
                val newHead = move.direction.move(headPosition)
                headPosition = newHead
                val movedChain = chain.runningFold(newHead) { relativeHead, relativeTail ->
                    val differenceX = relativeHead.x - relativeTail.x
                    val differenceY = relativeHead.y - relativeTail.y

                    // we went right a nominal amount
                    if (differenceX > 1 && differenceY == 0) {
                        relativeTail.copy(x = relativeTail.x + 1)
                    } else if (differenceX < -1 && differenceY == 0) {
                        relativeTail.copy(x = relativeTail.x - 1)
                    } else if (differenceY > 1 && differenceX == 0) {
                        relativeTail.copy(y = relativeTail.y + 1)
                    } else if (differenceY < -1 && differenceX == 0) {
                        relativeTail.copy(y = relativeTail.y - 1)
                    }  // pesky diagnal moves do nothing but diagnal BEYOND does thing
                    else if (differenceX.absoluteValue > 1 || differenceY.absoluteValue > 1) {
                        val newX = if (differenceX > 1) {
                            relativeTail.x + 1
                        } else if (differenceX < -1) {
                           relativeTail.x - 1
                        } else {
                            relativeTail.x
                        }
                        val newY = if (differenceY > 1) {
                            relativeTail.y + 1
                        } else if (differenceY < -1) {
                            relativeTail.y -1
                        } else {
                            relativeTail.y
                        }
                        Position(x = newX, y = newY)
                    } else {
                        relativeTail
                    }
                }
//                println(movedChain)
                // head is being tracked seperatedly still
                chain = movedChain.drop(1)
//                println(chain)
                tailPositions.add(chain.last())
            }
        }

        println(tailPositions)
        return tailPositions.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val input = readInput("Day09")
//    val p1TestResult = part1(testInput)
//    println(p1TestResult)
//    check(p1TestResult == 13)
//    println(part1(input))

    println("Part 2")
    val p2TestResult = part2(testInput)
    println(p2TestResult)
    check(p2TestResult == 36)
    println(part2(input))
}

data class Move(val positions: Int, val direction: Direction)
val directionMap = mapOf(
    'U' to Direction.UP,
    'D' to Direction.DOWN,
    'L' to Direction.LEFT,
    'R' to Direction.RIGHT
)
data class Position(var x: Int, var y: Int)
// 1. tell us where H moves
// 2. tell us if T follows
// 3. tell us T's new position, if it moved
enum class Direction(val move: (Position) -> Position, val moveCondition: (Position, Position) -> Boolean, val followPosition: (Position) -> Position) {
    UP(
        { position -> position.copy(y = position.y + 1) },
        { head, tail -> tail.y == head.y - 1 },
        { head -> head.copy(y = head.y - 1) }
    ),
    DOWN(
        { position -> position.copy(y = position.y - 1) },
        { head, tail -> tail.y == head.y + 1 },
        { head -> head.copy(y = head.y + 1) }
    ),
    LEFT(
        { position -> position.copy(x = position.x - 1) },
        { head, tail -> tail.x == head.x + 1 },
        { head -> head.copy(x = head.x + 1) }
    ),
    RIGHT(
        { position -> position.copy(x = position.x + 1) },
        { head, tail -> tail.x == head.x - 1 },
        { head -> head.copy(x = head.x - 1) }
    );
}