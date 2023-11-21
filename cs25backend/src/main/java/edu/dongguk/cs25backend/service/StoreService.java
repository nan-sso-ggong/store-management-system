package edu.dongguk.cs25backend.service;

import edu.dongguk.cs25backend.domain.Manager;
import edu.dongguk.cs25backend.domain.Store;
import edu.dongguk.cs25backend.dto.request.StoreRequestDto;
import edu.dongguk.cs25backend.exception.CS25Exception;
import edu.dongguk.cs25backend.exception.ErrorCode;
import edu.dongguk.cs25backend.repository.ManagerRepository;
import edu.dongguk.cs25backend.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ManagerRepository managerRepository;

    //C
    public Boolean createStore(StoreRequestDto requestDto) {
        storeRepository.findTop1ByNameOrCallNumber(requestDto.getName(), requestDto.getCallNumber())
                .ifPresent(s -> {
                    throw new CS25Exception(ErrorCode.DUPLICATION_STORE);
                });
        Manager manager = managerRepository.findById(requestDto.getManagerId())
                .orElseThrow(() -> new CS25Exception(ErrorCode.NOT_FOUND_MANAGER));


        storeRepository.save(Store.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .callNumber(requestDto.getCallNumber())
                .thumbnail(requestDto.getThumbnail())
                .manager(manager).build());

        return Boolean.TRUE;
    }
    //R
    //보류

    //U
    //보류

    //D
    //디비에서 지우죠!
}
