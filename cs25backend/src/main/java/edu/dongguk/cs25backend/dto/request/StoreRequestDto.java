package edu.dongguk.cs25backend.dto.request;

import edu.dongguk.cs25backend.domain.Manager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequestDto {
    private String name;

    private String address;

    private String callNumber;

    private String thumbnail;

    private Manager manager;
    private Long managerId;
}
