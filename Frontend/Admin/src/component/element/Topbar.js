import React, {useState} from "react";
import { Link } from "react-router-dom";

import { IoPersonCircleOutline } from "react-icons/io5";

function Topbar(){

    const [name , setname] = useState("users")

    const boxstyle = {
        width:"1555px",
        height:"120px",
        border:"1px solid #F0F1F3",
        backgroundColor:"white",
        display : "flex",
        textAlign:"center",
        //overflow:"auto",
        //margin : "auto",
        //fontSize: f,
        
    }

    return <div style={boxstyle}>
        <div style={{marginLeft:"1330px", marginTop:"40px", fontSize:"40px", color:"#5D6679"}}>{name}</div>
        <IoPersonCircleOutline size="80" color="#5D6679" style={{marginTop:"25px", marginLeft:"10px"}}/>
    </div>

}

export default Topbar