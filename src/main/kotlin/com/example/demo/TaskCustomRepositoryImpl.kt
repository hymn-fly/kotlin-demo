package com.example.demo

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class TaskCustomRepositoryImpl(private val queryFactory: JPAQueryFactory): TaskCustomRepository {

    override fun queryData(): List<Task> {
        val data = queryFactory.selectFrom(QTask.task)
        return data.fetch()
    }

    override fun conditionQueryTest(contents: Set<DaysOff>): List<Task> {
        val builder = BooleanBuilder()
        contents.forEach { builder.or(QTask.task.contents.contains(it.name)) }

        val query = queryFactory.selectFrom(QTask.task).where(builder)
        return query.fetch()
    }


}