import './Node.css';
import { useState } from 'react';
import { Link } from 'react-router-dom';

const Node = ((props)=>{

    const [appCode, setAppCode] = useState(props.state.appCode);
    const [statusDTO, setStatusDTO] = useState(props.state.statusDTO);
  
    return (<Link className="nodeLinkStyle" to="status" state={{ appCode: appCode, statusDTO: statusDTO }} >
    <div className={appCode === "BLANK" ? "bodyWrapperGridBox blankBox" : statusDTO.status === "OK" ?
    statusDTO.warningOn === "Y" ? "bodyWrapperGridBox warnflash" : "bodyWrapperGridBox" : "bodyWrapperGridBox flash"}>{ appCode } </div></Link>);
});


export default Node;
