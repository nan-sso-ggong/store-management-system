import axios from 'Frontend/store/src/Axios';

const api = axios.create({
    baseURL: 'http://127.0.0.1:8000/quiz//api/v1', // API의 기본 URL 설정
    headers: {
        'Content-Type': 'application/json', // 요청의 Content-Type 설정
        //Authorization: `Bearer ${localStorage.getItem('accessToken')}`
    },
});

export default api;