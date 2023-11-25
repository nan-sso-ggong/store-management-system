import React, { useEffect, useState } from 'react';
import axios from 'axios';
import googleImg from "../../Image/web_light_sq_SI@4x.png";

function GoogleLogin() {
    const [loginUrl, setLoginUrl] = useState('');

    useEffect(() => {
        const getLoginUrl = async () => {
            try {
                const response = await axios.get('/oauth2/authorization/google');
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
            <div><img src={googleImg} alt="Login with Goole" onClick={handleLogin} /></div>
        </>
    );
}

export default GoogleLogin;