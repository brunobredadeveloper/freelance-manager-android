package dev.breda.freelancemanager.domain

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ReminderPolicyTest {
    @Test fun generatesAdvanceDates() {
        val due = LocalDate.of(2026, 10, 26)
        val dates = ReminderPolicy().occurrences(ReminderKind.PAYMENT, "installment", due)
        assertEquals(listOf(5, 3, 1, 0), dates.map { it.daysBefore })
        assertEquals(listOf(21, 23, 25, 26), dates.map { it.reminderDate.dayOfMonth })
    }

    @Test fun crossesMonthBoundary() {
        val due = LocalDate.of(2027, 1, 2)
        val dates = ReminderPolicy().occurrences(ReminderKind.DELIVERABLE, "milestone", due)
        assertEquals(LocalDate.of(2026, 12, 28), dates.first().reminderDate)
    }

    @Test fun rejectsInvalidOffsets() {
        assertFailsWith<IllegalArgumentException> { ReminderPolicy(setOf(-1)) }
    }
}
