import React, { useEffect, useState } from 'react';
import axios from 'axios';
import kakaoImg from "../../Image/kakao_login_large_narrow.png";

function KakaoLogin() {
    const [loginUrl, setLoginUrl] = useState('');

    useEffect(() => {
        const getLoginUrl = async () => {
            try {
                const response = await axios.get('/oauth2/authorization/kakao');
                setLoginUrl(response.data.url);
            } catch (error) {
                console.error('Failed to get login URL', error);
            }
        };

        getLoginUrl();
    }, []);

    const handleLogin = () => {
        window.location.href = loginUrl; // 받아온 로그인 URL로 리다이렉트
    };

    return (
        <>
        <div><img src={kakaoImg} alt="Login with Kakao" onClick={handleLogin} /></div>
        </>
    );
}

export default KakaoLogin;