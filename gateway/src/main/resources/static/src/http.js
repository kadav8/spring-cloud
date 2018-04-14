import Vue from 'vue'
import axios from 'axios'
import config from './config.js'

const http = axios.create({
    baseURL: config.apiBaseUrl,
    timeout: 3000
})

http.interceptors.request.use(config => {
    console.log("request: " + config.baseURL + config.url)
    return config
}, error => {
    console.error("error during request: " + error)
    return Promise.reject(error)
})

http.interceptors.response.use(response => {
    console.log("response: " + response.request.responseURL)
    if (response && response.data && response.data.items && response.data.items instanceof Array) {
        response.data.items = response.data.items.filter(n => n)
    }
    return response
}, error => {
    console.error("error during response: " + error)
    return Promise.reject(error)
})

export default http