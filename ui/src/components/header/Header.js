import './header.css';
import { useState } from 'react';
import config from '../../config/config';

const Header = ((props) => {

    const [dateTime, setDateTime] = useState(new Date());
    const [notifyByEmail, setNotifyByEmail] = useState(props.state.notifyByEmail);

    const formatDate = (date) => {
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var seconds = date.getSeconds();
        var ampm = hours >= 12 ? 'PM' : 'AM';
        hours = hours % 12;
        hours = hours ? hours : 12;
        hours = hours < 10 ? '0' + hours : hours;
        minutes = minutes < 10 ? '0' + minutes : minutes;
        seconds = seconds < 10 ? '0' + seconds : seconds;
        var strTime = hours + ':' + minutes + ':' + seconds + ' ' + ampm;
        return (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear() + "   " + strTime;
    };

    const toggleEmailNotification = () => {
        console.log("Toggle the email notification now..notifyEmailFlag = " + notifyByEmail);
        fetch(config.baseApiUrl + '/api/v1/email-status', {
            method: 'POST', headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                key: "notifyByEmail",
                value: notifyByEmail === "true" ? "false" : "true"
            })
        }).then(response => response.json()).then(emailStatusdata => {
            console.log("POSTing notifyByEmail => " + emailStatusdata.value);
            if(notifyByEmail!==emailStatusdata.value){
                setNotifyByEmail(emailStatusdata.value);
            }
        });
    };

    const triggerHealthCheck = () => {
        console.log("Trigger the health check process now..= " + new Date());
        fetch(config.baseApiUrl + '/api/v1/manual-check?notifyByEmail=' + notifyByEmail, {
            method: 'GET', headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(response => {
            console.log("Trigger StatusCode => " + response.statusCode);
            alert('Successfully triggered the application health check!!');
        });

    };

    return (<div>
        <center><span className="title blinker">Application Health Check Monitor Dashboard</span><br /><br />
            <div className='headerRowWrapper'>
                <div className='headerWrapperGridBox'>App Version: 0.0.3</div>
                <div className='headerWrapperGridBox'> </div>
                <div className='headerWrapperGridBox'>{formatDate(dateTime)}</div>
                <div className='headerWrapperGridBox'><button className={notifyByEmail === '' ? "notifyBtn notifyBtn" : notifyByEmail === "true" ? "notifyBtn notifyBtntrue" : "notifyBtn notifyBtnfalse"}
                    onClick={() => toggleEmailNotification()}>{notifyByEmail === "true" ? "Disable" : "Enable"} All Email Alerts</button></div>
                <div className='headerWrapperGridBox'>
                    <button className="triggerBtn" onClick={() => triggerHealthCheck()}>Trigger All Health Checks</button></div>
            </div><br /><hr /></center>
        </div>);
});


export default Header;