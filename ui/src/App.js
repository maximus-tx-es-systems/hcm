import './App.css';
import { useState } from 'react';
import config from './config/config';
import Login from './components/login/Login';
import Header  from './components/header/Header';
import AppBoard from './components/app/board/AppBoard';
import NodeStatus from './components/node/status/NodeStatus';
import AppDashboard from './components/app/dashboard/AppDashboard';
import { BrowserRouter , Routes, Route } from 'react-router-dom';
import { fetchStatusData, fetchEmailStatus } from './services/hcm-services'

function App() {

    const [data, setData] = useState([]);
    const [notifyByEmail, setNotifyByEmail] = useState('');

    window.onload = () => {
        getStatusData();
        getEmailStatus();
        setInterval(() => {
             getStatusData();
             getEmailStatus();
        }, config.frequency);
        
     };

    const getStatusData = () => {
        fetchStatusData((respData) => {
            setData(respData)
        });
    };

    const getEmailStatus = () => {
        fetchEmailStatus((emailStatusdata)=>{
            if(notifyByEmail!==emailStatusdata.value){
                setNotifyByEmail(emailStatusdata.value);
            }
        });
    };

  return (<div className="App">
    <Header state={{"appData": data, "emailStatus": notifyByEmail}}/>
    <BrowserRouter basename= {config.basename}>
        <Routes>
            <Route index element={ <AppDashboard state={{"appData": data, "emailStatus": notifyByEmail}}/> } />
            <Route path="/" element={<AppDashboard state={{ "appData": data, "emailStatus": notifyByEmail }} /> } />
            <Route path="/app/:appCode" element={ <AppBoard state={{"appData": data, "emailStatus": notifyByEmail}}/> } />
            <Route path="/app/:appCode/status" element={ <NodeStatus state={{"appData": data, "emailStatus": notifyByEmail}} /> } />
            <Route path="/dashboard" element = { <AppDashboard state={{"appData": data, "emailStatus": notifyByEmail}}/>}/>
            <Route path="*" element={ <AppDashboard state={{"appData": data, "emailStatus": notifyByEmail}} /> } />

        </Routes>
    </BrowserRouter>
    </div>
  );
}

export default App;