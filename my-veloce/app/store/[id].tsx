import { Stack, useLocalSearchParams } from 'expo-router';
import { useEffect } from 'react';
import { useStoreDetails, VehicleSummary } from '@/src/stores/store-unit-store';
import { ActivityIndicator, FlatList, StyleSheet, Text, View } from "react-native";
import { FontAwesome5 } from "@expo/vector-icons";
import Colors from '@/src/constants/Colors';
import { useColorScheme } from '@/src/components/useColorScheme';
import { translateColor } from "@/src/utils/translator";

// Componente para exibir os dados da Loja - agora posicionado no topo
const StoreInfoHeader = ({ store, theme }: { store: any, theme: typeof Colors.dark }) => {
    if (!store) return null;

    return (
        <View style={[
            styles.storeInfoContainer,
            {
                backgroundColor: theme.surface,
                borderColor: theme.border,
                shadowColor: '#000',
                shadowOffset: { width: 0, height: 4 },
                shadowOpacity: 0.2,
                shadowRadius: 8,
                elevation: 4,
            }
        ]}>
            <View style={styles.storeInfoContent}>
                <Text style={[styles.storeName, { color: theme.text }]}>
                    {store.name}
                </Text>

                <View style={styles.storeInfoRow}>
                    <FontAwesome5 name="id-card" size={14} color={theme.textSecondary} style={styles.storeInfoIcon} />
                    <Text style={[styles.storeInfoText, { color: theme.textSecondary }]}>
                        {store.document}
                    </Text>
                </View>

                <View style={styles.storeInfoRow}>
                    <FontAwesome5 name="phone-alt" size={14} color={theme.textSecondary} style={styles.storeInfoIcon} />
                    <Text style={[styles.storeInfoText, { color: theme.textSecondary }]}>
                        {store.phone}
                    </Text>
                </View>

                {store.address && (
                    <View style={[styles.storeInfoRow, { alignItems: 'flex-start' }]}>
                        <FontAwesome5 name="map-marker-alt" size={14} color={theme.textSecondary} style={[styles.storeInfoIcon, { marginTop: 3 }]} />
                        <Text style={[styles.storeInfoText, { color: theme.textSecondary, flex: 1, lineHeight: 20 }]}>
                            {store.address.street}, {store.address.number}
                            {store.address.complement ? ` - ${store.address.complement}` : ''} - {store.address.district},{'\n'}
                            {store.address.city} - {store.address.state}, {store.address.country}
                        </Text>
                    </View>
                )}
            </View>
        </View>
    );
};

const VehicleCard = ({ vehicle, theme }: { vehicle: VehicleSummary, theme: typeof Colors.dark }) => {
    const formattedPrice = new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    }).format(vehicle.value || 0);
    const formatName = (name: string) => {
        const parts = vehicle.user_creator.name.split(" ");

        let displayName: string;

        if (parts.length === 1) {
            displayName = parts[0];
        } else {
            displayName = `${parts[0]} ${parts[parts.length - 1]}`;
        }
        return displayName;
    }
    return (
        <View style={[
            styles.card,
            {
                backgroundColor: theme.surface,
                borderColor: theme.border,
                shadowColor: '#000',
                shadowOffset: { width: 0, height: 8 },
                shadowOpacity: 0.3,
                shadowRadius: 16,
                elevation: 8,
            }
        ]}>
            <View style={styles.cardHeader}>
                <View style={{ display: "flex", justifyContent: "flex-end", paddingTop: 16}}>
                    <View style={[styles.badge, { position: "absolute", right: 0}]}>
                        <FontAwesome5 name="user" size={10} color={theme.textSecondary} />
                        <Text style={[styles.badgeText, { color: theme.textSecondary, fontSize: 9 }]}>
                            {formatName(vehicle.user_creator)}
                        </Text>
                    </View>
                </View>
                <Text style={[styles.vehicleTitle, { color: theme.text }]} numberOfLines={1}>
                    {vehicle.name}
                </Text>
                <Text style={styles.vehiclePrice}>{formattedPrice}</Text>
            </View>

            <View style={styles.specificationsRow}>
                <View style={[styles.badge, { backgroundColor: theme.background, borderColor: theme.border }]}>
                    <FontAwesome5 name="calendar-alt" size={12} color={theme.textSecondary} />
                    <Text style={[styles.badgeText, { color: theme.textSecondary }]}>
                        {vehicle.year_manufacture}/{vehicle.year_model}
                    </Text>
                </View>
                <View style={[styles.badge, { backgroundColor: theme.background, borderColor: theme.border }]}>
                    <FontAwesome5 name="road" size={12} color={theme.textSecondary} />
                    <Text style={[styles.badgeText, { color: theme.textSecondary }]}>
                        {vehicle.mileage} km
                    </Text>
                </View>
                <View style={[styles.badge, { backgroundColor: theme.background, borderColor: theme.border }]}>
                    <FontAwesome5 name="palette" size={12} color={theme.textSecondary} />
                    <Text style={[styles.badgeText, { color: theme.textSecondary }]}>
                        {translateColor(vehicle.color)}
                    </Text>
                </View>
            </View>

            <View style={[styles.detailsFooter, { borderTopColor: theme.border }]}>
                {vehicle.car ? (
                    <View style={styles.footerRow}>
                        <FontAwesome5 name="car" size={14} color={theme.textSecondary} style={styles.footerIcon} />
                        <Text style={[styles.footerText, { color: theme.textSecondary }]}>
                            {vehicle.car.body_type} • {vehicle.car.transmission} • {vehicle.car.doors} portas
                        </Text>
                    </View>
                ) : vehicle.moto ? (
                    <View style={styles.footerRow}>
                        <FontAwesome5 name="motorcycle" size={14} color={theme.textSecondary}
                                      style={styles.footerIcon} />
                        <Text style={[styles.footerText, { color: theme.textSecondary }]}>
                            {vehicle.moto.engine_cc}cc • {vehicle.moto.transmission}
                        </Text>
                    </View>
                ) : null}
            </View>
        </View>
    );
};

export default function StoreDetailScreen() {
    const { id } = useLocalSearchParams<{ id: string }>();
    const { fetchStore, currentStore, inventory, loading } = useStoreDetails();

    const colorScheme = useColorScheme() ?? 'dark';
    const theme = Colors[colorScheme];

    useEffect(() => {
        if (id) {
            fetchStore(id, 0);
        }
    }, [id]);

    if (loading || !currentStore) {
        return (
            <View style={[styles.loadingContainer, { backgroundColor: theme.background }]}>
                <Stack.Screen options={{ title: 'Carregando Loja...', headerBackTitle: 'Voltar' }} />
                <ActivityIndicator size="large" color={theme.tint} />
            </View>
        );
    }

    return (
        <View style={[styles.container, { backgroundColor: theme.background }]}>
            <Stack.Screen
                options={{
                    title: currentStore.name,
                    headerBackTitle: 'Voltar',
                }}
            />

            {/* Cabeçalho da loja posicionado estaticamente no topo, fora da FlatList padded */}
            <StoreInfoHeader store={currentStore} theme={theme} />

            <FlatList
                data={inventory}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => <VehicleCard vehicle={item} theme={theme} />}
                contentContainerStyle={styles.listContainer}
                showsVerticalScrollIndicator={false}
                ListEmptyComponent={
                    <View style={styles.emptyContainer}>
                        <View style={[styles.emptyIconCircle, {
                            backgroundColor: theme.surface,
                            borderColor: theme.border
                        }]}>
                            <FontAwesome5 name="car-side" size={32} color={theme.textSecondary} />
                        </View>
                        <Text style={[styles.emptyTitle, { color: theme.text }]}>Estoque vazio</Text>
                        <Text style={[styles.emptySubtitle, { color: theme.textSecondary }]}>
                            Nenhum veículo foi encontrado no estoque desta loja.
                        </Text>
                    </View>
                }
            />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    loadingContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    listContainer: {
        padding: 24,
    },
    storeInfoContainer: {
        width: '100%',
        borderRadius: 0,
        borderWidth: 1,
    },
    storeInfoContent: {
        paddingHorizontal: 24,
        paddingVertical: 20,
    },
    storeName: {
        fontSize: 22,
        fontWeight: '800',
        marginBottom: 16,
        letterSpacing: -0.5,
    },
    storeInfoRow: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 10,
    },
    storeInfoIcon: {
        width: 20,
        marginRight: 8,
        textAlign: 'center',
    },
    storeInfoText: {
        fontSize: 14,
        fontWeight: '500',
    },
    card: {
        flexDirection: 'column',
        padding: 24,
        borderRadius: 20,
        borderWidth: 1,
        marginBottom: 20,
    },
    cardHeader: {
        marginBottom: 16,
    },
    vehicleTitle: {
        fontSize: 18,
        fontWeight: '700',
        marginBottom: 6,
        letterSpacing: -0.3,
    },
    vehiclePrice: {
        fontSize: 22,
        fontWeight: '800',
        color: '#34D399',
        letterSpacing: -0.5,
    },
    specificationsRow: {
        flexDirection: 'row',
        flexWrap: 'wrap',
        gap: 8,
        marginBottom: 16,
    },
    badge: {
        flexDirection: 'row',
        alignItems: 'center',
        borderWidth: 1,
        paddingHorizontal: 10,
        paddingVertical: 6,
        borderRadius: 8,
    },
    badgeText: {
        fontSize: 12,
        marginLeft: 6,
        fontWeight: '600',
    },
    detailsFooter: {
        borderTopWidth: 1,
        paddingTop: 16,
    },
    footerRow: {
        flexDirection: 'row',
        alignItems: 'center',
    },
    footerIcon: {
        marginRight: 8,
    },
    footerText: {
        fontSize: 13,
        textTransform: 'capitalize',
        fontWeight: '500',
    },
    emptyContainer: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        paddingVertical: 40,
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