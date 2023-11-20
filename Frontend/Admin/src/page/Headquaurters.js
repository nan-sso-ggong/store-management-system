import React from "react";
import { Link } from "react-router-dom";
import ModuleStyle from "../ModuleStyle.module.css"
import Frame from "../component/Frame";
import Search from "../component/headquaurters/Search";
import Inventory from "../component/headquaurters/Inventory";

function Headquaurters() {

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
  
  export default Headquaurters;