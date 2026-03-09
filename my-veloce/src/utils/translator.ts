import {VehicleColor} from "@/src/types";

const colorDictionary: Record<VehicleColor, string> = {
    WHITE: "Branco",
    BLACK: "Preto",
    SILVER: "Prata",
    GRAY: "Cinza",
    RED: "Vermelho",
    BLUE: "Azul",
    GREEN: "Verde",
    YELLOW: "Amarelo",
    ORANGE: "Laranja",
    BROWN: "Marrom",
    BEIGE: "Bege",
    GOLD: "Dourado",
    OTHER: "Outro",
};

export const translateColor = (color?: string | null): string => {
    if (!color) return "Não informado";

    return colorDictionary[color as VehicleColor] || color;
};