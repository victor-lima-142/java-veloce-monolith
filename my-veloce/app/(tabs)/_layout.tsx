import React, {useState} from 'react';
import {SymbolView} from 'expo-symbols';
import {Tabs, useRouter} from 'expo-router';
import {FlatList, Modal, Pressable, StyleSheet, Text, TouchableOpacity, View} from 'react-native';

import Colors from '@/src/constants/Colors';
import {useColorScheme} from '@/src/components/useColorScheme';
import {useClientOnlyValue} from '@/src/components/useClientOnlyValue';
import {useAuthStore} from '@/src/stores/auth-store';

function CompanySelector() {
    const colorScheme = useColorScheme();
    const companies = useAuthStore((state) => state?.companies ?? []);
    const [selectedCompany, setSelectedCompany] = useState(companies[0] ?? null);
    const [modalVisible, setModalVisible] = useState(false);

    if (!companies.length) return null;

    return (
        <>
            <Pressable
                onPress={() => setModalVisible(true)}
                style={[styles.selectorButton, {borderColor: "none"}]}
            >
                <Text
                    style={[styles.selectorText, {color: Colors[colorScheme].text}]}
                    numberOfLines={1}
                >
                    {selectedCompany?.company_name ?? 'Select Company'}
                </Text>
                <Text style={{color: Colors[colorScheme].tint, marginLeft: 4}}>▾</Text>
            </Pressable>

            <Modal
                visible={modalVisible}
                transparent
                animationType="fade"
                onRequestClose={() => setModalVisible(false)}
            >
                <Pressable style={styles.modalOverlay} onPress={() => setModalVisible(false)}>
                    <View
                        style={[styles.modalContainer, {backgroundColor: colorScheme === 'dark' ? '#1c1c1e' : '#fff'}]}>
                        <Text style={[styles.modalTitle, {color: Colors[colorScheme].text}]}>
                            Select Company
                        </Text>
                        <FlatList
                            data={companies}
                            keyExtractor={(item) => String(item.company_id ?? item.company_name)}
                            renderItem={({item}) => (
                                <TouchableOpacity
                                    style={[
                                        styles.modalItem,
                                        selectedCompany?.company_id === item.company_id && {
                                            backgroundColor: Colors[colorScheme].tint + '22',
                                        },
                                    ]}
                                    onPress={() => {
                                        setSelectedCompany(item);
                                        setModalVisible(false);
                                    }}
                                >
                                    <Text style={[styles.modalItemText, {color: Colors[colorScheme].text}]}>
                                        {item.company_name}
                                    </Text>
                                    {selectedCompany?.company_id === item.company_id && (
                                        <Text style={{color: Colors[colorScheme].tint}}>✓</Text>
                                    )}
                                </TouchableOpacity>
                            )}
                        />
                    </View>
                </Pressable>
            </Modal>
        </>
    );
}

export default function TabLayout() {
    const colorScheme = useColorScheme();
    const clearAuth = useAuthStore((state) => state.clearAuth);
    const router = useRouter();

    const handleLogout = () => {
        clearAuth();
        router.replace('/login');
    };

    return (
        <Tabs
            screenOptions={{
                tabBarActiveTintColor: Colors[colorScheme].tint,
                headerShown: useClientOnlyValue(false, true),
            }}
        >
            <Tabs.Screen
                name="index"
                options={{
                    title: 'Stores',
                    headerTitle: () => <CompanySelector/>,
                    tabBarIcon: ({color}) => (
                        <SymbolView
                            name={{ios: 'storefront.fill', android: 'store', web: 'store'}}
                            tintColor={color}
                            size={28}
                        />
                    ),
                    headerRight: () => (
                        <Pressable onPress={handleLogout} style={{marginRight: 15}}>
                            {({pressed}) => (
                                <SymbolView
                                    name={{ios: 'rectangle.portrait.and.arrow.right', android: 'logout', web: 'logout'}}
                                    size={24}
                                    tintColor={Colors[colorScheme].text}
                                    style={{opacity: pressed ? 0.5 : 1}}
                                />
                            )}
                        </Pressable>
                    ),
                }}
            />
            <Tabs.Screen
                name="two"
                options={{
                    title: 'Tab Two',
                    tabBarIcon: ({color}) => (
                        <SymbolView
                            name={{ios: 'chevron.left.forwardslash.chevron.right', android: 'code', web: 'code'}}
                            tintColor={color}
                            size={28}
                        />
                    ),
                }}
            />
        </Tabs>
    );
}

const styles = StyleSheet.create({
    selectorButton: {
        flexDirection: 'row',
        alignItems: 'center',
        borderWidth: 1,
        borderRadius: 8,
        paddingHorizontal: 10,
        paddingVertical: 5,
        maxWidth: 200,
    },
    selectorText: {
        fontSize: 14,
        fontWeight: '600',
        flexShrink: 1,
    },
    modalOverlay: {
        flex: 1,
        backgroundColor: 'rgba(0,0,0,0.4)',
        justifyContent: 'center',
        alignItems: 'center',
    },
    modalContainer: {
        width: '75%',
        borderRadius: 14,
        paddingVertical: 12,
        shadowColor: '#000',
        shadowOpacity: 0.2,
        shadowRadius: 10,
        elevation: 8,
    },
    modalTitle: {
        fontSize: 13,
        fontWeight: '600',
        textTransform: 'uppercase',
        letterSpacing: 0.5,
        marginHorizontal: 16,
        marginBottom: 8,
        opacity: 0.5,
    },
    modalItem: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingHorizontal: 16,
        paddingVertical: 12,
    },
    modalItemText: {
        fontSize: 16,
    },
});