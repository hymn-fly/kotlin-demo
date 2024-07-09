package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(val dynamoDbRepository: DynamoDbRepository) {

//    @GetMapping("/tables")
//    fun tables() = dynamoDbRepository.getTableList()
//
//    @PostMapping("/tables/items")
//    fun addItem(@RequestBody keyValueMap: MutableMap<String, String>) {
//        dynamoDbRepository.putItem("test_table", keyValueMap)
//    }
}