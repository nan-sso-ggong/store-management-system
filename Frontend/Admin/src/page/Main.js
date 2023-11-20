import React from "react";
import { Link } from "react-router-dom";
import ModuleStyle from "../ModuleStyle.module.css"
function Main() {

    const bstyle = {
        width:"200px",
        height:"100px",
        border:"1px solid black",
        textAlign:"center",
        padding:"auto",
        margin:"25px",
    }


    return <div>
        <div>

            <Link to="/modaltable" className={ModuleStyle.linkstyle}>
            <div style={bstyle}>modaltable</div>
            </Link>

            <Link to="/itempagetable" className={ModuleStyle.linkstyle}>
                <div style={bstyle}>itempagetable</div>
            </Link>

        </div>
    </div>
  }
  
  export default Main;