package edu.dongguk.cs25backend.controller;

import edu.dongguk.cs25backend.dto.request.StoreRequestDto;
import edu.dongguk.cs25backend.dto.response.RestResponse;
import edu.dongguk.cs25backend.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
@Slf4j
public class StoreController {
    private final StoreService storeService;

    @PostMapping("")
    public RestResponse<Boolean> createManager(@RequestBody StoreRequestDto requestDto) {
        return new RestResponse<Boolean>(storeService.createStore(requestDto));
    }
}
