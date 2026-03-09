const primaryBlue = '#3B82F6'; // Azul moderno dos botões
const dangerRed = '#EF4444';   // Vermelho para erros/alertas

export default {
    light: {
        text: '#0F172A',           // Slate 900 (Texto principal quase preto)
        textSecondary: '#64748B',  // Slate 500 (Subtítulos)
        background: '#F8FAFC',     // Slate 50 (Fundo da tela cinza bem claro)
        surface: '#FFFFFF',        // Branco (Fundo de cards e inputs)
        border: '#E2E8F0',         // Slate 200 (Bordas suaves)
        tint: primaryBlue,
        tabIconDefault: '#94A3B8',
        tabIconSelected: primaryBlue,
        danger: dangerRed,
    },
    dark: {
        text: '#F1F5F9',           // Slate 100 (Texto principal quase branco)
        textSecondary: '#94A3B8',  // Slate 400 (Subtítulos do login)
        background: '#0F172A',     // Slate 900 (Fundo principal do login)
        surface: '#1E293B',        // Slate 800 (Fundo dos cards do login)
        border: '#334155',         // Slate 700 (Bordas dos cards/inputs)
        tint: primaryBlue,
        tabIconDefault: '#64748B',
        tabIconSelected: primaryBlue,
        danger: dangerRed,
    },
};