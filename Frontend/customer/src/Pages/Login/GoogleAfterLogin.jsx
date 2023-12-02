import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import api from "../../Axios"
import { useRecoilState } from 'recoil';
import {userNameState} from "../../state";

const href = window.location.href;
let params = new URL(window.location.href).searchParams;
let code = params.get("code");
const GoogleAfterLogin = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [userName, setUserName] = useRecoilState(userNameState);
    useEffect(() => {
        api.post(`/auth/customers/google?code=${code}`)
            .then(response => {
                console.log(response)
                setUserName(response.data.data.name);

                // localStorage 저장
                localStorage.setItem("access_token", response.data.data.access_token);
                localStorage.setItem("refresh_token", response.data.data.refresh_token);
                api.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('access_token')}`;

                // 점포 선택 페이지로 이동
                navigate('/customer/selectstore');
            })
            .catch(error => {
                console.error(error);
            });
    }, [location, navigate, setUserName]);

}

export default GoogleAfterLogin;