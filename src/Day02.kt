fun main() {

    fun part1(input: List<String>): Int {
        return input.map { round ->
            val split = round.split(" ")
            val opponentPlay = opponentPlay[split.first()]!!
            val myPlay = myPlay[split[1]]!!
            gameScore(opponentPlay, myPlay)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map { round ->
            val split = round.split(" ")
            val opponent = opponentPlay[split.first()]!!
            val me = when(split[1]) {
                "X" -> Result.LOSE
                "Y" -> Result.DRAW
                "Z" -> Result.WIN
                else -> error("")
            }

            val myPlay = when (me) {
                Result.WIN -> Play.values().first { it.ordinal == (opponent.ordinal + 1) % 3 }
                Result.DRAW -> opponent
                Result.LOSE -> Play.values().first { (it.ordinal + 1) % 3 == opponent.ordinal }
            }
            me.score + myPlay.score
        }.sum()
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    println(part2(testInput))
    check(part2(testInput) == 12)
    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
enum class Play(val score: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);
}

private fun gameScore(opponent: Play, me: Play): Int {
    if (opponent == me) {
        return me.score + Result.DRAW.score
    } else if (opponent.ordinal == (me.ordinal + 1) % 3) {
        return me.score + Result.LOSE.score
    } else {
        return me.score + Result.WIN.score
    }
}

enum class Result(val score: Int) {
    WIN(6), LOSE(0), DRAW(3);
}

val myPlay = mapOf(
    "X" to Play.ROCK,
    "Y" to Play.PAPER,
    "Z" to Play.SCISSORS
)

val opponentPlay = mapOf(
    "A" to Play.ROCK,
    "B" to Play.PAPER,
    "C" to Play.SCISSORS
)