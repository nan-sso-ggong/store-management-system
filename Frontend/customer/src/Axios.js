import axios from 'axios';

const api = axios.create({
    baseURL: 'http://13.125.112.60:8080/api/v1', // API의 기본 URL 설정
    headers: {
        'Content-Type': 'application/json', // 요청의 Content-Type 설정
        Authorization: `Bearer ${localStorage.getItem('access_token')}`
    },
});

export default api;