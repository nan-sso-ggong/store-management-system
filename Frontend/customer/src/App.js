import React from "react";
import './App.css';
import Topbar from "./Components/Topbar";
import Sidebar from "./Components/Sidebar";
import Login from "./Pages/Login/Login";
import KakaoCallBack from "./Pages/Login/KakaoCallBack";
import SelectStore from "./Pages/SelectStore";
import SelectItems from "./Pages/SelectItems";
import ShoppingCart from "./Pages/ShoppingCart";
import CheckPayment from "./Pages/CheckPayment";
import PaymentList from "./Pages/PaymentList";
import PaymentListDetail from "./Pages/PaymentListDetail";
import { BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import {RecoilRoot} from 'recoil';


function App() {
  return (
      <RecoilRoot>
        <Router>
          <Routes>
            <Route path="/" element={<Navigate to="/auth" />} />
            <Route path="/auth" element={<Login />} />
            <Route path="/auth/kakao/callback" element={<KakaoCallBack/>}/>
            <Route path='*' element={
              <div className="container">
                <Topbar />
                <Sidebar />
                <Routes>
                  <Route path='/customer/selectstore' element={<SelectStore/>}/>
                  <Route path='/customer/:storeId/selectitems' element={<SelectItems/>}/>
                  <Route path='/customer/shoppingcart' element={<ShoppingCart/>}/>
                  <Route path='/customer/checkpayment' element={<CheckPayment/>}/>
                  <Route path='/customer/paymentlist' element={<PaymentList/>}/>
                  <Route path='/customer/paymentlist:id' element={<PaymentListDetail/>}/>
                </Routes>
              </div>
            } />
          </Routes>
        </Router>
      </RecoilRoot>
  );
}

export default App;
