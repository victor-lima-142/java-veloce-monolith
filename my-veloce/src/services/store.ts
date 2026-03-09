import {AuthResponse} from '@/src/stores/auth-store';
import api from "@/src/services/api";

export const authService = {
    login: async (identifier: string, password: string): Promise<AuthResponse> => {
        const response = await api.post<AuthResponse>('/auth/login', {
            email: identifier, // e-mail or CNPJ
            password,
        });
        return response.data;
    },
};