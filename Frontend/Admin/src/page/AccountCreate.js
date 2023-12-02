import React from "react";
import { Link } from "react-router-dom";
import ModuleStyle from "../ModuleStyle.module.css"
import CreateAccount from "../component/login/CreateAccount";

function AccountCreate() {

    return <div className={ModuleStyle.loginpagestyle}>
        <CreateAccount />
    </div>
  }
  
  export default AccountCreate;