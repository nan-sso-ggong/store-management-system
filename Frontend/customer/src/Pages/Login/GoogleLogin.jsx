import React from 'react';
import googleImg from "../../Image/web_light_sq_SI@4x.png";

function GoogleLogin() {
    const loginUrl = `http://13.125.112.60:8080/oauth2/authorization/google`

    const handleLogin = () => {
        window.location.href = loginUrl;
    };

    return (
        <>
            <div><img src={googleImg} alt="Login with Goole" onClick={handleLogin} /></div>
        </>
    );
}

export default GoogleLogin;