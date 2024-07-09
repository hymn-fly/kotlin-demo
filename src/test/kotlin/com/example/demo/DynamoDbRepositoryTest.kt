package com.example.demo

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DynamoDbRepositoryTest(val dynamoDbRepository: DynamoDbRepository): FunSpec({

    class TestTable(
        @DynamodbAnnotation.PartitionKey
        val id: Int,

        @DynamodbAnnotation.SortKey
        val sort: String,

        val key: String,

        val key2: String
    )

    test("test") {
        val table = dynamoDbRepository.getTable("test_table", TestTable::class)
        dynamoDbRepository.putItem(table, TestTable(2, "sort", "value", "value1"))

    }
})