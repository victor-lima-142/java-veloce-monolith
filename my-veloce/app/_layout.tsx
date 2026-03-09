import {DarkTheme, ThemeProvider} from '@react-navigation/native';
import {useFonts} from 'expo-font';
import {Stack, useRouter, useSegments} from 'expo-router';
import * as SplashScreen from 'expo-splash-screen';
import {useEffect} from 'react';
import 'react-native-reanimated';

import {useColorScheme} from '@/src/components/useColorScheme';
import {useAuthStore} from '@/src/stores/auth-store';
import Colors from "@/src/constants/Colors";

export {ErrorBoundary} from 'expo-router';

export const unstable_settings = {
    initialRouteName: '(tabs)',
};

SplashScreen.preventAutoHideAsync();

const AppDarkTheme = {
    ...DarkTheme,
    colors: {
        ...DarkTheme.colors,
        background: Colors.dark.background,
        card: Colors.dark.surface,
        text: Colors.dark.text,
        border: Colors.dark.border,
        primary: Colors.dark.tint,
    },
};

export default function RootLayout() {
    const [loaded, error] = useFonts({
        SpaceMono: require('../assets/fonts/SpaceMono-Regular.ttf'),
    });

    useEffect(() => {
        if (error) throw error;
    }, [error]);

    useEffect(() => {
        if (loaded) SplashScreen.hideAsync();
    }, [loaded]);

    if (!loaded) return null;

    return <RootLayoutNav/>;
}

function RootLayoutNav() {
    const isAuthenticated = useAuthStore((state) => state.isAuthenticated);
    const segments = useSegments();
    const router = useRouter();

    useEffect(() => {
        if (!segments.length) return;

        const inPublicGroup = segments[0] === 'login';

        if (!isAuthenticated && !inPublicGroup) {
            router.replace('/login');
        } else if (isAuthenticated && inPublicGroup) {
            router.replace('/(tabs)');
        }
    }, [isAuthenticated, segments]);

    return (
        <ThemeProvider value={AppDarkTheme}>
            <Stack>
                <Stack.Screen name="(tabs)" options={{headerShown: false}}/>
                <Stack.Screen name="login" options={{headerShown: false}}/>
                <Stack.Screen name="+not-found"/>
            </Stack>
        </ThemeProvider>
    );
}