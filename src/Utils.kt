import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

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