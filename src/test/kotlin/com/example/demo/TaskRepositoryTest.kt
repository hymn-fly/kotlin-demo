package com.example.demo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TaskRepositoryTest(private val repository: TaskRepository): FunSpec({

    test("test") {
        repository.saveAll(listOf(Task(1, "description", "${DaysOff.MON},${DaysOff.TUE}"),
            Task(2, "description", "${DaysOff.WED},${DaysOff.THU}")))

        val tasks: List<Task> = repository.findAll()

        tasks.size shouldBe 2
    }
})