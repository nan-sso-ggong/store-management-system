import React, { useState, useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import { IoMdArrowDropdown } from "react-icons/io";
import { useSelector, useDispatch } from "react-redux";

import ModuleStyle from "../../ModuleStyle.module.css"

function Search(){
    
    const [searchCategory, setsearchCategory] = useState("카테고리를 선택하세요")
    const [open, setopen] = useState(false)
    const [name, setname] = useState("")

    const boxstyle = {
        width:"1500px",
        height:"130px",
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

    const dispatch = useDispatch();

    const headquaurters = () => {
        
        let tempCategory = ""
        if (searchCategory != "카테고리를 선택하세요"){
            tempCategory = searchCategory
        }

        const data = {
            name : name,
            category : tempCategory
        }
        dispatch({ type: 'headquaurters', payload: data });
    };

    return <div style={boxstyle}>
        {(open) && <div style={out} onClick={() => setopen(false)} ></div>}
        <div style={{display:"flex"}}>
            <p style={{marginTop:"15px", marginLeft:"25px", fontSize:"30px"}}>재고 검색</p>
        </div>
        <div style={{display:"flex"}}>
            <div style={{marginLeft:"20px", marginRight:"25px", marginTop:"-25px", display:"flex"}}>
                <p style={{fontSize:"25px", marginRight:"25px"}}>카테고리</p>
                <div>
                    <div style={{marginTop:"20px"}} className={ModuleStyle.inputBox} onClick={() => setopen(true)}>{searchCategory}</div>
                    {(open) && <div style={{height:"250px", overflow:"auto"}}>
                        <div style={listStyle} onClick={() => {setopen(false); setsearchCategory("아이스크림")}}>아이스크림</div>
                        <div style={listStyle} onClick={() => {setopen(false); setsearchCategory("라면")}}>라면</div>
                        <div style={listStyle} onClick={() => {setopen(false); setsearchCategory("음료")}}>음료</div>
                        <div style={listStyle} onClick={() => {setopen(false); setsearchCategory("과자")}}>과자</div>
                    </div>}
                </div>
                <p style={{fontSize:"25px", marginLeft:"25px", marginRight:"25px"}}>상품검색</p>
                <input type="text" className={ModuleStyle.inputBox} placeholder="상품명을 입력하세요" onChange={savename}></input>
            </div>
            <button className={ModuleStyle.whiteSmallButton} onClick={() => {headquaurters()}}>검색</button>
            
        </div>
    </div>

}

export default Search