package dev.breda.freelancemanager.domain

/** Domain models have no dependency on Android or persistence. */
data class Client(val id: String, val name: String, val contact: String? = null) {
    init { require(id.isNotBlank()); require(name.isNotBlank()) }
}

enum class ProjectStatus { PLANNED, ACTIVE, PAUSED, COMPLETED, CANCELLED }

data class Project(
    val id: String,
    val clientId: String,
    val title: String,
    val description: String = "",
    val status: ProjectStatus = ProjectStatus.PLANNED
) {
    init { require(id.isNotBlank()); require(clientId.isNotBlank()); require(title.isNotBlank()) }
}

data class Contract(
    val id: String,
    val projectId: String,
    val title: String,
    val amount: Money
) {
    init {
        require(id.isNotBlank() && projectId.isNotBlank() && title.isNotBlank())
        require(amount.cents >= 0)
    }
}
