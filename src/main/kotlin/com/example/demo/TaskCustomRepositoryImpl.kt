package com.example.demo

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class TaskCustomRepositoryImpl(private val queryFactory: JPAQueryFactory): TaskCustomRepository {

    override fun queryData(): List<Task> {
        val data = queryFactory.selectFrom(QTask.task)
        return data.fetch()
    }

    override fun conditionQueryTest(contents: Set<String>): List<Task> {
        val contentsCondition = QTask.task.contents.any().notIn(contents)

        val query = queryFactory.selectFrom(QTask.task).where(contentsCondition)
        return query.fetch()
    }


}