import './Node.css';
import { useState } from 'react';
import { Link } from 'react-router-dom';

const Node = ((props)=>{

    const [nodeCode, setNodeCode] = useState(props.state.nodeCode);
    const [appConfig, setAppConfig] = useState(props.state.appConfig);
    const [statusDTO, setStatusDTO] = useState(props.state.statusDTO);
    const [active, setActive] = useState(props.state.statusDTO.active);
    
    return (<Link className="nodeLinkStyle" to="status"
        state={{ appCode: statusDTO.appCode ,nodeCode: nodeCode, statusDTO: statusDTO, appConfig: appConfig }} >
        <div className={nodeCode === "BLANK" ? "bodyWrapperGridBox blankBox" : active === false ?
            "bodyWrapperGridBox deactivatedAppNode" : statusDTO.warningOn === "Y" ? "bodyWrapperGridBox warnflash" :
                (statusDTO.status === "OK" || statusDTO.status=="N/A") ?  "bodyWrapperGridBox" :
                    "bodyWrapperGridBox flash"}>{nodeCode} </div></Link>);
});


export default Node;
