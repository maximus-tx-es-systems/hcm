import './AppNode.css';
import { Link } from 'react-router-dom';

const AppNode = ((props) => {

	const buildAppNode = (()=>{
	   let appConfig = props.state.appConfig;
	   if(appConfig.appCode === 'BLANK'){ return (<div className="blankBox"> </div>); }
	   return <Link key={appConfig.appCode + (1000 * Math.random())} className='appNodeLink'
                    to={ '/app/' + appConfig.appCode} state={{ 'appConfig': appConfig }}> 
            <div className={appConfig.appCode === 'BLANK' 
            ? "bodyWrapperGridBox blankBox" : appConfig.errSummary === 'OK' 
            ? appConfig.warningOn === 'Y' 
            ? 'bodyWrapperGridBox warnflash ' : 'bodyWrapperGridBox' : 'bodyWrapperGridBox flash'}>
            {appConfig.appCode === 'BLANK' ? ' ' : appConfig.displayName}
            </div></Link>
    });

    return (<div>{buildAppNode()}</div>);
    });

export default AppNode;
