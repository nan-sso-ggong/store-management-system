package edu.dongguk.cs25server.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class StoreRequestDto(
    @field:NotBlank(message = "이름를 입력해주세요")
    val name: String,
    @field:NotBlank(message = "점포 주소를 입력해주세요")
    val address: String,
    @field:NotBlank(message = "점포 전화번호를 입력해주세요")
    @field:Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$", message = "전화번호 양식에 맞게 입력해주세요")
    val callNumber: String,
)