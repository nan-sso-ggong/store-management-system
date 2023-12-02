import styled from 'styled-components';


const LoginTitle = styled.div`
  position: absolute;
  width: 450px;
  height: 85px;
  left: 172px;
  top: 80px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: bold;
  font-size: 60px;
  line-height: 85px; /* Assuming you want line-height to be the same as the height */
`;

/* 본사 점주 선택 화면 텍스트*/
const SignupChoiceText = styled.div`
  position: relative;
  width: 250px;
  height: 85px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: bold;
  font-size: 60px;
  line-height: 85px; /* Assuming you want line-height to be the same as the height */
`;

/* 회원가입 */
const SignUpButton = styled.div`
  position: absolute;
  width: 90px;
  height: 44px;
  left: 1223px;
  top: 523px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 500;
  font-size: 15px;
  line-height: 30px;
  text-decoration-line: underline;
  color: rgba(0, 0, 0, 0.4);
`;

/* 비밀번호 찾기 */
const ForgotPasswordButton = styled.div`
  position: absolute;
  width: 132px;
  height: 44px;
  left: 1096px;
  top: 523px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 500;
  font-size: 15px;
  line-height: 30px;
  text-decoration-line: underline;
  color: rgba(0, 0, 0, 0.4);
`;

/* Rectangle 2827 비밀번호*/
const InputPwd = styled.input`
  box-sizing: border-box;
  position: absolute;
  width: 343px;
  height: 42px;
  left: 941px;
  top: 472px;
  background: #FFFFFF;
  border: 1px solid #000000;
`;

/* Rectangle 2829 */
const InputId = styled.input`
  box-sizing: border-box;
  position: absolute;
  width: 343px;
  height: 41px;
  left: 941px;
  top: 406px;
  background: #FFFFFF;
  border: 1px solid #000000;
`;

/* 점주/본사사 로그인 */
const LoginType = styled.div`
  position: absolute;
  width: 483px;
  height: 44px;
  left: 941px;
  //left: 741px;
  top: 348px;
  //top: 447px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 500;
  font-size: 20px;
  line-height: 30px;
  color: rgba(0, 0, 0, 0.4);
`;

/* Rectangle 2828 */
const LoginBtn = styled.div`
  position: absolute;
  width: 107px;
  height: 120px;
  left: 1299px;
  top: 397px;
  background: #397CA8;
  font-weight: bold;
  text-align: center;
`;

/* 로그인 */
const LoginButton = styled.button`
  position: absolute;
  width: 74px;
  height: 33px;
  left: 1317px;
  top: 443px;
  font-family: 'Inter';
  font-style: normal;
  font-weight: 500;
  font-size: 17px;
  border: none;
  color: #ffffff;
  background-color: transparent;
`;


export {
    SignUpButton,
    ForgotPasswordButton,
    InputPwd,
    InputId,
    LoginType,
    LoginBtn,
    LoginButton,
    LoginTitle,
    SignupChoiceText,
};


