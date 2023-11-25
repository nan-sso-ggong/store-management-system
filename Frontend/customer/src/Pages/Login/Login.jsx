import React from "react";

function Login(){
    const Rest_api_key='REST API KEY' //REST API KEY
    const redirect_uri = 'http://localhost:3000/auth' //Redirect URI
    // oauth 요청 URL
    const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${Rest_api_key}&redirect_uri=${redirect_uri}&response_type=code`
    const handleLogin = ()=>{
        window.location.href = kakaoURL
    }
    return(
        <>
            <div>
                <h1>CS25</h1>
                <div>
                    <div>소셜 계정으로 로그인</div>
                    <button onClick={handleLogin}>카카오 로그인</button>
                </div>
            </div>
        </>
    )
}

export default Login;