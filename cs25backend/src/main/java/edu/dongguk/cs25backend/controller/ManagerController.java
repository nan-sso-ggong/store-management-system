package edu.dongguk.cs25backend.controller;

import edu.dongguk.cs25backend.dto.request.ManagerRequestDto;
import edu.dongguk.cs25backend.dto.response.RestResponse;
import edu.dongguk.cs25backend.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/managers")
@Slf4j
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("")
    public RestResponse<Boolean> createManager(@RequestBody ManagerRequestDto requestDto) {
        return new RestResponse<Boolean>(managerService.createManager(requestDto));
    }
}
