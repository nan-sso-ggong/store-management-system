import React from "react";
import { Link } from "react-router-dom";
import ModuleStyle from "../ModuleStyle.module.css"
import Adminlogin from "../component/login/Adminlogin";

function Loginpage() {

    return <div className={ModuleStyle.loginpagestyle}>
        <Adminlogin/>
        
    </div>
  }
  
  export default Loginpage;