import React from 'react';
import kakaoImg from "../../Image/kakao_login_large_narrow.png";

function KakaoLogin() {
    const loginUrl = `http://13.125.112.60:8080/oauth2/authorization/kakao`

    const handleLogin = () => {
        window.location.href = loginUrl;
    };

    return (
        <>
        <div><img src={kakaoImg} alt="Login with Kakao" onClick={handleLogin} /></div>
        </>
    );
}

export default KakaoLogin;