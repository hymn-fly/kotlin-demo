package com.example.demo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/task")
class TaskController(val service: TaskService) {

    @GetMapping
    fun tasks() = ResponseEntity.ok(service.getAll())

    @PostMapping
    fun create(@RequestBody task : Task): ResponseEntity<String> {
        val result = service.save(task)
        return ResponseEntity.ok("added task with description " + result.description)
    }

    @GetMapping("/test")
    fun test() = listOf(1, 2, 3)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        if (service.delete(id)) {
            return ResponseEntity.ok("Task with id $id deleted")
        }
        return ResponseEntity.status(404).body("Task with id $id not found")
    }
}