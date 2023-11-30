import React, { useState, useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import { IoMdArrowDropdown } from "react-icons/io";
import { useSelector, useDispatch } from "react-redux";

import ModuleStyle from "../../ModuleStyle.module.css"

function Search(){

    const [address, setAddress] = useState("")
    const [open, setopen] = useState(false)
    const [name, setname] = useState([])

    const boxstyle = {
        width:"1500px",
        height:"200px",
        border:"1px solid #F0F1F3",
        backgroundColor:"white",
        //display : d,
        textAlign:"center",
        //overflow:"auto",
        marginLeft : "27.5px",
        marginTop : "20px",
        marginBottom : "5px",
        //fontSize: f,
        zIndex:4

    }

    const listStyle = {
        zIndex:10,
        width:"300px",
        height:"40px",
        border:"1px solid #F0F1F3",
        backgroundColor:"white",
        textAlign:"center",
        lineHeight:"40px",
        position: "relative",
    }

    const out = {
        zIndex: 3,
        display: "block", 
        position: "fixed",
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
    }

    const savename = event => {
        setname(event.target.value);
    };

    const saveaddr = event => {
        setAddress(event.target.value);
    };

    const dispatch = useDispatch();

    const storemanager = () => {

        const data = {
            name : name,
            addr : address,
        }
        dispatch({ type: 'employee', payload: data });
    };


    return <div style={boxstyle}>
        {(open) && <div style={out} onClick={() => setopen(false)} ></div>}
        <div style={{display:"flex"}}>
            <p style={{marginTop:"15px", marginLeft:"25px", fontSize:"30px"}}>검색</p>
        </div>
        <div style={{display:"flex", height:"60px"}}>
            <div style={{marginLeft:"20px", marginRight:"25px", marginTop:"-25px", display:"flex"}}>
                <p style={{fontSize:"25px", marginLeft:"5px", marginRight:"75px"}}>이름</p>
                <input type="text" className={ModuleStyle.inputBox} placeholder=" 이름을 입력하세요" onChange={savename}></input>
            </div>

        </div>
        <div style={{display:"flex"}}>
            <p style={{fontSize:"25px", marginLeft:"25px", marginRight:"25px" , marginTop:"5px"}}>지역 검색</p>
            <input type="text" style={{marginTop:"0px"}} className={ModuleStyle.inputBox} placeholder=" 지역을 입력하세요" onChange={saveaddr}></input>
            <button style={{marginLeft:"10px", marginTop:"0px"}} className={ModuleStyle.whiteSmallButton} onClick={storemanager}>검색</button>
        </div>
    </div>

}

export default Search