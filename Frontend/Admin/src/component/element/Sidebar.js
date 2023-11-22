import React, {useState, useEffect} from "react";
import { useLocation } from 'react-router-dom';
import { Link } from "react-router-dom";

import { BiLogOut } from "react-icons/bi";
import { TbBuildingWarehouse } from "react-icons/tb";
import { LiaBoxSolid } from "react-icons/lia";
import { MdAddShoppingCart } from "react-icons/md";

function Sidebar(props){

    const [where, setwhere] = useState(["#1570EF", "#5D6679", "#5D6679"]);

    const location = useLocation();

    useEffect(() => {
        if (location.pathname == "/admin/headquaurters"){
            setwhere(["#1570EF", "#5D6679", "#5D6679"]);
        }
        if (location.pathname == "/admin/release"){
            setwhere(["#5D6679", "#1570EF", "#5D6679"]);
        }
        if (location.pathname == "/admin/warehousing"){
            setwhere(["#5D6679", "#5D6679", "#1570EF"]);
        }
      }, [ location ])
    

    const boxstyle = {
        width:"360px",
        height:"1080px",
        border:"1px solid #F0F1F3",
        backgroundColor : "white",
    }

    return <div style={boxstyle} >

        <Link to="/admin/headquaurters" style={{ textDecoration: "none" }}>
            <div style={{marginTop:"25px", marginBottom:"80px", marginLeft:"25px", fontSize:"95px", color:"#2B7175"}}>CS25</div>
        </Link>
        <div>
            <Link to="/admin/headquaurters" style={{ textDecoration: "none" }}>
                <div style={{display:"flex"}}>                    
                    <TbBuildingWarehouse size="50" color={where[0]} style={{marginTop:"-15px", marginLeft:"40px"}}/>
                    <div style={{marginBottom:"50px", marginLeft:"20px", fontSize:"25px", color:where[0]}}>재고 관리</div>
                </div>
            </Link>
            <Link to="/admin/release" style={{ textDecoration: "none" }}>
                <div style={{display:"flex"}}>
                    <MdAddShoppingCart size="40" color={where[1]} style={{marginTop:"-10px", marginLeft:"40px"}}/>
                    <div style={{marginBottom:"50px", marginLeft:"25px", fontSize:"25px", color:where[1]}}>입고 관리</div>
                </div>
            </Link>
            <Link to="/admin/warehousing" style={{ textDecoration: "none" }}>
                <div style={{display:"flex"}}>
                    <LiaBoxSolid size="50" color={where[2]} style={{marginTop:"-15px", marginLeft:"35px"}}/>
                    <div style={{marginBottom:"50px", marginLeft:"20px", fontSize:"25px", color:where[2]}}>출고 관리</div>
                </div>
            </Link>
        </div>
        
        <div style={{marginTop:"550px", display:"flex"}} >
            <BiLogOut size="40" color="#5D6679" style={{marginTop:"-5px", marginLeft:"40px"}}/>
            <div style={{marginLeft:"20px", fontSize:"25px", color:"#5D6679"}}> logout</div>
        </div>
    </div>

}

export default Sidebar