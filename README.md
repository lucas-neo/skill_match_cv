# 🎯 Skill Match CV

Sistema inteligente de matching entre candidatos e vagas baseado em compatibilidade de habilidades e experiência.

## 📋 Sobre o Projeto

O **Skill Match CV** é uma API REST desenvolvida em Java com Micronaut que permite:

- 📝 **Cadastro de candidatos** com habilidades e experiência
- 💼 **Cadastro de vagas** com requisitos específicos  
- 🤖 **Matching inteligente** que calcula compatibilidade
- 📊 **Ranking dos melhores candidatos** para cada vaga
- 🔍 **Busca de vagas compatíveis** para cada candidato

## 🚀 Tecnologias

- **Java 21** - Linguagem principal
- **Micronaut 4.0** - Framework reativo e moderno
- **H2 Database** - Banco de dados persistente
- **Micronaut Data JDBC** - ORM para operações no banco
- **Maven** - Gerenciamento de dependências
- **JUnit 5** - Testes unitários

## 🎯 Funcionalidades

### 👥 Gerenciamento de Candidatos
- ✅ Criar candidato
- ✅ Listar todos os candidatos
- ✅ Buscar candidato por ID
- ✅ Atualizar candidato
- ✅ Deletar candidato

### 💼 Gerenciamento de Vagas
- ✅ Criar vaga
- ✅ Listar todas as vagas
- ✅ Buscar vaga por ID
- ✅ Atualizar vaga
- ✅ Deletar vaga

### 🧠 Matching Inteligente
- ✅ **Listar melhores candidatos para uma vaga**
- ✅ Listar vagas compatíveis para um candidato
- ✅ Algoritmo de score baseado em:
  - Habilidades em comum (peso maior)
  - Anos de experiência adequados
  - Proximidade geográfica

## 🔧 Como Executar

### Pré-requisitos
- Java 21
- Maven 3.8+

### Executando a aplicação

```bash
cd back-end/skill-match-api
mvn compile exec:exec
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 API Endpoints

### Candidatos
```bash
GET    /candidatos           # Listar todos
POST   /candidatos           # Criar
GET    /candidatos/{id}      # Buscar por ID
PUT    /candidatos/{id}      # Atualizar
DELETE /candidatos/{id}      # Deletar
```

### Vagas
```bash
GET    /vagas                # Listar todas
POST   /vagas                # Criar
GET    /vagas/{id}           # Buscar por ID
PUT    /vagas/{id}           # Atualizar
DELETE /vagas/{id}           # Deletar
```

### 🎯 Matching (Principal)
```bash
GET /matching/vagas/{vagaId}/melhores-candidatos?limite=N
GET /matching/candidatos/{candidatoId}/vagas-compativeis?limite=N
```

## 📊 Exemplo de Uso

### 1. Criar um candidato
```bash
curl -X POST "http://localhost:8080/candidatos" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "candidato-1",
    "nome": "João Silva",
    "email": "joao.silva@email.com",
    "localizacao": "São Paulo",
    "habilidades": ["Java", "Spring", "MySQL", "Docker"],
    "anosDeExperiencia": 5
  }'
```

### 2. Criar uma vaga
```bash
curl -X POST "http://localhost:8080/vagas" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "vaga-1",
    "titulo": "Desenvolvedor Java Senior",
    "localizacao": "São Paulo",
    "habilidadesRequeridas": ["Java", "Spring", "MySQL"],
    "anosDeExperienciaMinimos": 4
  }'
```

### 3. 🎯 Buscar melhores candidatos para a vaga
```bash
curl -X GET "http://localhost:8080/matching/vagas/vaga-1/melhores-candidatos?limite=5"
```

## 🧪 Executar Testes

```bash
cd back-end/skill-match-api
mvn test
```

## 📈 Algoritmo de Matching

O sistema calcula um **score de compatibilidade** considerando:

- **Habilidades em comum** (+20 pontos por habilidade)
- **Experiência adequada** (+10 pontos se atende o mínimo)
- **Localização** (+5 pontos se mesma cidade)

Os candidatos são ordenados pelo **maior score** (melhor compatibilidade).

## 🎨 Estrutura do Projeto

```
skill_match_cv/
├── back-end/
│   └── skill-match-api/
│       ├── src/
│       │   ├── main/java/skill/match/api/
│       │   │   ├── controller/     # Controllers REST
│       │   │   ├── service/        # Lógica de negócio
│       │   │   ├── model/          # Entidades
│       │   │   └── repository/     # Acesso a dados
│       │   └── test/               # Testes
│       └── pom.xml
└── README.md
```

## 👤 Desenvolvedor

**Lucas Neo** - [lucas-neo](https://github.com/lucas-neo)

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

⭐ **Se este projeto foi útil, deixe uma estrela!**
