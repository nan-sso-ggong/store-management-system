import React, {useState} from "react";
import { Link } from "react-router-dom";

import { BiLogOut } from "react-icons/bi";
import { TbBuildingWarehouse } from "react-icons/tb";
import { LiaBoxSolid } from "react-icons/lia";
import { MdAddShoppingCart } from "react-icons/md";

function Sidebar(props){

    const boxstyle = {
        width:"360px",
        height:"1080px",
        border:"1px solid #F0F1F3",
        backgroundColor : "white",
        //display : d,
        //textAlign:"center",
        //overflow:"auto",
        //margin : "auto",
        //fontSize: f,
    }

    return <div style={boxstyle}>
        <div style={{marginTop:"25px", marginBottom:"80px", marginLeft:"25px", fontSize:"95px", color:"#2B7175"}}>CS25</div>
        <div>
            <div style={{display:"flex"}}>
                <TbBuildingWarehouse size="50" color="#5D6679" style={{marginTop:"-15px", marginLeft:"40px"}}/>
                <div style={{marginBottom:"50px", marginLeft:"20px", fontSize:"25px", color:"#5D6679"}}>재고 관리</div>
            </div>
            <div style={{display:"flex"}}>
                <MdAddShoppingCart size="40" color="#5D6679" style={{marginTop:"-10px", marginLeft:"40px"}}/>
                <div style={{marginBottom:"50px", marginLeft:"25px", fontSize:"25px", color:"#5D6679"}}>입고 관리</div>
            </div>
            <div style={{display:"flex"}}>
                <LiaBoxSolid size="50" color="#5D6679" style={{marginTop:"-15px", marginLeft:"35px"}}/>
                <div style={{marginBottom:"50px", marginLeft:"20px", fontSize:"25px", color:"#5D6679"}}>출고 관리</div>
            </div>
            
        </div>
        
        <div style={{marginTop:"550px", display:"flex"}} >
            <BiLogOut size="40" color="#5D6679" style={{marginTop:"-5px", marginLeft:"40px"}}/>
            <div style={{marginLeft:"20px", fontSize:"25px", color:"#5D6679"}}> logout</div>
        </div>
    </div>

}

export default Sidebar