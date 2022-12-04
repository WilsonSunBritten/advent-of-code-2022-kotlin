fun main() {
    fun part1(input: List<String>): Int {
        return input.map { rucksack ->
            println(rucksack)
            val firstHalf = rucksack.take(rucksack.length / 2).toList()
            val secondHalf = rucksack.takeLast(rucksack.length / 2).toList()
            val matchingCharacters = firstHalf.toSet().intersect(secondHalf.toSet())
//            println("here")
//            println(matchingCharacters)
            val result = matchingCharacters.toList().map { if(it.toInt() > 90) { it.toInt() - 96} else { it.toInt() - 64 + 26 } }.sum()
            result
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.windowed(3, 3).map { rucksackGroup ->
            rucksackGroup.fold(rucksackGroup.first().toSet()) { acc, current -> acc.intersect(current.toSet()) }
        }.map {
            val value = it.single()
            if(value.toInt() > 90) { value.toInt() - 96} else { value.toInt() - 64 + 26 }
        }.also { println(it) }.sum()
    }
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val p1Result = part1(testInput)
    println(p1Result)
    check(p1Result == 157)
    println(part2(testInput))
    check(part2(testInput) == 70)
    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}