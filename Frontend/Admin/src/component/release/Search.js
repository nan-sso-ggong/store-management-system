import React, { useState, useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import { IoMdArrowDropdown } from "react-icons/io";

import ModuleStyle from "../../ModuleStyle.module.css"

function Search(){

    const [category, setcategory] = useState("카테고리를 선택하세요")
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

    return <div style={boxstyle}>
        {(open) && <div style={out} onClick={() => setopen(false)} ></div>}
        <div style={{display:"flex"}}>
            <p style={{marginTop:"15px", marginLeft:"25px", fontSize:"30px"}}>재고 검색</p>
        </div>
        <div style={{display:"flex", height:"60px"}}>
            <div style={{marginLeft:"0px", marginRight:"25px", marginTop:"-25px", display:"flex"}}>
                <p style={{fontSize:"25px", marginLeft:"25px", marginRight:"25px"}}>상품검색</p>
                <input type="text" className={ModuleStyle.inputBox} placeholder="상품명을 입력하세요" onChange={savename}></input>
                <p style={{fontSize:"25px", marginLeft:"25px", marginRight:"25px"}}>카테고리</p>
                <div>
                    <div style={{marginTop:"20px"}} className={ModuleStyle.inputBox} onClick={() => setopen(true)}>{category}</div>
                    {(open) && <div style={{height:"250px", overflow:"auto"}}>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("category1")}}>category1</div>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("category2")}}>category2</div>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("category3")}}>category3</div>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("category4")}}>category4</div>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("category5")}}>category5</div>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("category6")}}>category6</div>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("category7")}}>category7</div>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("category8")}}>category8</div>
                    </div>}
                </div>
                <p style={{fontSize:"25px", marginLeft:"25px", marginRight:"25px"}}>공급처</p>
                <input type="text" className={ModuleStyle.inputBox} placeholder="공급처명을 입력하세요" onChange={savename}></input>
            </div>

        </div>
        <div style={{display:"flex"}}>
            <button style={{marginLeft:"1350px"}} className={ModuleStyle.whiteSmallButton} onClick={() => {
                    
                }}>검색</button>
        </div>
    </div>

}

export default Search