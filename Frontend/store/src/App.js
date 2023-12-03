import React from "react";
import "./App.css";
import Topbar from "./component/Topbar";
import Sidebar from "./component/Sidebar";
import { BrowserRouter as Router, Routes, Route, Navigate,useLocation} from 'react-router-dom';
import {RecoilRoot} from 'recoil';
import AccountCreate from "./Pages/AccountCreate";
import Home from "./Pages/Home";
import Order from "./Pages/Order";
import Loginpage from "./Pages/Loginpageheadquarters";
import LoginpageM from "./Pages/Loginpagemanager";
import OrderApply from "./Pages/OrderApply";
import InventoryManagement from "./Pages/InventoryManagement";

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
                                <Route path='/store/inventory' element={<InventoryManagement/>}/>
                                <Route path='/store/order' element={<Order/>}/>
                                <Route path='/store/order/apply' element={<OrderApply/>}/>
                                <Route path="/store/selectstore" element={<Home/>} />
                            </Routes>
                        </div>
                    }
                />
            </Routes>
        </>
    );
}

export default App;
