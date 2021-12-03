fun List<String>.charactersForColumn(n: Int): Map<Char, Int> = this.groupingBy {
    it[n]
}.eachCount()

fun List<Map<Char, Int>>.findMostFrequentChar(): String {
    return this.joinToString("") { frequencies ->
        val mostFrequentChar = frequencies
            .maxByOrNull { it.value }?.key
            ?: error("Should find maximum in $frequencies")
        mostFrequentChar.toString()
    }
}

fun List<Map<Char, Int>>.findLeastFrequentChar(): String {
    return this.joinToString("") { frequencies ->
        val leastFrequentChar = frequencies
            .minByOrNull { it.value }?.key
            ?: error("Should find minimum in $frequencies")
        leastFrequentChar.toString()
    }
}

fun List<String>.findDesiredString(operation: (zeroes: Int, ones: Int) -> Char): String {
    var candidates = this
    for (element in bitIndices) {
        val frequencies = candidates.charactersForColumn(element)
        val zeroes = frequencies['0'] ?: 0
        val ones = frequencies['1'] ?: 0
        candidates = candidates.filter { it[element] == operation(zeroes, ones) }
        if (candidates.size == 1) break
    }
    return candidates.single()
}

val input = readInput("Day03")
val bitIndices = input[0].indices

fun main() {
    fun part1(input: List<String>): Int {
        val charFrequencyByColumn = bitIndices
            .map { column -> input.charactersForColumn(column) }
        val gammaRateString = charFrequencyByColumn.findMostFrequentChar()
        val epsilonRateString = charFrequencyByColumn.findLeastFrequentChar()
        return gammaRateString.toInt(2) * epsilonRateString.toInt(2)
    }

    fun part2(input: List<String>): Int {
        val oxyGenRating = input.findDesiredString { zeroes, ones ->
            if (zeroes > ones) '0' else '1'
        }
        val co2ScrubberRating = input.findDesiredString { zeroes, ones ->
            if (zeroes > ones) '1' else '0'
        }
        return (oxyGenRating.toInt(2) * co2ScrubberRating.toInt(2))
    }

    println(part1(input))
    println(part2(input))
}