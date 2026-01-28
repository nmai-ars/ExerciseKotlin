
fun main() {
    println("Hello, world!")


    val age = 5
    val name = "Rover"
    var roll = 6
    var rolledValue: Int = 4

    // String template
    println("You are already $age!")
    println("You are already ${age} days old, $name!")
    println("roll=$roll, rolledValue=$rolledValue")

    // Gọi hàm
    printHello()
    printBorder("=", 23)

    val diceRange = 1..6
    val randomNumber = diceRange.random()
    println("randomNumber from range: $randomNumber")

    // Hàm trả về giá trị
    val r = rollDice()
    println("rollDice() returned: $r")

    // repeat() và lồng repeat()
    printBorder("*", 30)
    printCakeBottom(age = 5, layers = 3)
    printBorder("*", 30)

    // if / else if / else
    val num = 4
    if (num > 4) {
        println("The variable is greater than 4")
    } else if (num == 4) {
        println("The variable is equal to 4")
    } else {
        println("The variable is less than 4")
    }

    // when :
    val luckyNumber = 3
    val rollResult = (1..6).random()
    println("rollResult=$rollResult, luckyNumber=$luckyNumber")
    when (rollResult) {
        luckyNumber -> println("You won!")
        1 -> println("So sorry! You rolled a 1. Try again!")
        2 -> println("Sadly, you rolled a 2. Try again!")
        3 -> println("Unfortunately, you rolled a 3. Try again!")
        4 -> println("No luck! You rolled a 4. Try again!")
        5 -> println("Don't cry! You rolled a 5. Try again!")
        6 -> println("Apologies! you rolled a 6. Try again!")
    }

    // Class Dice
    val myFirstDice = Dice(numSides = 6)
    println("Dice rolled: ${myFirstDice.roll()}")
}

// Hàm không có đối số :
fun printHello() {
    println("Hello Kotlin")
}

// Hàm có đối số :
fun printBorder(border: String, timesToRepeat: Int) {
    repeat(timesToRepeat) { print(border) }
    println()
}

// Hàm trả về Int
fun rollDice(): Int {
    val randomNumber = (1..6).random()
    return randomNumber
}

//  repeat() lồng nhau
fun printCakeBottom(age: Int, layers: Int) {
    repeat(layers) {
        repeat(age + 2) { print("@") }
        println()
    }
}

// Class có tham số
class Dice(private val numSides: Int) {
    fun roll(): Int = (1..numSides).random()
}
