import {create} from 'zustand';
import api from '../services/api';
import {PagedResponse, VehicleColor} from "@/src/types";

export interface CompanyStoreDetail {
    id: string;
    name: string;
    phone: string;
    address: any;
}

export interface CarSpecSummary {
    id: string;
    vehicle_id: string;
    doors: number;
    fuel: string;
    engine: string;
    transmission: string;
    body_type: string;
}

export interface MotoSpecSummary {
    id: string;
    vehicle_id: string;
    body_type: string;
    fuel: string;
    engine: string;
    transmission: string;
    engine_cc: number;
    false: boolean;
}

export interface VehicleSummary {
    id: string;
    name: string;
    type: string;
    color: VehicleColor;
    year_manufacture: number;
    year_model: number;
    mileage: number;
    market_value: number;
    value: number;
    manufacturer_name: string;
    inventory_code: string;
    inventory_item_id: string;
    model: string;
    car?: CarSpecSummary;
    moto?: MotoSpecSummary;
}

interface StoreState {
    currentStore: CompanyStoreDetail | null;
    inventory: VehicleSummary[];
    loading: boolean;
    fetchStore: (storeId: string, page: number) => Promise<void>;
}

export const useStoreDetails = create<StoreState>((set, get) => ({
    currentStore: null,
    inventory: [],
    loading: false,

    fetchStore: async (storeId: string, page: number = 0) => {
        set({loading: true});
        try {
            const [detail, vehicles] = await Promise.all([
                api.get<CompanyStoreDetail>(`/store/${storeId}`),
                api.get<PagedResponse<VehicleSummary>>(
                    `/store/${storeId}/inventory`,
                    {params: {page, size: 20}}
                )
            ]);

            if (detail.data) {
                set({currentStore: detail.data});
            }
            if (vehicles.data?.data?.length > 0) {
                set((state) => ({
                    inventory: page === 0 ? vehicles.data.data : [...state.inventory, ...vehicles.data.data]
                }));
            }
        } catch (error) {
            console.error("Erro ao buscar dados", error);
        } finally {
            set({loading: false});
        }
    }
}));