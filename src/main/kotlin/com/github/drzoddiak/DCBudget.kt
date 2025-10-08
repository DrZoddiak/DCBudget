package com.github.drzoddiak

import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.time.Instant

fun main() {
    // Create directories for input/output
    File("input").mkdir()
    File("output").mkdir()

    // Read from input file
    runCatching {
        File("input/budget.txt").readLines()
    }.onFailure {
        println("Failed to read file, creating blank text file")
        File("input/budget.txt").also {
            it.createNewFile()
            it.writeText(
                """
                #################################################################################
                # Paste budget text here and run program again to generate a CSV of the report! #
                #################################################################################
                
            """.trimIndent()
            )
        }
    }.getOrNull()?.let { lines ->
        // Cuts the budget text down into only necessary bits

        // Gets all the payments using the regex

        "(\\w*) » (\\w*) has been unfined \\D*(\\d*) by \\w* for (\\w)".toRegex().let { regex ->
            lines.map { it.trim() }
                .filter { it.contains(regex) }
                .map {
                    val type = it.replaceAfter(" ", "").trim()
                    val amount = it.replaceBefore("$", "").replaceAfter(" ", "").replace("$", "").toDouble()
                    val reason = it.replaceBefore("for", "").replace("for ", "")
                    val cause = it.replaceBefore("by", "").replace("by ", "").replaceAfter(" ", "").trim()
                    val recipient = it.replaceBefore("»", "").replace("» ", "").replaceAfter(" ", "").trim()

                    BudgetInfo(type, amount, reason, cause, recipient)
                }.let { budgets ->
                    mutableListOf<List<String>>().also { list ->
                        list.add(listOf("type", "amount", "reason", "cause", "recipient"))
                        list.addAll(budgets.map { it.asList() })
                    }
                }
        }.let { csv ->
            csvWriter().writeAll(csv, "output/budget-${Instant.now().toEpochMilli()}.csv", append = true)
        }
    }
}




