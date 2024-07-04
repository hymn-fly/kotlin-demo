package com.example.demo

interface TaskCustomRepository {

    fun queryData(): List<Task>

    fun conditionQueryTest(contents: Set<String>): List<Task>
}