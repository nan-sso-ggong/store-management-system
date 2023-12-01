import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from "axios";
import {useRecoilState } from 'recoil';
import {userNameState} from "../../state";

function KakaoAfterLogin() {
    const location = useLocation();
    const navigate = useNavigate();
    const [userName, setUserName] = useRecoilState(userNameState); // Recoil 상태를 사용합니다.

    useEffect(() => {
        const getAccessToken = async () => {
            const params = new URLSearchParams(location.search);
            const code = params.get('code');

            if (!code) {
                console.error('Authorization code not found');
                return;
            }

            try {
                const response = await axios.post(`13.125.112.60:8080/api/v1/auth/customers/kakao?code=${code}`);
                console.log(response.data);
                if (response.data.success) {
                    setUserName(response.data.data.name);
                    localStorage.setItem('access_token', response.data.data.access_token);
                    navigate('/customer/selectstore');
                } else {
                    console.error('Failed to get access token', response.data.error);
                }
            } catch (error) {
                console.error('Failed to get access token', error);
            }
        };

        getAccessToken();
    }, [location, navigate, setUserName]);

    return null;
}

export default KakaoAfterLogin;