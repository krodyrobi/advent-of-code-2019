const val PADDING = 4
const val OUTPUT = 19690720

fun day02(input: String): Int {
    val program = input.lines()[0].split(",").map { it.toInt() }.toIntArray().toTypedArray()
    return partB(program)
}

private fun partB(program: Array<Int>): Int {
    val result = lazyCartesianProduct(0..99, 0..99).firstOrNull {
        val clone = program.clone()
        partA(clone, it.first, it.second)
        return@firstOrNull clone[0] == OUTPUT
    }

    return 100 * (result?.first ?: 0) + (result?.second ?: 0)
}

private fun partA(program: Array<Int>, x: Int, y: Int): Int {
    program[1] = x
    program[2] = y

    var pc = 0
    eval@ do {
        when (getOpCode(program, pc)) {
            1 -> setOpResult(program, pc, getOpFirstArgument(program, pc) + getOpSecondArgument(program, pc))
            2 -> setOpResult(program, pc, getOpFirstArgument(program, pc) * getOpSecondArgument(program, pc))
            else -> break@eval
        }
        pc++
    } while (true)

    return program[0]
}

private fun getOpCode(program:  Array<Int>, offset: Int): Int {
    return getField(program, offset, 0)
}

private fun getOpFirstArgument(program:  Array<Int>, offset: Int): Int {
    return program[getField(program, offset, 1)]
}

private fun getOpSecondArgument(program:  Array<Int>, offset: Int): Int {
    return program[getField(program, offset, 2)]
}

private fun setOpResult(program:  Array<Int>, offset: Int, result: Int) {
    program[getField(program, offset, 3)] = result
}

private fun getField(program:  Array<Int>, offset: Int, field: Int): Int {
    return program[offset * PADDING + field]
}

private fun <A, B> lazyCartesianProduct(
    listA: Iterable<A>,
    listB: Iterable<B>
): Sequence<Pair<A, B>> =
    sequence {
        listA.forEach { a ->
            listB.forEach { b ->
                yield(a to b)
            }
        }
    }

