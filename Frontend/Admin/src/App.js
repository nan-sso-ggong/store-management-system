import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes} from "react-router-dom";

import Headquaurters from './page/Headquaurters';
import Release from './page/Release';
import Warehousing from './page/Warehousing';
import Detail from './component/headquaurters/Detail';

function App() {
  return (
    <div>
        <BrowserRouter>
              <Routes>
                  <Route path="/admin/headquaurters" element={<Headquaurters/>} />
                  <Route path="/admin/release" element={<Release/>} />
                  <Route path="/admin/warehousing" element={<Warehousing/>}/>
                  <Route path="/admin/detail" element={<Detail/>}/>
              </Routes>
          </BrowserRouter>
    </div>
  );
}

export default App;