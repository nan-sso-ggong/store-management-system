import React, { useState, useEffect, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

import ModuleStyle from "../../ModuleStyle.module.css"
import axios from "axios";

function Adminlogin(){

    const search = useSelector((state) => state)

    const [ID, setID] = useState("")
    const [PW, setPW] = useState("")

    const saveID = event => {
        setID(event.target.value);
    };

    const savePW = event => {
        setPW(event.target.value);
    };
    
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const logining = () => {
        const link = search.url + "/api/v1/auth/headquarters/login"

        const data = {
            "login_id" : ID,
            "password" : PW 
        }
        const config = {"Content-Type": 'application/json'};

        axios.post(link, data, config)
        .then((response) => {
            if (response.data.success){
                dispatch({ type:"login", payload:response.data.data})
                navigate("/admin/headquaurters")
            }
            else{
                alert("아이디 또는 비밀번호를 확인해주세요!")
            }
        }).catch(err => {
            // 에러
        });
    }

    return <div>
        <p style={{marginLeft:"350px", marginTop:"100px", fontSize:"50px"}}>CS25 관리자 로그인</p>
        <div style={{display:"flex", borderTop:"1px solid black", borderBottom:"1px solid black", width:"1200px", padding:"50px", margin:"auto"}}>

            <img style={{height:"500px", marginLeft:"0px"}} src="LoginImage.png"></img>
            <div style={{marginLeft:"100px", display:"table"}}>
                <div style={{display:"table-cell", verticalAlign:"middle"}}>
                    <p style={{fontSize:"20px"}} onClick={() => {navigate("/login")}}> 점주/본사 로그인 </p>
                    <div style={{display:"flex", marginTop:"-20px"}}>
                        <div>
                            <input className={ModuleStyle.inputBox} placeholder="   아이디" onChange={saveID}/><p style={{height:"0px"}}/>
                            <input type="password" style={{marginTop:"0px"}} className={ModuleStyle.inputBox} placeholder="   비밀번호" onChange={savePW}/>
                        </div>
                        <div style={{backgroundColor:"#397CA8", width:"100px", height:"105px", marginTop:"20px", marginLeft:"30px", display:"table", textAlign:"center"}} onClick={() => {logining();}}>
                            <p style={{display:"table-cell", verticalAlign:"middle", color:"white", fontSize:"20px"}}>로그인</p>
                        </div>
                    </div>
                    <div style={{display:"flex", marginTop:"10px"}}>
                        <Link style={{marginRight:"10px"}}>비밀번호 찾기</Link>
                        <Link to="/회원가입">회원가입</Link>
                    </div>
                </div>
            </div>
        </div>
    </div>
}

export default Adminlogin