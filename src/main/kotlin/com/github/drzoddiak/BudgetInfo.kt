package com.github.drzoddiak

// Data structure for CSV output
data class BudgetInfo(
    var type: String,
    val amount: Double,
    var reason: String,
    var cause: String,
    var recipient: String
) {
    // Convenience method to convert to list for CSV dependency
    fun asList(): List<String> {
        return listOf(type, amount.toString(), reason, cause, recipient)
    }
}