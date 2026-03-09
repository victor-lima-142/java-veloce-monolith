import axios from 'axios';
import {useAuthStore} from '@/src/stores/auth-store';

const API_BASE_URL = 'http://10.0.2.2:8080/api';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
    timeout: 10000,
});

api.interceptors.request.use(
    (config) => {
        const {accessToken} = useAuthStore.getState();

        if (accessToken) {
            config.headers.Authorization = `Bearer ${accessToken}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            const {refreshToken, setAuth, clearAuth} = useAuthStore.getState();

            if (!refreshToken) {
                clearAuth();
                return Promise.reject(error);
            }

            try {
                const response = await axios.post(`${API_BASE_URL}/auth/refresh`, {
                    refresh_token: refreshToken
                });
                const newData = response.data;
                setAuth(newData);
                originalRequest.headers.Authorization = `Bearer ${newData.access_token}`;
                return api(originalRequest);
            } catch (refreshError) {
                clearAuth();
                return Promise.reject(refreshError);
            }
        }

        return Promise.reject(error);
    }
);

export default api;