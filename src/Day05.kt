fun main() {
    fun part1(input: List<String>): String {
        val splitIndex = input.indexOf("")
        val rawGridDefinition = input.subList(0, splitIndex)
        val rawInstructions = input.subList(splitIndex + 1, input.size)
        val stacks = mutableMapOf<Int, MutableList<Char>>()

        println(rawGridDefinition)
        // ignore the column name definitions, infer by position
        rawGridDefinition.dropLast(1).forEach { line ->
//            println("line: $line")
            line.forEachIndexed { index, c ->
//                println("index: $index, c: $c")
                // correspoinding column by index
                if ((index - 1) % 4 == 0 && c != ' ') {
                    stacks.compute(((index - 1) / 4) + 1) { key, currentList -> currentList?.apply { add(c) } ?: mutableListOf(c) }
                }
            }
        }
        println(stacks)
        val instructionRegex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        val instructions = rawInstructions.map {instructionLine ->
            println(instructionLine)
            val matchGroups = instructionRegex.matchEntire(instructionLine)!!.groupValues.drop(1).map { it.toInt() }
            Instruction(matchGroups[0], matchGroups[1], matchGroups[2])
        }

        instructions.forEach { instruction ->
            repeat(instruction.count) {
                val fromColumn = stacks[instruction.sourceColumn]
                println(fromColumn)
                val from = stacks[instruction.sourceColumn]!!.removeAt(0)
                println("from: $from")
                stacks.compute(instruction.destinationColumn) { key, value ->
                    (value ?: mutableListOf()).let {
                        mutableListOf(from).plus(it).toMutableList()
                    }
                }
            }
        }
        println(stacks)

        return stacks.toList().sortedBy { it.first }.map { it.second.first() }.joinToString("") { it.toString() }
    }


    fun part2(input: List<String>): String {
        val splitIndex = input.indexOf("")
        val rawGridDefinition = input.subList(0, splitIndex)
        val rawInstructions = input.subList(splitIndex + 1, input.size)
        val stacks = mutableMapOf<Int, MutableList<Char>>()

        println(rawGridDefinition)
        // ignore the column name definitions, infer by position
        rawGridDefinition.dropLast(1).forEach { line ->
//            println("line: $line")
            line.forEachIndexed { index, c ->
//                println("index: $index, c: $c")
                // correspoinding column by index
                if ((index - 1) % 4 == 0 && c != ' ') {
                    stacks.compute(((index - 1) / 4) + 1) { key, currentList -> currentList?.apply { add(c) } ?: mutableListOf(c) }
                }
            }
        }
        println(stacks)
        val instructionRegex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        val instructions = rawInstructions.map {instructionLine ->
            println(instructionLine)
            val matchGroups = instructionRegex.matchEntire(instructionLine)!!.groupValues.drop(1).map { it.toInt() }
            Instruction(matchGroups[0], matchGroups[1], matchGroups[2])
        }

        instructions.forEach { instruction ->

            val fromColumn = stacks[instruction.sourceColumn]

            val movingElements = (0 until instruction.count).map {
                stacks[instruction.sourceColumn]!!.removeAt(0)
            }
            stacks.compute(instruction.destinationColumn) { key, value ->
                (value ?: mutableListOf()).let {
                    movingElements.plus(it).toMutableList()
                }
            }
        }
        println(stacks)
        return stacks.toList().sortedBy { it.first }.map { it.second.first() }.joinToString("") { it.toString() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")
    val p1TestResult = part1(testInput)
    println(p1TestResult)
    check(p1TestResult == "CMZ")
    println(part1(input))

    println("Part 2")
    val p2TestResult = part2(testInput)
    println(p2TestResult)
    check(part2(testInput) == "MCD")
    println(part2(input))
}



data class Grid(val stacks: Map<Int, MutableList<Char>>)
data class Instruction(val count: Int, val sourceColumn: Int, val destinationColumn: Int)