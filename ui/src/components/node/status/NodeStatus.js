import './NodeStatus.css';
import { useState } from 'react';
import {
    triggerAppHealthCheck, toggleAppCodeEmailNotification,
    fetchAppCodeExceptionDesc, fetchAppCodeStatusDTO, toggleAppCodeActiveState
} from '../../../services/hcm-services';
import { Link, useLocation, useNavigate } from 'react-router-dom';

const NodeStatus = ((props) => {

    const [detailedErroDesc, setDetailedErroDesc] = useState('');
    const [statusDTO, setStatusDTO] = useState(useLocation().state.statusDTO);
    const navigate = useNavigate();
    const [appCode, setAppCode] = useState(useLocation().state.appCode);
    const [nodeCode, setNodeCode] = useState(useLocation().state.nodeCode);
    const [appConfig, setAppConfig] = useState(useLocation().state.appConfig);
    const [displayName, setDisplayName] = useState(useLocation().state.displayName);

    const goBack = (() => {
        navigate(-1);
    });

    const action1Handler1 = ((e) => {
        triggerAppHealthCheck(nodeCode, (respData) => {
   //         console.log('triggerAppHealthCheck respData ==> ', JSON.stringify(respData));
        });
    });

    const action1Handler2 = ((e) => {
   //     console.log('firing up  toggleAppCodeEmailNotification statusDTO.notifyByEmail= ', statusDTO.notifyByEmail);
        toggleAppCodeEmailNotification(nodeCode, {
            key: 'notifyByEmail',
            value: (statusDTO.notifyByEmail === true) ? false : true
        }, (respData) => {
    //        console.log('toggleAppCodeEmailNotification callback method inside...', respData.value);
            setStatusDTO(respData);
        });
    });

    const action1Handler3 = ((e) => {
        fetchAppCodeExceptionDesc(nodeCode, (respData) => {
   //         console.log('fetchAppCodeExceptionDesc callback method inside...', respData.value);
            setDetailedErroDesc(respData.value)
        });
    });

    const action1Handler4 = ((e) => {
        toggleAppCodeActiveState(nodeCode, {
            key: 'active',
            value: (statusDTO.active === true) ? false : true
        }, (respData) => {
 //           console.log('toggleAppCodeActiveState callback method inside...', JSON.stringify(respData));
            setStatusDTO(respData);
        });
    });

    const displayKeyValue = ((key, displayName) => {
        return (<tr key={key} className="nodeStatusOutput">
            <td className="nodestatusKeyStyle">{displayName}</td>
            <td className={statusDTO.status === "OK" ? "nodestatusValueStyle" :
                statusDTO.warningOn === "Y" ?
                    "nodestatusValueWarningStyle warnflash" : "nodestatusValueErrorStyle flash"}>
                {key === 'active' ? statusDTO.active === true ?
                    'Active' : 'Deactived' : key === 'notifyByEmail' ?
                    statusDTO.notifyByEmail === true?'Enabled':'Disabled' : String(statusDTO[key])}</td></tr>);
    });

    /*
    const fetchAppCodeStatus = (() => {
        console.log('inside fetchAppCodeStatus...');
        fetchAppCodeStatusDTO(appCode, (respData) => {
            console.log('fetchAppCodeStatus=> inside callback', JSON.stringify(respData));
            setStatusDTO(respData);
        });
    });

    // <div onClick={goBack} className="boardBackLink">Back</div>

    */
    return (<div> <Link className="boardBackLink" to={'/'} state={{ appConfig: appConfig }}>Home</Link>
        <Link to={"/app/" + statusDTO.appCode} state={{ appConfig: appConfig, statusDTO: statusDTO  }} className="boardBackLink">Back</Link>
            <center><h1 className="appCodeTitleStyle">{displayName}</h1></center>
        <center> 
            <h4 className={statusDTO.status==="OK"?"nodeAppCodeStyle":
            statusDTO.warningOn==="Y"?"nodeAppCodeWarningStyle warnflash":"nodeAppCodeErrorStyle flash"}>{ appCode }</h4>
            <table className="nodeStatusTable">
                <tbody>
                    {displayKeyValue('nodeCode', 'App Node Code')}
                    <tr key={statusDTO.nodeCode} className="nodeStatusOutput">
                    <td className="nodestatusKeyStyle">App Code URI</td>
                    <td className={statusDTO.status==="OK"?"nodestatusValueStyle":
                    statusDTO.warningOn==="Y"?"nodestatusValueWarningStyle": "nodestatusValueErrorStyle flash"}>
                            <a target="blank" href={statusDTO.nodeCodeUri}>{statusDTO.nodeCodeUri}</a></td></tr>
                {displayKeyValue('statusCode', 'Health Check Status Code')}
                {displayKeyValue('status', 'Health Check Status')}
                {displayKeyValue('warningOn', 'Health Check Warning Status')}
                {displayKeyValue('error', 'Check Error Status')}
                {displayKeyValue('notifyByEmail', 'Node Level Email Notification')}
                {displayKeyValue('active', 'Node Active state')}
                </tbody>
            </table>
            <div> &nbsp;
            </div>
           
        </center>
        <br /><br /><br />
        <div className="nodesStatusActionWrapper">
            <div><button className="nodeStatusActionBtn" name="action1" onClick={action1Handler1} type="button">Trigger Validation</button></div>
            <div><button className={statusDTO.notifyByEmail === true ? "nodeStatusActionBtn nodeStatusActionBtnfalse" : "nodeStatusActionBtn"} name="action2"
                onClick={action1Handler2} type="button">{statusDTO.notifyByEmail === true ? "Disable" : "Enable"} Email Alerts</button></div>
            <div><button className="nodeStatusActionBtn" name="action3" onClick={action1Handler3} type="button">Show Error/Exception Logs</button></div>
            <div><button className={statusDTO.active === true ? "nodeStatusActionBtn nodeStatusActionBtnfalse" : "nodeStatusActionBtn "}
                name="action4" onClick={action1Handler4} type="button">{statusDTO.active === true ? "DeActivate " : "Activate "} AppCode</button></div>
        </div>
        <textarea className={detailedErroDesc === '' ? "detailedErrorDescHiddenStyle" : "detailedErrorDescStyle"} readOnly value={detailedErroDesc} />
    </div>);
    });

export default NodeStatus;