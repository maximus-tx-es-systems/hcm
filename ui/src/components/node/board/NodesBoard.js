import './NodesBoard.css';
import { useState } from 'react';

import Node from '../Node';

const NodesBoard = ((props) => {

   const [listTitle, setListTitle] = useState(props.state.listTitle);
   const [nodesList, setNodesList] = useState(props.state.nodesList);
   const [allStatusMap, setAllStatusMap] = useState(props.state.allStatusMap);
   const [appConfig, setAppConfig] = useState(props.state.appConfig);

    return (<div>
        {
            nodesList!=null?
            <div>
                <h5 className="appBoardListTitleStyle"> { listTitle } </h5>
                <div className="nodesBoardWrapper">{
                 nodesList.map((node)=>{
                    return (<Node className="nodesBoardStyle" key={node} state={{ appCode: node, statusDTO: allStatusMap[node], appConfig: appConfig }} />);
                })}</div><hr/>
            </div>:''
        }
        </div>);
    });

export default NodesBoard;