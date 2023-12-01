import React from 'react';
import NaverImg from "../../Image/btnG_완성형.png";

function NaverLogin() {
    const loginUrl = `http://13.125.112.60:8080/oauth2/authorization/naver`

    const handleLogin = () => {
        window.location.href = loginUrl; // 받아온 로그인 URL로 리다이렉트
    };

    return (
        <>
            <div><img src={NaverImg} alt="Login with Naver" onClick={handleLogin} /></div>
        </>
    );
}

export default NaverLogin;