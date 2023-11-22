import React from "react";
import './App.css';
import Topbar from "./Components/Topbar";
import Sidebar from "./Components/Sidebar";
import Login from "./Pages/Login";
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
          <Topbar />
          <Routes>
            <Route path="/" element={<Navigate to="/customer/login" />} />
            <Route path="/customer/login" element={<Login />} />
            <Route path='*' element={
              <div className="container">
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
