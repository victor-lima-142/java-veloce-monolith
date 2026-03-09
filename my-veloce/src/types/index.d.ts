export interface PageMetadata {
    total_count: number;
    page: number;
    size: number;
    total_pages: number;
    has_next: boolean;
    has_previous: boolean;
}

export interface PagedResponse<T> {
    metadata: PageMetadata;
    data: T[];
}

export type VehicleColor =
    "WHITE"
    | "BLACK"
    | "SILVER"
    | "GRAY"
    | "RED"
    | "BLUE"
    | "GREEN"
    | "YELLOW"
    | "ORANGE"
    | "BROWN"
    | "BEIGE"
    | "GOLD"
    | "OTHER";