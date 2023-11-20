import React, {useState} from "react";
import { Link } from "react-router-dom";
import Sidebar from "./element/Sidebar";
import Topbar from "./element/Topbar";

import ModuleStyle from "../ModuleStyle.module.css"

function Frame(props){

    return <div style={{display:"flex"}}>
        
        <Sidebar/>
        <div>
            <Topbar/>
            {props.item}
        </div>

    </div>

}


export default Frame