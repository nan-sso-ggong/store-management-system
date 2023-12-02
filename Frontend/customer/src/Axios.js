import axios from 'axios';

const api = axios.create({
    baseURL: 'http://13.125.112.60:8080/api/v1', // API의 기본 URL 설정
    headers: {
        'Content-Type': 'application/json', // 요청의 Content-Type 설정
        Authorization: `Bearer ${localStorage.getItem('access_token')}`
    },
});

const reissueAccessToken = async () => {
    try {
        const refreshToken = localStorage.getItem('refresh_token');
        const response = await axios.post('/auth/customers/reissue', null, {
            headers: {
                'Authorization': `Bearer ${refreshToken}`
            }
        });

        if (response.data.success) {
            const { access_token } = response.data.data.access_token;
            localStorage.setItem('access_token', access_token);
            // 토큰 재발급 후, axios의 기본 headers를 재설정
            api.defaults.headers.common['Authorization'] = `Bearer ${access_token}`;
        } else {
            console.error(response.data.error);
        }
    } catch (error) {
        console.error(error);
    }
};

api.interceptors.response.use(
    response => response,
    async error => {
        const originalRequest = error.config;
        if (error.response.data.error.code === "433" && !originalRequest._retry) {
            originalRequest._retry = true;
            await reissueAccessToken();
            return api(originalRequest);
        }
        return Promise.reject(error);
    }
);

export default api;