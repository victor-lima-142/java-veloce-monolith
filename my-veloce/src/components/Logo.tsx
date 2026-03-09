import React from 'react';
import {StyleSheet, View} from 'react-native';
import Svg, {Circle, G, Path, Polygon, Rect, Text as SvgText} from 'react-native-svg';
import Colors from '@/src/constants/Colors';
import {useColorScheme} from '@/src/components/useColorScheme';

interface LogoProps {
    width?: number;
    height?: number;
}

const Logo: React.FC<LogoProps> = ({width = 250, height = 100}) => {
    const colorScheme = useColorScheme() ?? 'dark';
    const theme = Colors[colorScheme];

    return (
        <View style={styles.container}>
            <Svg
                width={width}
                height={height}
                viewBox="0 0 500 200"
                fill="none"
            >
                {/* ÍCONE */}
                <G transform="translate(40, 50)">
                    {/* Círculo base da logo usando o Azul Primário global */}
                    <Circle cx="60" cy="50" r="50" fill={theme.tint}/>

                    <G transform="translate(10, 10) scale(1.1)">
                        {/* Silhueta do Carro - Usa a cor clara principal para máximo contraste */}
                        <Path
                            d="M11.5,43.2l7.7-22.3h61.7l7.7,22.3H11.5z M82.1,38.8c0-1.2-0.3-2.3-0.9-3.3c-0.6-1-1.4-1.8-2.5-2.2C77.6,33,76.4,32.7,75.1,32.7h-7l-1.9-1.9H33.8l-1.9,1.9h-7c-1.2,0-2.4,0.3-3.6,0.9c-1,0.6-1.8,1.4-2.2,2.5c-0.4,1-0.9,2.2-0.9,3.3H82.1z"
                            fill={theme.text}
                        />

                        {/* Gráfico de Inventário */}
                        {/* Usamos a cor de fundo (background) para criar um efeito moderno de "recorte/vazado" na barra central */}
                        <Rect x="25" y="47" width="10" height="25" fill={theme.text}/>
                        <Rect x="40" y="38" width="10" height="34" fill={theme.background}/>
                        <Rect x="55" y="28" width="10" height="44" fill={theme.text}/>

                        {/* Seta de Velocidade */}
                        <Polygon points="70,20 77,27 70,34" fill={theme.background}/>
                    </G>
                </G>

                {/* TEXTO */}
                <G transform="translate(160, 110)">
                    {/* My */}
                    <SvgText
                        x="0"
                        y="0"
                        fill={theme.text}
                        fontSize="72"
                        fontWeight="300"
                        letterSpacing="-1"
                    >
                        My
                    </SvgText>

                    {/* Veloce */}
                    <SvgText
                        x="95"
                        y="0"
                        fill={theme.tint}
                        fontSize="72"
                        fontWeight="700"
                        letterSpacing="-1"
                    >
                        Veloce
                    </SvgText>

                    {/* Slogan */}
                    <SvgText
                        x="0"
                        y="38"
                        fill={theme.textSecondary}
                        fontSize="20"
                        fontWeight="300"
                        letterSpacing="1"
                    >
                        ESTOQUE AUTOMOTIVO
                    </SvgText>
                </G>
            </Svg>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        alignItems: 'center',
        justifyContent: 'center',
    },
});

export default Logo;