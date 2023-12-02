import React, {useState} from 'react';
import loginImg from "../../image/cs25login.png";
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import {
    SignUpButton,
    ForgotPasswordButton,
    InputPwd,
    InputId,
    LoginType,
    LoginBtn,
    LoginButton,
    LoginTitle
} from '../../component/LoginStyle';




function Login() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSignUp = () => {
        // Navigate to the SignUp page when the SignUp button is clicked
        navigate('/login/signup_choice');
    };


    const handleLogin = () => {
        // Create a data object with username and password
        const data = {
            username: username,
            password: password,
        };

        // Send a POST request using axios
        axios.post('https://21fbeac1-c1d4-41dc-aeeb-e04b9315664e.mock.pstmn.io/api/vi/auth/managers/login', data,{

        })
            .then(response => {
                // Handle the response from the server
                console.log(response.data);
                // You may want to redirect the user or perform other actions based on the response
            })
            .catch(error => {
                console.error('Error:', error);
                // Handle errors if any
            });
    };


    return (
        <div>
        <LoginTitle>
            CS25 로그인
        </LoginTitle>
        <div style={{marginTop: "200px"}} className="App">
            <hr style={{zIndex: 1, position: 'relative'}}/>
            <div style={{zIndex: 0, position: 'relative'}}>
                <img style={{marginLeft: "250px", marginTop: "-10px", marginBottom: "-10px", zIndex: 0, position: 'relative' }} src={loginImg}  />
            </div>
            <div style={{zIndex: 10, position: 'relative'}}><hr/></div>
        </div>
            <div>
                <LoginType>점주/본사 로그인</LoginType>
                <InputId type="text" placeholder="아이디를 입력하시오." onChange={(e) => setUsername(e.target.value)} />
                <InputPwd type="password" placeholder="비밀번호를 입력하시오." onChange={(e) => setPassword(e.target.value)} />
                <LoginBtn></LoginBtn>
                <LoginButton value="로그인" onClick={handleLogin}>로그인</LoginButton>
                <SignUpButton onClick={handleSignUp}>회원가입</SignUpButton>
                <ForgotPasswordButton>비밀번호 찾기</ForgotPasswordButton>
            </div>

        </div>
    );
}

export default Login;
