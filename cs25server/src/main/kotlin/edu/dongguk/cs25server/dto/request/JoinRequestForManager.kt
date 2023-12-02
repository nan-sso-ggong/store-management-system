package edu.dongguk.cs25server.dto.request

import edu.dongguk.cs25server.domain.Image
import edu.dongguk.cs25server.domain.Manager
import edu.dongguk.cs25server.domain.Store
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.web.multipart.MultipartFile

class JoinRequestForManager(
    @field:Pattern(
        regexp = "^[A-Za-z0-9]{6,}\$",
        message = "아이디는 6글자 이상, 영어, 숫자 포함입니다."
    )
    val login_id: String,

    @field:Pattern(
        regexp = "^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$",
        message = "비밀번호 8글자 이상, 영어, 숫자, 특수문자 포함입니다."
    )
    val password: String,

    @field:NotBlank(message = "이름를 입력해주세요")
    val name: String,

    @field:NotBlank(message = "이메일을 입력해주세요")
    @field:Email(message = "이메일 양식에 맞게 입력해주세요")
    val email: String,

    @field:NotBlank(message = "전화번호를 입력해주세요")
    @field:Pattern(regexp = "^010-[0-9]{4}-[0-9]{4}$", message = "전화번호 양식에 맞게 입력해주세요")
    val phone_number: String,
    //--------------------점포 -----------------------
    @field:NotBlank(message = "점포 이름을 입력해주세요")
    val store_name: String,

    @field:NotBlank(message = "점포 주소를 입력해주세요")
    val store_address: String,

    @field:NotBlank(message = "점포 전화번호를 입력해주세요")
    @field:Pattern(regexp = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$", message = "전화번호 양식에 맞게 입력해주세요")
    val store_callnumber: String,
) {
    fun toManager(): Manager = Manager(
        loginId = this.login_id,
        password = this.password,
        name = this.name,
        email = this.email,
        phoneNumber = this.phone_number
    )

    fun toStore(manager: Manager, image: Image): Store = Store(
        name = this.store_name,
        address = this.store_address,
        callNumber = this.store_callnumber,
        thumbnail = image,
        manager = manager
    )
}