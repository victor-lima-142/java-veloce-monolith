import {create} from 'zustand';
import {createJSONStorage, persist} from 'zustand/middleware';
import AsyncStorage from '@react-native-async-storage/async-storage';

export interface CompanySelector {
    company_id: string;
    company_name: string;
    company_document: string;
}

export interface StoreSelector {
    store_id: string;
    store_name: string;
    store_document: string;
}

export interface AuthResponse {
    access_token: string;
    refresh_token: string;
    companies: CompanySelector[];
    stores: StoreSelector[];
}

interface AuthState {
    accessToken: string | null;
    refreshToken: string | null;
    companies: CompanySelector[];
    stores: StoreSelector[];
    isAuthenticated: boolean;
    setAuth: (data: AuthResponse) => void;
    clearAuth: () => void;
}

export const useAuthStore = create<AuthState>()(
    persist(
        (set) => ({
            accessToken: null,
            refreshToken: null,
            companies: [],
            stores: [],
            isAuthenticated: false,

            setAuth: (data: AuthResponse) =>
                set({
                    accessToken: data.access_token,
                    refreshToken: data.refresh_token,
                    companies: data.companies,
                    stores: data.stores,
                    isAuthenticated: true,
                }),

            clearAuth: () =>
                set({
                    accessToken: null,
                    refreshToken: null,
                    companies: [],
                    stores: [],
                    isAuthenticated: false,
                }),
        }),
        {
            name: 'auth-storage',
            storage: createJSONStorage(() => AsyncStorage),
        }
    )
);