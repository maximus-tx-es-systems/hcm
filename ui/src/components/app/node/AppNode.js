import './AppNode.css';
import { useState } from 'react';
import { Link } from 'react-router-dom';

const AppNode = ((props) => {

    const [appConfig, setAppConfig] = useState(props.state.appConfig); 
    const [active, setActive] = useState(props.state.appConfig.active);

	const buildAppNode = (()=>{
	   if(appConfig.appCode === 'BLANK'){ return (<div className="blankBox"> </div>); }
	   return <Link key={appConfig.appCode + (1000 * Math.random())} className='appNodeLink'
                    to={ '/app/' + appConfig.appCode} state={{ 'appConfig': appConfig }}> 
            <div className={appConfig.appCode === 'BLANK' 
               ? "bodyWrapperGridBox blankBox" : (active === false) ? " bodyWrapperGridBox bodyWrapperGridBoxDisabled " :
                   (appConfig.errSummary === 'OK' || appConfig.errSummary === 'N/A')
            ? appConfig.warningOn === 'Y' 
            ? 'bodyWrapperGridBox warnflash ' : 'bodyWrapperGridBox' : 'bodyWrapperGridBox flash'}>
            {appConfig.appCode === 'BLANK' ? ' ' : appConfig.displayName}
            </div></Link>
    });

    return (<div>{ buildAppNode() }</div>);
    });

export default AppNode;
