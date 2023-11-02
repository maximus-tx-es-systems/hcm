import './AppBoard.css';
import { Link, useLocation } from 'react-router-dom';
import NodesBoard from '../../node/board/NodesBoard';

const AppBoard = (() => {

   let  appConfig  = null;
   const init = ((location)=>{ appConfig = location.state.appConfig; });
   
   useLocation().state.appConfig;

    return (
            <div>
            <Link className="boardBackLink" to="/">Home</Link>{ init(useLocation()) }
            <center><h1 className="appCodeTitleStyle">{appConfig.displayName}</h1></center><hr/>
            <div>
            { /*   VIP Nodes  */ }
            <NodesBoard className="appBoardStyle" 
            state={{ 'listTitle':'VIP instances List', 'nodesList': appConfig.vipAppCodes, 
            'allStatusMap': appConfig.consolidatedAppCodeStatusMap, 'appConfig': appConfig  }} />
            { /*   HAP Nodes  */ }
            <NodesBoard className="appBoardStyle" 
            state={{ 'listTitle':'HA Proxy instances List', 'nodesList': appConfig.haproxyAppCodes, 
            'allStatusMap': appConfig.consolidatedAppCodeStatusMap, 'appConfig': appConfig  }} />

            { /*   Direct Nodes  */ }
            <NodesBoard className="appBoardStyle" 
            state={{ 'listTitle':'Direct instances List', 'nodesList': appConfig.directAppCodes, 
            'allStatusMap': appConfig.consolidatedAppCodeStatusMap, 'appConfig': appConfig  }} />
            </div>
            </div>);
    });

export default AppBoard;
