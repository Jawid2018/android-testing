package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultTasksRepositoryTest {

    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var localDataSource: FakeDataSource
    private lateinit var remoteDataSource: FakeDataSource

    private lateinit var defaultTasksRepository: DefaultTasksRepository

    @Before
    fun createDataSource() {
        localDataSource = FakeDataSource(localTasks.toMutableList())
        remoteDataSource = FakeDataSource(remoteTasks.toMutableList())

        defaultTasksRepository = DefaultTasksRepository(
                localDataSource, remoteDataSource, Dispatchers.Unconfined
        )
    }

    @Test
    fun getTasks_requestAllTasksFromRemoteDataSource() = runBlockingTest {
        val tasks = defaultTasksRepository.getTasks(true) as Result.Success
        // Then tasks are loaded from the remote data source
        assertThat(tasks.data, IsEqual(remoteTasks))
    }

    @Test
    fun getTasks_requestAllTasksFromLocalDataSource() = runBlockingTest {
        val tasks = defaultTasksRepository.getTasks(forceUpdate = false) as Result.Success
        assertThat(tasks.data, IsEqual(localTasks))
    }

    @Test
    fun clearTasks_clearAllTaskFromDataSource() = runBlockingTest {
        defaultTasksRepository.deleteAllTasks()
        val tasks = defaultTasksRepository.getTasks(true) as Result.Success
        assertThat(tasks.data, `is`(emptyList()))
    }

    @Test
    fun saveTasks_sizeIsMoreThanOne() = runBlockingTest {
        defaultTasksRepository.saveTask(newTasks[0])
        val tasks = defaultTasksRepository.getTasks(false) as Result.Success
        assertThat(tasks.data.size, `is`((localTasks.size + 1)))
    }
}