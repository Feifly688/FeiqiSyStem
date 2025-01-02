import axios from "axios";

/*创建一个axios请求*/
const request = axios.create({
    baseURL: 'http://localhost:8080/feiqi',
    timeout: 5000
})

/**
 * request拦截器
 * 可以自请求发送前对请求做一些处理
 * 比如统一加token，对请求参数统一加密
 */
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    const user = localStorage.getItem("user");
    if (user) {
        config.headers['token'] = JSON.parse(user).token;
    }

    // config.headers['token'] = user.token //设置请求头
    return config
}, error => {
    return Promise.reject(error)
});

/**
 * response拦截器
 * 可以在接口响应后统一处理结果
 */
request.interceptors.response.use(response => {
    let res = response.data;
    if (typeof res === 'string') {
        res = res ? JSON.parse(res) : res
    }
    return res;
}, error => {
    console.log('err' + error)
    return Promise.reject(error)
});

export default request