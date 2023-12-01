import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <App />

    // React.StrictMode 모드는 요청 두 번 보내서 별로임 
//   <React.StrictMode>
//     <App />
//   </React.StrictMode>
);
