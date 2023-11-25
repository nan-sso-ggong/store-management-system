import React from "react";
import styled from "styled-components";
import KakaoLogin from "./KakaoLogin";
import NaverLogin from "./NaverLogin";
import GoogleLogin from "./GoogleLogin";


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
    return(
        <>
            <CONTAINER>
                <SPAN>CS25</SPAN>
                <BUTTON>
                    <div><p>소셜 계정으로 로그인</p></div>
                    <GoogleLogin/>
                    <KakaoLogin/>
                    <NaverLogin/>
                </BUTTON>
            </CONTAINER>
        </>
    )
}

export default Login;