# 🎯 Skill Match CV - Front-end

Interface web moderna e responsiva para o sistema de matching de candidatos e vagas.

## ✨ Funcionalidades

### 🏠 Dashboard
- Visão geral do sistema com estatísticas
- Monitoramento do status da API em tempo real

### 👥 Gerenciamento de Candidatos
- ➕ Adicionar novos candidatos
- 📋 Listar todos os candidatos
- 🗑️ Excluir candidatos
- 🏷️ Visualização de habilidades com tags

### 💼 Gerenciamento de Vagas
- ➕ Adicionar novas vagas
- 📋 Listar todas as vagas
- 🗑️ Excluir vagas
- 🏷️ Visualização de habilidades requeridas

### 🔍 Sistema de Matching
- Busca automática de candidatos compatíveis
- Score de compatibilidade visual
- Destaque de habilidades compatíveis
- Classificação por nível de compatibilidade

## 🚀 Como usar

### 1. Iniciar o Back-end
Primeiro, certifique-se de que o back-end está rodando:
```bash
cd skill-match-api
mvn mn:run
```

### 2. Abrir o Front-end
Simplesmente abra o arquivo `index.html` no navegador ou use um servidor local:

```bash
# Opção 1: Abrir diretamente
open index.html

# Opção 2: Servidor local com Python
python -m http.server 3000

# Opção 3: Servidor local com Node.js
npx http-server -p 3000
```

### 3. Verificar Conexão
- O indicador de status da API deve mostrar "API Online" em verde
- Se estiver offline, verifique se o back-end está rodando na porta 8080

## 💻 Interface

### Layout Responsivo
- **Sidebar**: Navegação principal com indicador de status da API
- **Dashboard**: Estatísticas e visão geral
- **Tabelas**: Listagem organizada de dados
- **Modais**: Formulários para adicionar novos registros
- **Cards de Match**: Resultados visuais de compatibilidade

### Cores e Status
- 🟢 **Verde**: Alta compatibilidade (≥70%)
- 🟡 **Amarelo**: Média compatibilidade (40-69%)
- 🔴 **Vermelho**: Baixa compatibilidade (<40%)

### Responsividade
- Desktop: Layout completo com sidebar
- Tablet: Layout adaptado
- Mobile: Layout compacto e otimizado

## 🔧 Tecnologias

- **HTML5**: Estrutura semântica
- **CSS3**: Estilização moderna com gradientes e animações
- **JavaScript ES6+**: Lógica da aplicação
- **Fetch API**: Comunicação com o back-end
- **CSS Grid/Flexbox**: Layout responsivo

## 📡 Integração com API

### Endpoints utilizados:
- `GET /hello` - Verificação de status
- `GET /candidatos` - Listar candidatos
- `POST /candidatos` - Adicionar candidato
- `DELETE /candidatos/{id}` - Excluir candidato
- `GET /vagas` - Listar vagas
- `POST /vagas` - Adicionar vaga
- `DELETE /vagas/{id}` - Excluir vaga
- `GET /matching/vaga/{id}` - Buscar matches

### Tratamento de Erros
- Mensagens de erro amigáveis
- Indicador visual de status da API
- Fallbacks para quando a API está offline

## 🎨 Recursos Visuais

- ✨ Animações suaves
- 🎨 Gradientes modernos
- 📱 Design responsivo
- 🔄 Loading spinners
- 💡 Indicadores de status
- 🏷️ Tags coloridas para habilidades
- 📊 Cards informativos

## 🚀 Melhorias Futuras

- 📊 Gráficos e charts
- 🔍 Filtros avançados
- 📤 Exportação de dados
- 🔔 Notificações
- 👤 Sistema de usuários
- 📱 PWA (Progressive Web App)

---

**Desenvolvido com ❤️ para facilitar o processo de recrutamento!**
