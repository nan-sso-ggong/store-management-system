import React from "react";
import "./App.css";
import Topbar from "./Components/Topbar";
import Sidebar from "./Components/Sidebar";
import Order from "./Pages/Order";
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

    return (
        <>
                <Topbar />
            <Routes>
                <Route
                    path="*"
                    element={
                        <div className="container">
                            <Sidebar />
                            <Routes>
                               <Route path='/store/order' element={<Order/>}/>
                            </Routes>
                        </div>
                    }
                />
            </Routes>
        </>
    );
}

export default App;
