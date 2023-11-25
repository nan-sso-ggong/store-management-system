import React, { useEffect, useState } from 'react';
import axios from 'axios';
import NaverImg from "../../Image/btnG_완성형.png";

function NaverLogin() {
    const [loginUrl, setLoginUrl] = useState('');

    useEffect(() => {
        const getLoginUrl = async () => {
            try {
                const response = await axios.get('/oauth2/authorization/naver');
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
            <div><img src={NaverImg} alt="Login with Naver" onClick={handleLogin} /></div>
        </>
    );
}

export default NaverLogin;