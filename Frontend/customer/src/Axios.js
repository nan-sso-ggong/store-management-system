import axios from 'axios';

const api = axios.create({
    baseURL: 'https://4616260c-fcd5-412c-b1de-e82190e93117.mock.pstmn.io/api/v1', // API의 기본 URL 설정
    headers: {
        'Content-Type': 'application/json', // 요청의 Content-Type 설정
        Authorization: `Bearer ${localStorage.getItem('access_token')}`
    },
});

export default api;