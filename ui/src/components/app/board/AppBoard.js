import './AppBoard.css';
import { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import NodesBoard from '../../node/board/NodesBoard';

const AppBoard = ((props) => {

    const [appData, setAppData] = useState(props.state.appData);
    const [emailStatus, setEmailStatus] = useState(props.state.emailStatus);
    
    let appConfig = null;
    //let statusDTO = null;
    const refreshAppConfig = ((location) => {
        appConfig = location.state.appConfig;
    //    statusDTO = location.state.statusDTO;
    //    console.log('AppBoard. Receieved appConfig = ', JSON.stringify(appConfig));
    //    console.log('AppBoard. Receieved statusDTO = ', JSON.stringify(statusDTO));
    });
   
    return (
            <div>
            <Link className="boardBackLink" to="/">Home</Link>{refreshAppConfig(useLocation()) }
            <center><h1 className="appCodeTitleStyle">{appConfig.displayName}</h1></center><hr/>
            <div>
            { /*   VIP Nodes  */ }
            <NodesBoard className="appBoardStyle" 
                    state={{
                        'listTitle': 'VIP instances List', 'nodesList': appConfig.vipNodeCodes, 
                'consolidatedNodeCodeStatusMap': appConfig.consolidatedNodeCodeStatusMap, 'appConfig': appConfig  }} />
            { /*   HAP Nodes  */ }
            <NodesBoard className="appBoardStyle" 
                    state={{
                        'listTitle': 'HA Proxy instances List', 'nodesList': appConfig.haproxyNodeCodes, 
                'consolidatedNodeCodeStatusMap': appConfig.consolidatedNodeCodeStatusMap, 'appConfig': appConfig  }} />

            { /*   Direct Nodes  */ }
            <NodesBoard className="appBoardStyle" 
                    state={{
                        'listTitle': 'Direct instances List', 'nodesList': appConfig.directNodeCodes, 
                'consolidatedNodeCodeStatusMap': appConfig.consolidatedNodeCodeStatusMap, 'appConfig': appConfig  }} />
            </div>
            </div>);
    });

export default AppBoard;
