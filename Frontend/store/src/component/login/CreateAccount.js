import React, { useState, useEffect, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

import ModuleStyle from "../../ModuleStyle.module.css"
import axios from "axios";
import store from "../../store";

function CreateAccount(){

    const search = useSelector((state) => state)
    const navigate = useNavigate()

    const [isSelect, setSelect] = useState("")
    const [ID, setID] = useState("")
    const [PW, setPW] = useState("")
    const [PW2, setPW2] = useState("")
    const [name, setName] = useState("")
    const [phone, setPhone] = useState("")
    const [email, setEmail] = useState("")
    const [store_name, setStore_name] = useState("")
    const [store_address, setStore_address] = useState("")
    const [store_callnumber, setStore_callnumber] = useState("")
    const [storeThumbnail, setStoreThumbnail] = useState(null);

    const changeID = (event) => {
        setID(event.target.value)
    }

    const changePW = (event) => {
        setPW(event.target.value)
    }

    const changePW2 = (event) => {
        setPW2(event.target.value)
    }

    const changeName = (event) => {
        setName(event.target.value)
    }

    const changePhone = (event) => {
        setPhone(event.target.value)
    }

    const changeEmail = (event) => {
        setEmail(event.target.value)
    }

    const changeStore_name = (event) => {
        setStore_name(event.target.value)
    }

    const changeStore_address = (event) => {
        setStore_address(event.target.value)
    }

    const changeStore_callnumber = (event) => {
        setStore_callnumber(event.target.value)
    }

    const changeStoreThumbnail = (event) => {
        setStoreThumbnail(event.target.files[0]);
    }


    const buttonStyle = {
        width: "400px",
        height: "40px",
        border: "1px solid #D9D9D9",
        backgroundColor: "#D9D9D9",
        marginTop: "20px",
        borderRadius: "5px",
        textAlign: "center",
        lineHeight: "40px",
        fontSize: "20px",
        margin: "auto",
        boxShadow: "1px 1px gray"
    }

    const createHeadquarters = () => {
        if (ID.length < 6) {
            alert("아이디는 6글자 이상입니다.")
        }
        else if (PW.length < 8) {
            alert("비밀번호는 8글자 이상입니다.")
        }
        else if (PW != PW2) {
            alert("비밀번호가 틀렸습니다.")
        }
        else if (name.length == 0) {
            alert("이름을 입력해주세요")
        }
        else if (phone.length < 11) {
            alert("전화번호를 입력해주세요.")
        }
        else if (email.length == 0) {
            alert("이메일을 입력해주세요")
        }
        else{
            const link = search.url + "/api/v1/auth/headquarters/join"

            const data = {
                "login_id": ID,
                "password": PW,
                "name": name,
                "email": email,
                "phone_number": phone
            }
            const config = {"Content-Type": 'application/json'};
    
            axios.post(link, data, config)
            .then((response) => {
                if (response.data.success){
                    alert("회원가입되었습니다.")
                    navigate("/")
                }
                else{
                    alert("회원가입 실패했습니다!")
                }
            }).catch(err => {
                // 에러
            });
        }
    }

    const createManager = () => {
        if (ID.length < 6) {
            alert("아이디는 6글자 이상입니다.")
        }
        else if (PW.length < 8) {
            alert("비밀번호는 8글자 이상입니다.")
        }
        else if (PW != PW2) {
            alert("비밀번호가 틀렸습니다.")
        }
        else if (name.length == 0) {
            alert("이름을 입력해주세요")
        }
        else if (phone.length < 11) {
            alert("전화번호를 입력해주세요.")
        }
        else if (email.length == 0) {
            alert("이메일을 입력해주세요")
        }
        else if (store_name.length == 0){
            alert("점포 이름을 입력해주세요")
        }
        else if (store_address.length == 0){
            alert("점포 주소를 입력해주세요")
        }
        else if (store_callnumber.length == 0){
            alert("점포 전화번호를 입력해주세요")
        }
        else{
            const link = "http://13.125.112.60:8080/api/v1/auth/managers/join"
            const formData = new FormData();

            formData.append('imageFile',storeThumbnail);

            const data = {
                "login_id": ID,
                "password": PW,
                "name": name,
                "email": email,
                "phone_number": phone,
                "store_name": store_name,
                "store_address": store_address,
                "store_callnumber": store_callnumber
            };

            formData.append(
                "joinRequest",
                new Blob([JSON.stringify(data)], { type: "application/json" })
            );
            const config = {
                    "Content-Type": "image/jpeg",
            };
            console.log(formData);

            console.log(storeThumbnail);
            axios.post(link, formData, config)
            .then((response) => {
                console.error(response.data);
                if (response.data.success){
                    alert("회원가입되었습니다.")
                    navigate("/login")
                }
                else{
                    alert("회원가입 실패했습니다!")
                }
            }).catch(err => {
                // 에러
            });
        }
    }


    return <div>
        {/* 회원가입 선택 */}
        {(isSelect == "") && <div>
            <p style={{marginLeft:"350px", marginTop:"100px", fontSize:"50px"}}>CS25 회원가입</p>
            <div style={{display:"flex", borderTop:"1px solid black", borderBottom:"1px solid black", width:"1000px", padding:"150px", margin:"auto"}}>
                <div style={{margin:"auto", width:"1000px", display:"flex"}}>
                    <img src="headquarter.png" style={{height:"300px", marginRight:"auto"}} onClick={() => {setSelect("headquarter")}}/>
                    <img src="manager.png"  style={{height:"300px"}} onClick={() => {setSelect("manager")}}/>
                </div>
            </div>
        </div>}

        {/* 본사 회원가입 */}
        {(isSelect == "headquarter") && <div style={{width:"410px", margin:"auto"}}>
            <div style={{borderBottom:"1px solid black", height:"100px", lineHeight:"100px"}}>
                <p style={{textAlign:"center", fontSize:"50px"}}>CS25 회원가입</p>
            </div>
            <div style={{display:"flex"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>아이디</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   6자리 이상 영문/숫자 조합" onChange={changeID}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>비밀번호</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   8자리 이상의 영문/숫자 조합" onChange={changePW}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>비밀번호확인</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   8자리 이상의 영문/숫자 조합" onChange={changePW2}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>이름</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   이름을 입력해 주세요" onChange={changeName}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>전화번호</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   010-0000-0000" onChange={changePhone}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>이메일</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   example@gamil.com" onChange={changeEmail}></input>
            
            <div style={{display:"flex", padding:"10px"}}>
                <input type="checkbox" style={{marginLeft:"auto"}}></input>
                <p style={{marginLeft:"10px", marginRight:"30px"}}>개인정보 수집 및 활용 동의</p>
                <div style={{height:"20px", width:"30px", border:"1px solid lightgray", display:"table", textAlign:"center", marginTop:"15px", marginRight:"0px", fontSize:"15px"}}> <p style={{display:"table-cell", verticalAlign:"middle", color:"gray"}}>보기</p> </div>
            </div>
            <div style={buttonStyle} onClick={() => {createHeadquarters();}}> 회원가입 </div>
        </div>}

        {/* 점주 회원가입 */}
        {(isSelect == "manager") && <div style={{width:"410px", margin:"auto", overflow:"auto", height:"1000px"}}>
            <div style={{borderBottom:"1px solid black", height:"100px", lineHeight:"100px"}}>
                <p style={{textAlign:"center", fontSize:"50px"}}>CS25 회원가입</p>
            </div>
            <div style={{display:"flex"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>아이디</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   6자리 이상 영문/숫자 조합" onChange={changeID}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>비밀번호</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   8자리 이상의 영문/숫자 조합" onChange={changePW}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>비밀번호확인</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   8자리 이상의 영문/숫자 조합" onChange={changePW2}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>이름</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   이름을 입력해 주세요" onChange={changeName}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>전화번호</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   010-0000-0000" onChange={changePhone}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>이메일</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   example@gamil.com" onChange={changeEmail}></input>
            
            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>점포명</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   점포명을 입력해 주세요" onChange={changeStore_name}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>점포 주소</p>
                <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder=" 서울특별시 필동로1길 30" onChange={changeStore_address}></input>

            <div style={{display:"flex", marginTop:"-10px"}}>
                
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>점포 전화번호</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
            </div>
            <input type="text" style={{marginTop:"-20px"}} className={ModuleStyle.dropdownBox} placeholder="   010-0000-0000" onChange={changeStore_callnumber}></input>

            <div style={{ display: "flex", marginTop: "-10px" }}>
                <p style={{ marginTop: "35px", marginRight: "0px", fontSize: "25px" }}>점포 이미지</p>
            </div>
            <input type="file" style={{ marginTop: "-20px" }} className={ModuleStyle.dropdownBox} onChange={changeStoreThumbnail}></input>

            <div style={{display:"flex", padding:"10px"}}>
                <input type="checkbox" style={{marginLeft:"auto"}}></input>
                <p style={{marginLeft:"10px", marginRight:"30px"}}>개인정보 수집 및 활용 동의</p>
                <div style={{height:"20px", width:"30px", border:"1px solid lightgray", display:"table", textAlign:"center", marginTop:"15px", marginRight:"0px", fontSize:"15px"}}> <p style={{display:"table-cell", verticalAlign:"middle", color:"gray"}}>보기</p> </div>
            </div>
            <div style={buttonStyle} onClick={() => {createManager();}}> 회원가입 </div>
        </div>}
    </div>

}

export default CreateAccount