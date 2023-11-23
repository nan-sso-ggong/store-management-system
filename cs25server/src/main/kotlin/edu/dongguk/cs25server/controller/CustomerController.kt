package edu.dongguk.cs25server.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController {

    // TODO 점포 검색
    @GetMapping("/search-store")
    fun searchStore(@RequestParam("name") name: String) {

    }

}