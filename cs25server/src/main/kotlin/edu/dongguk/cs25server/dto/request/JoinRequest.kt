package edu.dongguk.cs25server.dto.request

import edu.dongguk.cs25server.domain.Headquarters
import edu.dongguk.cs25server.domain.Manager
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.security.crypto.password.PasswordEncoder

class JoinRequest (

    @field:Pattern(regexp = "^[A-Za-z][A-Za-z0-9]{5,}\$",
        message = "아이디는 6글자 이상입니다.")
    val login_id: String,

    @field:Pattern(regexp = "^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{7,}\$",
        message = "비밀번호는 8글자 이상입니다.")
    val password: String,

    @field:NotBlank(message = "이름를 입력해주세요")
    val name: String,

    @field:NotBlank(message = "이메일을 입력해주세요")
    @field:Email(message ="이메일 양식에 맞게 입력해주세요")
    val email: String,

    @field:NotBlank(message = "전화번호를 입력해주세요")
    @field:Pattern(regexp = "^010-[0,9]{4}-[0,9]{4}$", message = "전화번호 양식에 맞게 입력해주세요")
    val phone_number: String
) {

    fun toManager(): Manager = Manager(
        loginId = this.login_id,
        password = this.password,
        name = this.name,
        email = this.email,
        phoneNumber = this.phone_number
    )

    fun toHeadquarters(): Headquarters = Headquarters(
        loginId = this.login_id,
        password = this.password,
        name = this.name,
        email = this.email,
        phoneNumber = this.phone_number
    )

}