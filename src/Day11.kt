import java.math.BigInteger

enum class OPERATION {
    MULTIPLY,
    ADD
}

fun main() {
    fun part1(input: List<String>): Long {
        val monkeyList = input.joinToString("\n") { it }.split("\n\n").map { monkeyDefinition: String ->
            val monkeyDefinitionLines = monkeyDefinition.split("\n")
            val startingItems = monkeyDefinitionLines[1].split(": ")[1].split(", ").map { it.toInt()}.toMutableList()
            val operation = monkeyDefinitionLines[2].split("new = old ")[1].split(" ").let { operationSplit ->
                val operationType = when(operationSplit[0]) {
                    "*" -> OPERATION.MULTIPLY
                    "+" -> OPERATION.ADD
                    else -> error("unknown op")
                }
                val value = operationSplit[1].toLongOrNull()
                when(operationType) {
                    OPERATION.MULTIPLY -> { x: Long ->
                        (x * (value ?: x))
                    }
                    OPERATION.ADD -> { x: Long ->
                        (x + (value ?: x))
                    }
                }
            }

            val test = monkeyDefinitionLines[3].split("Test: divisible by ")[1].toLong().let { {x: Long -> x % it == 0L }}
            val trueConditionDestination = monkeyDefinitionLines[4].split("throw to monkey ")[1].toInt()
            val falseConditionDestination = monkeyDefinitionLines[5].split("throw to monkey ")[1].toInt()
            Monkey(startingItems.map { it.toLong() }.toMutableList(), operation = operation, test, trueConditionDestination, falseConditionDestination)
        }

        (0 until 20).forEach { round ->
            monkeyList.forEach { monkey ->
                while(monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeFirst()
                    val worry = item
                    val postOperationWorry = monkey.operation.invoke(worry)
                    val boredWorry = postOperationWorry.div(3)
                    if (monkey.condition.invoke(boredWorry)) {
                        monkeyList.get(monkey.trueDestination).items.add(boredWorry)
                    } else {
                        monkeyList.get(monkey.falseDestination).items.add(boredWorry)
                    }
                    monkey.itemInspections++
                }
            }
        }
        return monkeyList.map { it.itemInspections }.sortedDescending().take(2).fold(1L) { acc, item -> acc * item }
    }


    fun part2(input: List<String>): Long {
        var commonMod = 1L
        val monkeyList = input.joinToString("\n") { it }.split("\n\n").map { monkeyDefinition: String ->
            val monkeyDefinitionLines = monkeyDefinition.split("\n")
            val startingItems = monkeyDefinitionLines[1].split(": ")[1].split(", ").map { it.toInt()}.toMutableList()
            val operation = monkeyDefinitionLines[2].split("new = old ")[1].split(" ").let { operationSplit ->
                val operationType = when(operationSplit[0]) {
                    "*" -> OPERATION.MULTIPLY
                    "+" -> OPERATION.ADD
                    else -> error("unknown op")
                }
                val value = operationSplit[1].toLongOrNull()
                when(operationType) {
                    OPERATION.MULTIPLY -> { x: Long ->
                        x * (value ?: x)
                    }
                    OPERATION.ADD -> { x: Long ->
                        x + (value ?: x)
                    }
                }
            }

            val testMod = monkeyDefinitionLines[3].split("Test: divisible by ")[1].toLong()
            commonMod = commonMod * testMod
            val test = testMod.let { {x: Long -> x % it == 0L }}
            val trueConditionDestination = monkeyDefinitionLines[4].split("throw to monkey ")[1].toInt()
            val falseConditionDestination = monkeyDefinitionLines[5].split("throw to monkey ")[1].toInt()
            Monkey(startingItems.map { it.toLong() }.toMutableList(), operation = operation, test, trueConditionDestination, falseConditionDestination)
        }

        (0 until 10000).forEach { round ->
            monkeyList.forEach { monkey ->
                while(monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeFirst()
                    val worry = item
                    val postOperationWorry = monkey.operation.invoke(worry)
                    val boredWorry = postOperationWorry % commonMod
                    if (monkey.condition.invoke(boredWorry)) {
                        monkeyList.get(monkey.trueDestination).items.add(boredWorry)
                    } else {
                        monkeyList.get(monkey.falseDestination).items.add(boredWorry)
                    }
                    monkey.itemInspections++
                }
            }
        }

        println("Common mod: $commonMod")
        monkeyList.forEach {
            println(it.itemInspections)
            println(it.items)
        }

        return monkeyList.map { it.itemInspections }.sortedDescending().take(2).fold(1L) { acc, item -> acc * item }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    val input = readInput("Day11")
    val p1TestResult = part1(testInput)
    println(p1TestResult)
    check(p1TestResult == 10605L)
    println(part1(input))

    println("Part 2")
    val p2TestResult = part2(testInput)
    println(p2TestResult)
    check(p2TestResult == 2713310158L)
    println(part2(input))
}

data class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val condition: (Long) -> Boolean,
    val trueDestination: Int,
    val falseDestination: Int,
    var itemInspections: Long = 0
)