fun main() {
    fun part1(input: List<String>): Int {
        // map of full path directories to a list of filesizes in them
        val directories = mutableMapOf<String, MutableList<Int>>()
        val currentFullPath = mutableListOf("/")

        input.subList(1, input.size).mapNotNull {
            if (it.startsWith("$")) {
                // get command
                if (it.contains("ls")) {
                    // ignore the command itself
                    null
                } else {
                    val destination = it.split(" ").last().let { if (it != ".." ) { it + "/" } else { it } }
                    CD(destination = destination)
                }
            } else {
                if (it.startsWith("dir")) {
                    // ignore the command itself
                    null
                } else {
                    val split = it.split(" ")
                    val fileSize = split.first().toInt()
                    val fileName = split[1]
                    File(fileName, fileSize)
                }
            }
        }.forEach { command ->
            when(command) {
                is LS -> { }
                is CD -> {
                    if (command.destination == "..") {
                        currentFullPath.removeLast()
                    } else {
                        currentFullPath.add(command.destination)
                    }
                }
                is File -> {
                    currentFullPath.runningFold("") { acc, current -> acc + current }.forEach {
                        directories.compute(it) { key: String, currentFileSizes: MutableList<Int>? ->
                            (currentFileSizes ?: mutableListOf()).apply {
                                this.add(command.size)
                            }
                        }
                    }
                }
            }
        }
        return directories.map { it.value.sum() }.filter { it <= 100000 }.sum()
    }


    fun part2(input: List<String>): Int {
        // map of full path directories to a list of filesizes in them
        val directories = mutableMapOf<String, MutableList<Int>>()
        val currentFullPath = mutableListOf("/")

        input.subList(1, input.size).mapNotNull {
            if (it.startsWith("$")) {
                // get command
                if (it.contains("ls")) {
                    LS()
                } else {
                    val destination = it.split(" ").last().let { if (it != ".." ) { it + "/" } else { it } }
                    CD(destination = destination)
                }
            } else {
                if (it.startsWith("dir")) {
                    null
                } else {
                    val split = it.split(" ")
                    val fileSize = split.first().toInt()
                    val fileName = split[1]!!
                    File(fileName, fileSize)
                }
            }
        }.forEach { command ->
            when(command) {
                is LS -> { }
                is CD -> {
                    if (command.destination == "..") {
                        currentFullPath.removeLast()
                    } else {
                        currentFullPath.add(command.destination)
                    }
                }
                is File -> {
                    currentFullPath.runningFold("") { acc, current -> acc + current }.forEach {
                        directories.compute(it) { key: String, currentFileSizes: MutableList<Int>? ->
                            (currentFileSizes ?: mutableListOf()).apply {
                                this.add(command.size)
                            }
                        }
                    }
                }
            }
        }

        val freeSpace = 70000000 - directories["/"]!!.sum()
        val requiredSpace = 30000000 - freeSpace
        return directories.map { it.value.sum() }.filter { it > requiredSpace }.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val input = readInput("Day07")
    val p1TestResult = part1(testInput)
    println(p1TestResult)
    check(p1TestResult == 95437)
    println(part1(input))

    println("Part 2")
    val p2TestResult = part2(testInput)
    println(p2TestResult)
    check(part2(testInput) == 24933642)
    println(part2(input))
}

data class File(val filename: String, val size: Int)
sealed interface Action
class LS: Action
class CD(val destination: String): Action