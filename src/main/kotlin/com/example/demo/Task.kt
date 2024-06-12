package com.example.demo

import jakarta.persistence.*
import java.lang.reflect.Constructor

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val description: String
) {
    internal constructor() : this(0, "")
}