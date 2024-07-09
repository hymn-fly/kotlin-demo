package com.example.demo

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TaskCustomRepositoryTest(val taskCustomRepositoryImpl: TaskCustomRepository): FunSpec({

    test("test") {
        val datas = taskCustomRepositoryImpl.conditionQueryTest(setOf(DaysOff.MON, DaysOff.WED))

        datas.forEach(::println)
    }

})