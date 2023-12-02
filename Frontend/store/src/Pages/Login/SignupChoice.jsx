import React from 'react';
import {
    LoginTitle,
    SignupChoiceText
} from '../../component/LoginStyle';
import { AiOutlineShop } from "react-icons/ai";
import { LuBuilding2 } from "react-icons/lu";
import { useNavigate } from 'react-router-dom';

function SignupChoice() {
    const navigate = useNavigate();
    const handleSignUp = () => {
        // Navigate to the SignUp page when the SignUp button is clicked
        navigate('/login/signup_form');
    };
    return (
        <div>
            <LoginTitle>
                CS25 회원가입
            </LoginTitle>
            <div style={{ marginTop: "300px" }} className="App">
                <div>
                    <hr />
                </div>
                <LuBuilding2 size={200} style={{ marginLeft: "400px" }} />
                <AiOutlineShop onClick={handleSignUp} size={200} style={{ marginLeft: "500px" }} />
                <div>
                    <SignupChoiceText style={{ fontSize: "50px", marginLeft: "400px" }}> 본사 </SignupChoiceText>
                    <SignupChoiceText style={{ fontSize: "50px", marginLeft: "20px" }}> 점주 </SignupChoiceText>
                </div>
                <div style={{ zIndex: 10, position: 'relative' }}><hr /></div>
            </div>
        </div>
    );
}

export default SignupChoice;
