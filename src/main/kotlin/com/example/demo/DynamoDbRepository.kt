package com.example.demo

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.*
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument
import java.time.LocalDateTime
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

@Repository
class DynamoDbRepository(val client: DynamoDbEnhancedClient, val objectMapper: ObjectMapper) {


    fun getTable(tableName: String, tableScheme: KClass<*>): DynamodbTable {
        val fields = tableScheme.memberProperties
        var partitionKey: KProperty<*>? = null
        var sortKey: KProperty<*>? = null
        for (field in fields) {
            if (field.findAnnotations(DynamodbAnnotation.PartitionKey::class).isNotEmpty()) {
                partitionKey = field
            }
            if (field.findAnnotations(DynamodbAnnotation.SortKey::class).isNotEmpty()) {
                sortKey = field
            }
        }

        val tableBuilder = TableSchema.documentSchemaBuilder()
        if (partitionKey != null) {
            tableBuilder.addIndexPartitionKey(
                TableMetadata.primaryIndexName(),
                partitionKey.name,
                convert(partitionKey.javaField!!.type)
            )
        }
        if (sortKey != null) {
            tableBuilder.addIndexSortKey(
                TableMetadata.primaryIndexName(),
                sortKey.name,
                convert(sortKey.javaField!!.type)
            )
        }
        val table: DynamoDbTable<EnhancedDocument> = client.table(
            tableName, tableBuilder.attributeConverterProviders(
                AttributeConverterProvider.defaultProvider()
            ).build()
        )

        try {
            table.createTable()
        } catch (e: Exception) {
            // do nothing
        }
        return DynamodbTable(table)
    }

    fun putItem(table: DynamodbTable, row: Any) {
        val doc: EnhancedDocument = EnhancedDocument.builder()
            .attributeConverterProviders(AttributeConverterProvider.defaultProvider())
            .json(objectMapper.writeValueAsString(row))
            .build()
        table.table.putItem(doc)
    }

    private fun convert(type: Class<*>): AttributeValueType {
        return when (type) {
            Double::class.java, Float::class.java, Long::class.java, Int::class.java -> AttributeValueType.N
            String::class.java, LocalDateTime::class.java -> AttributeValueType.S
            else -> throw Exception()
        }
    }

}

class DynamodbAnnotation {
    @Target(AnnotationTarget.PROPERTY)
    annotation class PartitionKey

    @Target(AnnotationTarget.PROPERTY)
    annotation class SortKey
}

class DynamodbTable(internal val table: DynamoDbTable<EnhancedDocument>)