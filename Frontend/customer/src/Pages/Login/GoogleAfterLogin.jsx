import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useRecoilState } from 'recoil';
import {userNameState} from "../../state";
function GoogleAfterLogin() {
    const location = useLocation();
    const navigate = useNavigate();
    const [userName, setUserName] = useRecoilState(userNameState);

    useEffect(() => {
        const getAccessToken = async () => {
            const params = new URLSearchParams(location.search);
            const code = params.get('code');

            if (!code) {
                console.error('Authorization code not found');
                return;
            }

            try {
                const response = await axios.post(`/api/v1/auth/customers/google?code=${code}`);
                console.log(response.data);
                if (response.data.success) {
                    setUserName(response.data.data.name);
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

export default GoogleAfterLogin;