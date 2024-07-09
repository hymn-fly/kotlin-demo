package com.example.demo

import jakarta.persistence.*

@Entity
@Table(name = "tasks")
data class Task(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val description: String,

    val contents: String
) {

    class ContentConverter(): AttributeConverter<Set<String>?, String?> {
        override fun convertToDatabaseColumn(attribute: Set<String>?): String? {
            val sb = StringBuilder()
            attribute?.forEach { sb.append(it).append(",") }
            return sb.toString()
        }

        override fun convertToEntityAttribute(dbData: String?): Set<String> {
            val strings: List<String> = dbData?.split(",") ?: listOf()
            return strings.toSet()
        }

    }
    internal constructor() : this(0, "", "")
}