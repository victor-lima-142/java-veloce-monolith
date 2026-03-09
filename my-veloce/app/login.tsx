import React, {useState} from 'react';
import {
    ActivityIndicator,
    Alert,
    KeyboardAvoidingView,
    Platform,
    ScrollView,
    StatusBar,
    StyleSheet,
    Text,
    TextInput,
    TouchableOpacity,
    View,
} from 'react-native';
import {useAuthStore} from '@/src/stores/auth-store';
import {authService} from '@/src/services/auth';
import {useRouter} from "expo-router";
import Logo from "@/src/components/Logo";
import {FontAwesome5} from '@expo/vector-icons';

export default function LoginScreen() {
    const [identifier, setIdentifier] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [errors, setErrors] = useState<{ identifier?: string; password?: string }>({});
    const router = useRouter();
    const setAuth = useAuthStore((state) => state.setAuth);

    const validate = (): boolean => {
        const newErrors: { identifier?: string; password?: string } = {};

        if (!identifier.trim()) {
            newErrors.identifier = 'E-mail ou CNPJ é obrigatório';
        }

        if (!password) {
            newErrors.password = 'Senha é obrigatória';
        } else if (password.length < 6) {
            newErrors.password = 'Senha deve ter pelo menos 6 caracteres';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleLogin = async () => {
        if (!validate()) return;

        setIsLoading(true);
        try {
            const response = await authService.login(identifier.trim(), password);
            setAuth(response);
            router.replace('/(tabs)');
        } catch (error: any) {
            const message =
                error?.response?.data?.message ||
                'Não foi possível realizar o login. Verifique suas credenciais.';
            Alert.alert('Erro ao entrar', message);
            console.error(error);
        } finally {
            setIsLoading(false);
        }
    };

    const formatUsername = (text: string) => {
        const containsLetters = /[a-zA-Z]/.test(text);
        if (containsLetters || text.includes('@')) {
            return text;
        }

        const numericOnly = text.replace(/\D/g, '');

        if (numericOnly.length > 0) {
            return numericOnly
                .replace(/^(\d{2})(\d)/, '$1.$2')
                .replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3')
                .replace(/\.(\d{3})(\d)/, '.$1/$2')
                .replace(/(\d{4})(\d)/, '$1-$2')
                .substring(0, 18); // Limita ao tamanho máximo do CNPJ formatado
        }

        return text;
    };

    const handleIdentifierChange = (text: string) => {
        const formatted = formatUsername(text);
        setIdentifier(formatted);
        if (errors.identifier) setErrors((prev) => ({...prev, identifier: undefined}));
    };

    return (
        <KeyboardAvoidingView
            style={styles.container}
            behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
        >
            <StatusBar barStyle="light-content" backgroundColor="#0F172A"/>
            <ScrollView
                contentContainerStyle={styles.scrollContent}
                keyboardShouldPersistTaps="handled"
                showsVerticalScrollIndicator={false}
            >
                {/* Header */}
                <View style={styles.header}>
                    <Logo/>
                    <Text style={styles.subtitle}>Acesse sua conta para continuar</Text>
                </View>

                {/* Form Card */}
                <View style={styles.card}>
                    {/* Identifier Field */}
                    <View style={styles.fieldGroup}>
                        <Text style={styles.label}>E-mail ou CNPJ</Text>
                        <View style={[styles.inputWrapper, errors.identifier ? styles.inputError : null]}>
                            <TextInput
                                style={styles.input}
                                placeholder="Usuário"
                                placeholderTextColor="#64748B"
                                value={identifier}
                                onChangeText={handleIdentifierChange}
                                keyboardType="email-address"
                                autoCapitalize="none"
                                autoCorrect={false}
                                returnKeyType="next"
                            />
                        </View>
                        {errors.identifier && (
                            <Text style={styles.errorText}>{errors.identifier}</Text>
                        )}
                    </View>

                    {/* Password Field */}
                    <View style={styles.fieldGroup}>
                        <Text style={styles.label}>Senha</Text>
                        <View style={[styles.inputWrapper, errors.password ? styles.inputError : null]}>
                            <TextInput
                                style={[styles.input, styles.passwordInput]}
                                placeholder="Digite sua senha"
                                placeholderTextColor="#64748B"
                                value={password}
                                onChangeText={(text) => {
                                    setPassword(text);
                                    if (errors.password) setErrors((prev) => ({...prev, password: undefined}));
                                }}
                                secureTextEntry={!showPassword}
                                returnKeyType="done"
                                onSubmitEditing={handleLogin}
                            />
                            <TouchableOpacity
                                style={styles.eyeButton}
                                onPress={() => setShowPassword(!showPassword)}
                                activeOpacity={0.7}
                            >
                                <View>
                                    {showPassword ? (
                                        <FontAwesome5 name="eye-slash" size={20} color="white"/>
                                    ) : (
                                        <FontAwesome5 name="eye" size={20} color="white"/>
                                    )}
                                </View>
                            </TouchableOpacity>
                        </View>
                        {errors.password && (
                            <Text style={styles.errorText}>{errors.password}</Text>
                        )}
                    </View>

                    {/* Forgot Password */}
                    <TouchableOpacity style={styles.forgotContainer} activeOpacity={0.7}>
                        <Text style={styles.forgotText}>Esqueceu a senha?</Text>
                    </TouchableOpacity>

                    {/* Login Button */}
                    <TouchableOpacity
                        style={[styles.loginButton, isLoading && styles.loginButtonDisabled]}
                        onPress={handleLogin}
                        disabled={isLoading}
                        activeOpacity={0.85}
                    >
                        {isLoading ? (
                            <ActivityIndicator color="#FFFFFF" size="small"/>
                        ) : (
                            <Text style={styles.loginButtonText}>Entrar</Text>
                        )}
                    </TouchableOpacity>
                </View>

                {/* Footer */}
                <Text style={styles.footerText}>
                    Ao entrar, você concorda com nossos{' '}
                    <Text style={styles.footerLink}>Termos de Uso</Text>
                </Text>
            </ScrollView>
        </KeyboardAvoidingView>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#0F172A',
    },
    scrollContent: {
        flexGrow: 1,
        justifyContent: 'center',
        paddingHorizontal: 24,
        paddingVertical: 48,
    },

    // Header
    header: {
        alignItems: 'center',
        marginBottom: 40,
        width: "100%",
    },
    logoContainer: {
        width: 64,
        height: 64,
        borderRadius: 20,
        backgroundColor: '#3B82F6',
        alignItems: 'center',
        justifyContent: 'center',
        marginBottom: 16,
        shadowColor: '#3B82F6',
        shadowOffset: {width: 0, height: 8},
        shadowOpacity: 0.4,
        shadowRadius: 16,
        elevation: 10,
    },
    logoText: {
        fontSize: 28,
        fontWeight: '800',
        color: '#FFFFFF',
        letterSpacing: -1,
    },
    brandName: {
        fontSize: 28,
        fontWeight: '700',
        color: '#F1F5F9',
        letterSpacing: -0.5,
        marginBottom: 8,
    },
    subtitle: {
        fontSize: 15,
        color: '#94A3B8',
        textAlign: 'center',
    },

    // Card
    card: {
        backgroundColor: '#1E293B',
        borderRadius: 24,
        padding: 28,
        borderWidth: 1,
        borderColor: '#334155',
        shadowColor: '#000',
        shadowOffset: {width: 0, height: 16},
        shadowOpacity: 0.3,
        shadowRadius: 24,
        elevation: 12,
        marginBottom: 24,
    },

    // Fields
    fieldGroup: {
        marginBottom: 20,
    },
    label: {
        fontSize: 13,
        fontWeight: '600',
        color: '#CBD5E1',
        marginBottom: 8,
        letterSpacing: 0.3,
        textTransform: 'uppercase',
    },
    inputWrapper: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#0F172A',
        borderRadius: 12,
        borderWidth: 1,
        borderColor: '#334155',
        overflow: 'hidden',
    },
    inputError: {
        borderColor: '#EF4444',
    },
    input: {
        flex: 1,
        paddingHorizontal: 16,
        paddingVertical: 14,
        fontSize: 15,
        color: '#F1F5F9',
    },
    passwordInput: {
        paddingRight: 8,
    },
    eyeButton: {
        paddingHorizontal: 14,
        paddingVertical: 14,
    },
    eyeIcon: {
        fontSize: 18,
    },
    errorText: {
        fontSize: 12,
        color: '#EF4444',
        marginTop: 6,
        marginLeft: 4,
    },

    // Forgot
    forgotContainer: {
        alignSelf: 'flex-end',
        marginBottom: 28,
        marginTop: -8,
    },
    forgotText: {
        fontSize: 13,
        color: '#3B82F6',
        fontWeight: '500',
    },

    // Button
    loginButton: {
        backgroundColor: '#3B82F6',
        borderRadius: 14,
        paddingVertical: 16,
        alignItems: 'center',
        justifyContent: 'center',
        shadowColor: '#3B82F6',
        shadowOffset: {width: 0, height: 6},
        shadowOpacity: 0.35,
        shadowRadius: 12,
        elevation: 8,
        minHeight: 52,
    },
    loginButtonDisabled: {
        opacity: 0.65,
    },
    loginButtonText: {
        fontSize: 16,
        fontWeight: '700',
        color: '#FFFFFF',
        letterSpacing: 0.3,
    },

    // Footer
    footerText: {
        textAlign: 'center',
        fontSize: 12,
        color: '#475569',
        lineHeight: 18,
    },
    footerLink: {
        color: '#3B82F6',
        fontWeight: '500',
    },
});