import axios from 'axios';

const api = axios.create({
    baseURL: 'http://13.125.112.60:8080/api/v1',
    headers: {
        'Content-Type': 'application/json',
    },
});
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('access_token');
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
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