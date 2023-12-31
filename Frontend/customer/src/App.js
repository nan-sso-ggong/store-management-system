import React from "react";
import "./App.css";
import Topbar from "./Components/Topbar";
import Sidebar from "./Components/Sidebar";
import Login from "./Pages/Login/Login";
import KakaoAfterLogin from "./Pages/Login/KakaoAfterLogin";
import NaverAfterLogin from "./Pages/Login/NaverAfterLogin";
import GoogleAfterLogin from "./Pages/Login/GoogleAfterLogin";
import SelectStore from "./Pages/SelectStore";
import SelectItems from "./Pages/SelectItems";
import ShoppingCart from "./Pages/ShoppingCart";
import CheckPayment from "./Pages/CheckPayment";
import PaymentList from "./Pages/PaymentList";
import PaymentListDetail from "./Pages/PaymentListDetail";
import { BrowserRouter as Router, Routes, Route, Navigate,useLocation} from 'react-router-dom';
import {RecoilRoot} from 'recoil';


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
  const isLoginPage = location.pathname === "/auth";

  return (
      <>
        {!isLoginPage && (
              <Topbar />
        )}
        <Routes>
          <Route path="/" element={<Navigate to="/auth" />} />
          <Route path="/auth" element={<Login />} />
          <Route
              path="*"
              element={
                <div className="container">
                  <Sidebar />
                  <Routes>
                    <Route path='/customer/selectstore' element={<SelectStore/>}/>
                    <Route path='/customer/:storeId/selectitems' element={<SelectItems/>}/>
                    <Route path='/customer/shoppingcart' element={<ShoppingCart/>}/>
                    <Route path='/customer/checkpayment' element={<CheckPayment/>}/>
                    <Route path='/customer/paymentlist' element={<PaymentList/>}/>
                    <Route path='/customer/paymentlist/:id' element={<PaymentListDetail/>}/>
                    <Route path="/api/v1/auth/customers/kakao" element={<KakaoAfterLogin/>} />
                    <Route path="/api/v1/auth/customers/naver" element={<NaverAfterLogin/>} />
                    <Route path="/api/v1/auth/customers/google" element={<GoogleAfterLogin/>} />
                  </Routes>
                </div>
              }
          />
        </Routes>
      </>
  );
}

export default App;
