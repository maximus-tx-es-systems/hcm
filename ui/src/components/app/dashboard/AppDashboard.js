import './AppDashboard.css';
import AppNode from '../node/AppNode';

const AppDashboard = ((props) => {

    return (<div className="bodyWrapper ">
			{	
            props.state.appData.map((appConfig) => {
                return (<AppNode key={appConfig.appCode + (100*Math.random())} 
                state={{ 'appConfig': appConfig }} />);
                })
        }</div>);
    });

export default AppDashboard;
