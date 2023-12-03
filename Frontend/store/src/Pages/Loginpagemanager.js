import React from "react";
import { Link } from "react-router-dom";
import ModuleStyle from "../ModuleStyle.module.css"
import Managerlogin from "../component/login/Managerlogin";
function LoginpageM() {

    return <div className={ModuleStyle.loginpagestyle}>
        <Managerlogin/>
    </div>
  }
  
  export default LoginpageM;