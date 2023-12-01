import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes} from "react-router-dom";

import Headquaurters from './page/Headquaurters';
import Release from './page/Release';
import Warehousing from './page/Warehousing';
import Detail from './component/headquaurters/Detail';
import Storemanage from './page/Storemanage';
import Employeemanage from './page/Employeemanage';

function App() {
  return (
    <div>
        <BrowserRouter>
              <Routes>
                  <Route path="/admin/headquaurters" element={<Headquaurters/>} />
                  <Route path="/admin/release" element={<Release/>} />
                  <Route path="/admin/warehousing" element={<Warehousing/>}/>
                  <Route path="/admin/detail/:id" element={<Detail/>}/>
                  <Route path="/admin/storemanage" element={<Storemanage/>}/>
                  <Route path="/admin/employeemanage" element={<Employeemanage/>}/>
              </Routes>
          </BrowserRouter>
    </div>
  );
}

export default App;