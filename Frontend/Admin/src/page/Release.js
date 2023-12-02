import React, { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import ModuleStyle from "../ModuleStyle.module.css"
import Frame from "../component/Frame";
import Search from "../component/release/Search";
import Inventory from "../component/release/Inventory";


function Release() {

    const { search } = useSelector((state) => state)
    const navigater = useNavigate()

    useEffect(() => {
        if (search.access_token == ""){
            alert("로그인이 필요합니다!")
            navigater("/")
        }
    }, [])
    
    return <div className={ModuleStyle.pagestyle}>
                
        <Frame item={<div>
            <div style={{position: "relative", zIndex:2}}> 
                <Search/>
            </div>
            <div style={{position: "relative", zIndex:1}}>   
                <Inventory/>
            </div>
        </div>}/>
    </div>
  }
  
  export default Release;