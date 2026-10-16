package dev.breda.freelancemanager.domain

import java.time.LocalDate

enum class ReminderKind { DELIVERABLE, PAYMENT }

data class ReminderOccurrence(
    val kind: ReminderKind,
    val targetId: String,
    val dueDate: LocalDate,
    val reminderDate: LocalDate,
    val daysBefore: Int
)

/** Pure domain policy. Scheduling notifications is an infrastructure concern. */
class ReminderPolicy(private val offsets: Set<Int> = setOf(5, 3, 1, 0)) {
    init { require(offsets.all { it >= 0 }) }

    fun occurrences(kind: ReminderKind, targetId: String, dueDate: LocalDate): List<ReminderOccurrence> {
        require(targetId.isNotBlank())
        return offsets.sortedDescending().map { days ->
            ReminderOccurrence(kind, targetId, dueDate, dueDate.minusDays(days.toLong()), days)
        }
    }
}
