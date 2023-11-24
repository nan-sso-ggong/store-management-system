package edu.dongguk.cs25server.controller

import edu.dongguk.cs25server.dto.request.ManagerRequestDto
import edu.dongguk.cs25server.dto.response.RestResponse
import edu.dongguk.cs25server.service.ManagerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/managers")
class ManagerController(private val managerService: ManagerService) {

    @PostMapping("")
    fun createManager(@RequestBody requestDto: ManagerRequestDto): RestResponse<Boolean> {
        println(requestDto.email + "/" + requestDto.loginId);
        return RestResponse(managerService.createManager(requestDto))
    }
}
