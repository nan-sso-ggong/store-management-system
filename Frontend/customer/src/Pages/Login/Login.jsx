import React from "react";
import styled from "styled-components";
import kakaoImg from "../../Image/kakao_login_large_narrow.png";
import googleImg from "../../Image/web_light_sq_SI@4x.png";
import naverImg from "../../Image/btnG_완성형.png";

const CONTAINER = styled.div`
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  flex-direction: column;
`
const SPAN =styled.div`
  font-weight: bold;
  font-size: 120px;
  color: forestgreen;
  margin-top: 80px;
`
const BUTTON=styled.div`
  margin-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: center; /* 세로 중앙 정렬 */
  width:700px;
  height:360px;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
  border: 1px solid rgba(150,150,150,0.1);
img{
  width:350px;
  height: 80px;
  margin-top:5px;
  cursor: pointer;
}
  p{
    margin-top:25px;
    margin-right:190px;
    font-weight: bolder;
    color:rgba(150,150,150,0.8);
  }
`

function Login(){
    const Rest_api_key='REST API KEY' //REST API KEY
    const redirect_uri = 'http://localhost:3000/auth'
    const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${Rest_api_key}&redirect_uri=${redirect_uri}&response_type=code`
    const handleLogin = ()=>{
        window.location.href = kakaoURL
    }
    return(
        <>
            <CONTAINER>
                <SPAN>CS25</SPAN>
                <BUTTON>
                    <div><p>소셜 계정으로 로그인</p></div>
                    <div><img src={googleImg} alt="Login with Google" onClick={handleLogin} /></div>
                    <div><img src={kakaoImg} alt="Login with Kakao" onClick={handleLogin} /></div>
                    <div><img src={naverImg} alt="Login with Naver" onClick={handleLogin} /></div>
                </BUTTON>
            </CONTAINER>
        </>
    )
}

export default Login;