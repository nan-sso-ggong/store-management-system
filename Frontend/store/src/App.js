import React from "react";
import "./App.css";
import Topbar from "./component/Topbar";
import Sidebar from "./component/Sidebar";
import Order from "./Pages/Order";
import { BrowserRouter as Router, Routes, Route, Navigate,useLocation} from 'react-router-dom';
import {RecoilRoot} from 'recoil';
import OrderApply from "./Pages/OrderApply";
import Loginpage from "./Pages/Loginpageheadquarters";
import LoginpageM from "./Pages/Loginpagemanager";
import AccountCreate from "./Pages/AccountCreate";


function App() {
    return (
        <RecoilRoot>
            <Router>
                <AppContent />
            </Router>
        </RecoilRoot>
    );
}
function AppContent() {
    const location = useLocation();
    const isLoginPage1 = location.pathname === "/login";
    const isLoginPage2 = location.pathname === "/";
    const isLoginPage3 = location.pathname === "/회원가입";
    return (
        <>
            {(!isLoginPage1 && !isLoginPage2 && !isLoginPage3)&& (
                <Topbar/>
            )}
            <Routes>
                <Route path="/" element={<Loginpage/>} />
                <Route path="/login" element={<LoginpageM/>} />
                <Route path="/회원가입" element={<AccountCreate/>} />
                <Route
                    path="*"
                    element={
                        <div className="container">
                            <Sidebar />
                            <Routes>
                                <Route path='/store/order' element={<Order/>}/>
                                <Route path='/store/order/apply' element={<OrderApply/>}/>
                            </Routes>
                        </div>
                    }
                />
            </Routes>
        </>
    );
}

export default App;
