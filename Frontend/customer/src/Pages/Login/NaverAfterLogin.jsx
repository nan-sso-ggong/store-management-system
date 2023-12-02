import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import {useRecoilState } from 'recoil';
import {userNameState} from "../../state";
import api from "../../Axios";
const href = window.location.href;
let params = new URL(window.location.href).searchParams;
let code = params.get("code");
const NaverAfterLogin = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [userName, setUserName] = useRecoilState(userNameState);
    useEffect(() => {
        api.post(`/auth/customers/naver?code=${code}`)
            .then(response => {
                console.log(response)
                setUserName(response.data.data.name);

                // localStorage 저장
                localStorage.setItem("access_token", response.data.data.access_token);
                localStorage.setItem("refresh_token", response.data.data.refresh_token);
                // 점포 선택 페이지로 이동
                navigate('/customer/selectstore');
            })
            .catch(error => {
                console.error(error);
            });
    }, [location, navigate, setUserName]);
}

export default NaverAfterLogin;