import React from 'react';
import {FlatList, StyleSheet, TouchableOpacity, View as RNView,} from 'react-native';

import {Text, View} from '@/src/components/Themed';
import {StoreSelector, useAuthStore} from '@/src/stores/auth-store';
import {useColorScheme} from '@/src/components/useColorScheme';
import Colors from '@/src/constants/Colors';
import {FontAwesome5} from "@expo/vector-icons";
import {useRouter} from "expo-router";

function StoreCard({store}: { store: StoreSelector }) {
    const colorScheme = useColorScheme() ?? 'dark';
    const theme = Colors[colorScheme];
    const router = useRouter();

    const handlePress = () => {
        router.push(`/store/${store.store_id}`);
    };

    return (
        <TouchableOpacity activeOpacity={0.75} onPress={handlePress}>
            <RNView style={[
                styles.card,
                {
                    backgroundColor: theme.surface,
                    borderColor: theme.border,
                    // Sombra moderna parecida com a do login
                    shadowColor: '#000',
                    shadowOffset: {width: 0, height: 4},
                    shadowOpacity: colorScheme === 'dark' ? 0.3 : 0.05,
                    shadowRadius: 12,
                    elevation: 4,
                }
            ]}>
                <RNView style={[styles.iconContainer, {backgroundColor: theme.tint + '1A'}]}>
                    <FontAwesome5 name="store" size={20} style={{color: theme.tint}}/>
                </RNView>

                <RNView style={styles.cardContent}>
                    <Text style={[styles.storeName, {color: theme.text}]} numberOfLines={1}>
                        {store.store_name}
                    </Text>
                    {/* Se quiser reativar o endereço depois, o estilo já está pronto: */}
                    <Text style={[styles.storeAddress, {color: theme.textSecondary}]} numberOfLines={1}>
                        Toque para ver o estoque
                    </Text>
                </RNView>

                <FontAwesome5 name="chevron-right" size={16} color={theme.textSecondary} style={styles.chevron}/>
            </RNView>
        </TouchableOpacity>
    );
}

export default function StoresScreen() {
    const stores = useAuthStore((state) => state?.stores ?? []);
    const colorScheme = useColorScheme() ?? 'dark';
    const theme = Colors[colorScheme];

    if (!stores.length) {
        return (
            <RNView style={[styles.emptyContainer, {backgroundColor: theme.background}]}>
                <View style={[styles.emptyIconCircle, {backgroundColor: theme.surface, borderColor: theme.border}]}>
                    <FontAwesome5 name="store-slash" size={32} color={theme.textSecondary}/>
                </View>
                <Text style={[styles.emptyTitle, {color: theme.text}]}>Nenhuma loja encontrada</Text>
                <Text style={[styles.emptySubtitle, {color: theme.textSecondary}]}>
                    Não há lojas associadas a esta conta no momento.
                </Text>
            </RNView>
        );
    }

    return (
        <RNView style={[styles.container, {backgroundColor: theme.background}]}>
            <FlatList
                data={stores}
                keyExtractor={(item) => String(item.store_id ?? item.store_name)}
                renderItem={({item}) => <StoreCard store={item}/>}
                contentContainerStyle={styles.list}
                ItemSeparatorComponent={() => <RNView style={styles.separator}/>}
                showsVerticalScrollIndicator={false}
            />
        </RNView>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    list: {
        padding: 24, // Mais respiro, igual ao login
    },
    card: {
        flexDirection: 'row',
        alignItems: 'center',
        padding: 20,
        borderRadius: 20, // Cantos mais arredondados
        borderWidth: 1,
    },
    iconContainer: {
        width: 48,
        height: 48,
        borderRadius: 14,
        alignItems: 'center',
        justifyContent: 'center',
        marginRight: 16,
    },
    cardContent: {
        flex: 1,
        justifyContent: 'center',
    },
    storeName: {
        fontSize: 17,
        fontWeight: '700',
        marginBottom: 4,
        letterSpacing: -0.3,
    },
    storeAddress: {
        fontSize: 14,
    },
    chevron: {
        marginLeft: 12,
        opacity: 0.5,
    },
    separator: {
        height: 16, // Mais espaço entre os cards
    },
    // Estilos do Empty State
    emptyContainer: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        padding: 32,
    },
    emptyIconCircle: {
        width: 80,
        height: 80,
        borderRadius: 40,
        alignItems: 'center',
        justifyContent: 'center',
        marginBottom: 24,
        borderWidth: 1,
    },
    emptyTitle: {
        fontSize: 20,
        fontWeight: '700',
        marginBottom: 12,
        letterSpacing: -0.5,
    },
    emptySubtitle: {
        fontSize: 15,
        textAlign: 'center',
        lineHeight: 22,
        paddingHorizontal: 20,
    },
});