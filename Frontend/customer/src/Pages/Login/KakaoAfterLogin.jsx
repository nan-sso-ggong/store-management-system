import React, { useEffect } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import {useRecoilState } from 'recoil';
import {userNameState} from "../../state";
import api from '../../Axios';


const href = window.location.href;
let params = new URL(window.location.href).searchParams;
let code = params.get("code");

const KakaoAfterLogin = () => {

    const location = useLocation();
    const navigate = useNavigate();
    const [userName, setUserName] = useRecoilState(userNameState); // Recoil 상태를 사용합니다.
    useEffect(() => {
        api.post(`/auth/customers/kakao?code=${code}`) 
            .then(response => {
                console.log(response) //이렇게 출력하면 반환되는 json 모두 콘솔에서 볼 수 있음
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

export default KakaoAfterLogin;