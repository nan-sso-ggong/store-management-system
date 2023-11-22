import axios from 'axios';

const api = axios.create({
    baseURL: 'https://18821b90-7c6b-4217-b68e-e5775ac40a41.mock.pstmn.io/api/v1', // API의 기본 URL 설정
    headers: {
        'Content-Type': 'application/json', // 요청의 Content-Type 설정
        //Authorization: `Bearer ${localStorage.getItem('accessToken')}`
    },
});

export default api;