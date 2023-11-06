import config from '../config/config';

export const fetchStatusData = ((callback) => {
    fetchData(config.baseApiUrl + '/api/v1/status','GET', callback, null);
});

export const fetchEmailStatus = ((callback) => {
    fetchData(config.baseApiUrl + '/api/v1/email-status','GET', callback, null);
});

export const triggerAppHealthCheck = ((appCode, callback) => {
    fetchData(config.baseApiUrl + '/api/v1/app/' + appCode + '/trigger-check', 'POST', callback, {});
});

export const toggleAppCodeEmailNotification = ((appCode, payload, callback) => {
    fetchData(config.baseApiUrl + '/api/v1/app/' + appCode + '/toggle-email-notification', 'PUT', callback, payload);
});

export const fetchAppCodeExceptionDesc = ((appCode, callback) => {
    fetchData(config.baseApiUrl + '/api/v1/app/' + appCode + '/exception-description', 'GET', callback, null);
});

export const fetchAppCodeEmailStatus = ((appCode, callback) => {
    fetchData(config.baseApiUrl + '/api/v1/app/' + appCode + '/email-notification', 'GET', callback, null);
});

export const fetchAppCodeStatusDTO = ((appCode, callback) => {
    fetchData(config.baseApiUrl + '/api/v1/app/' + appCode + '/status', 'GET', callback, null);
});

export const toggleAppCodeActiveState = ((appCode, payload, callback) => {
    fetchData(config.baseApiUrl + '/api/v1/app/' + appCode + '/toggle-app-code-state', 'PUT', callback, payload);
});

//
const fetchData = ((url, methodType, callback, payload) => {
    if (payload === null) {
        fetch(url, { method: methodType })
            .then(response => response.json())
            .then(respData => {
                callback(respData)
            });
    } else {
        fetch(url, {
            method: methodType,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
            .then(response => response.json())
            .then(respData => {
                callback(respData)
            });
    }
});
