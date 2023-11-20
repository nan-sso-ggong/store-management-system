import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes} from "react-router-dom";


import Main from './page/Main';

import Headquaurters from './page/Headquaurters';
import Release from './page/Release';
import Warehousing from './page/Warehousing';

function App() {
  return (
    <div>
        <BrowserRouter>
              <Routes>
                  <Route path="/" element={<Main/>}/>
                  <Route path="/headquaurters" element={<Headquaurters/>} />
                  <Route path="/release" element={<Release/>} />
                  <Route path="/warehousing" element={<Warehousing/>}/>
              </Routes>
          </BrowserRouter>
    </div>
  );
}

export default App;