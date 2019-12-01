fun day01(input: String): Long = input.lines().map { it.toLong() }
    .map { computeFuel(0, it) }
    .sum()

private tailrec fun computeFuel(acc: Long, mass: Long): Long {
    val fuel = mass / 3 - 2
    if(fuel <= 0)
        return acc

    return computeFuel(acc + fuel, fuel)
}