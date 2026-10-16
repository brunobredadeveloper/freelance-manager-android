package dev.breda.freelancemanager.domain

/** Minor currency units (centavos for BRL). Never use Double for accounting. */
@JvmInline
value class Money(val cents: Long) {
    operator fun plus(other: Money): Money = Money(Math.addExact(cents, other.cents))
    operator fun minus(other: Money): Money = Money(Math.subtractExact(cents, other.cents))
    companion object { val ZERO = Money(0) }
}

data class Installment(val id: String, val expected: Money)
data class Receipt(val installmentId: String, val amount: Money)

/** Outstanding amount may be negative if receipts exceed the agreed installment. */
fun outstanding(installment: Installment, receipts: List<Receipt>): Money {
    require(installment.expected.cents >= 0) { "Expected amount cannot be negative" }
    val received = receipts.asSequence()
        .filter { it.installmentId == installment.id }
        .fold(Money.ZERO) { sum, receipt ->
            require(receipt.amount.cents > 0) { "Receipt must be positive" }
            sum + receipt.amount
        }
    return installment.expected - received
}
