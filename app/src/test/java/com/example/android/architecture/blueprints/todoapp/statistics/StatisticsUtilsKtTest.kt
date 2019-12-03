package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.intellij.lang.annotations.Language
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsKtTest {

    @Test
    fun getActiveAndCompletedStats_hudred_noCompelted_returnHundredZero() {

        //Given a list of tasks
        val tasks = listOf(
                Task("Study for", "awoid", isCompleted = false),
                Task("askjdfak", "ljdflalkdf", isCompleted = false),
                Task("Study for", "awoid", isCompleted = true),
                Task("askjdfak", "ljdflalkdf", isCompleted = false),
                Task("sdfa", "asdfa", isCompleted = true)
        )

        //When we call getActiveAndCompleteStats
        val result = getActiveAndCompletedStats(tasks)

        //Then assert the result
        assertEquals(result.activeTasksPercent, 60f)
        assertEquals(result.completedTasksPercent, 40f)
    }


    @Test
    fun getActiveAndCompletedStats_completeAndActive_returnSixtyFourty() {

        //Given a list of tasks
        val tasks = listOf(
                Task("asdfk", "asdfa", isCompleted = true),
                Task("asdfk", "asdfa", isCompleted = true),
                Task("asdfk", "asdfa", isCompleted = true),
                Task("asdfk", "asdfa", isCompleted = true),
                Task("asdfk", "asdfa", isCompleted = false)
        )

        //When call the method
        val result = getActiveAndCompletedStats(tasks)

        //Then Assert the result
        assertThat(result.activeTasksPercent, `is`(20f))
        assertThat(result.completedTasksPercent, `is`(80f))
    }

    @Test
    fun getActiveAndCompleteStats_null_returnZeroZero() {
        //Given
        val tasks: List<Task>? = null

        //when
        val result = getActiveAndCompletedStats(tasks)

        //Then
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompleteStats_empty_returnZeroZero() {
        //Given
        val tasks = emptyList<Task>()
        //when
        val result = getActiveAndCompletedStats(tasks)

        //Then
        assertThat(result.activeTasksPercent,`is`(0f))
        assertThat(result.completedTasksPercent,`is`(0f))
    }
}