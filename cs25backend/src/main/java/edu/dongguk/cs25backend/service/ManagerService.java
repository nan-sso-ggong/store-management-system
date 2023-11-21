package edu.dongguk.cs25backend.service;

import edu.dongguk.cs25backend.domain.Manager;
import edu.dongguk.cs25backend.dto.request.ManagerRequestDto;
import edu.dongguk.cs25backend.exception.CS25Exception;
import edu.dongguk.cs25backend.exception.ErrorCode;
import edu.dongguk.cs25backend.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    //C
    public Boolean createManager(ManagerRequestDto requestDto) {
        managerRepository.findTop1ByLoginIdOrNameOrEmailOrPhoneNumber(requestDto.getLoginId(),
                        requestDto.getName(), requestDto.getEmail(), requestDto.getPhoneNumber())
                .ifPresent(m -> {
                    throw new CS25Exception(ErrorCode.DUPLICATION_MANAGER);
                });

        managerRepository.save(Manager.builder()
                .loginId(requestDto.getLoginId())
                .password(requestDto.getPassword())
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .phoneNumber(requestDto.getPhoneNumber())
                .userRole(requestDto.getUserRole())
                .memberShip(requestDto.getMemberShip())
                .build());

        return Boolean.TRUE;
    }
    //R
    //보류

    //U
    //보류

    //D
    //디비에서 지우죠!

}
