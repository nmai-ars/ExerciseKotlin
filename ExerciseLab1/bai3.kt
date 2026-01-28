// bai3.kt


fun main() {

    val numbers = listOf(0, 3, 8, 4, 0, 5, 5, 8, 9, 2)
    val setOfNumbers = numbers.toSet()
    println("setOfNumbers = $setOfNumbers")

    val set1 = setOf(1, 2, 3)
    val set2 = mutableSetOf(3, 4, 5)
    println("intersect = ${set1.intersect(set2)}")
    println("union = ${set1.union(set2)}")

    // Map mutable :
    val peopleAges = mutableMapOf(
        "Fred" to 30,
        "Ann" to 23
    )
    peopleAges.put("Barbara", 42)
    peopleAges["Joe"] = 51

    // forEach :
    peopleAges.forEach { print("${it.key} is ${it.value}, ") }
    println()

    // map + joinToString :
    println(peopleAges.map { "${it.key} is ${it.value}" }.joinToString(", "))

    // filter :
    val filteredNames = peopleAges.filter { it.key.length < 4 }
    println("filteredNames = $filteredNames")

    // Chuỗi phép toán collection :
    val words = listOf("about", "acute", "balloon", "best", "brief", "class")
    val filteredWords = words
        .filter { it.startsWith("b", ignoreCase = true) }
        .shuffled()
        .take(2)
        .sorted()
    println("filteredWords = $filteredWords")

    // Scope functions: let :
    val maybeText: String? = "Hello"
    val lengthOrNull = maybeText?.let { it.length }
    println("lengthOrNull = $lengthOrNull")

    // Scope functions: apply :
    val builder = StringBuilder().apply {
        append("Kotlin ")
        append("apply() ")
        append("demo")
    }
    println(builder.toString())

    // Backing property :
    val holder = BackingPropertyDemo()
    println("currentScrambledWord = ${holder.currentScrambledWord}")

    // Safe call chain
    val a: String? = null
    val safe = a?.trim()?.uppercase()
    println("safe = $safe")

    // Lambda :
    val triple: (Int) -> Int = { x: Int -> x * 3 }
    println("triple(5) = ${triple(5)}")

    // Companion object :
    println("DetailActivity.LETTER = ${DetailActivity.LETTER}")

    // Delegated property
    val lazyValue: String by lazy { "I am lazy" }
    println("lazyValue = $lazyValue")

    // lateinit :
    val late = LateInitDemo()
    late.init("assigned later")
    println("late.currentWord = ${late.currentWord}")

    // Elvis operator :
    var quantity: Int? = null
    println("quantity ?: 0 = ${quantity ?: 0}")
    quantity = 4
    println("quantity ?: 0 = ${quantity ?: 0}")
}

class BackingPropertyDemo {
    private var _currentScrambledWord = "test" // :
    val currentScrambledWord: String
        get() = _currentScrambledWord
}

class DetailActivity {
    companion object {
        const val LETTER = "letter" // :
    }
}

class LateInitDemo {
    lateinit var currentWord: String // :

    fun init(value: String) {
        currentWord = value
    }
}
