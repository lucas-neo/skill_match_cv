# 🧪 Guia de Teste Manual - Skill Match API

## ✅ **Resposta à sua pergunta:**
**SIM! Você consegue listar os melhores candidatos para uma vaga específica!**

Seu sistema tem o endpoint: `GET /matching/vagas/{vagaId}/melhores-candidatos`

## 🚀 **Como testar sua API:**

### 1. **Endpoints Disponíveis:**

#### **Candidatos:**
- `POST /candidatos` - Criar candidato
- `GET /candidatos/{id}` - Buscar candidato
- `PUT /candidatos/{id}` - Atualizar candidato
- `DELETE /candidatos/{id}` - Deletar candidato

#### **Vagas:**
- `POST /vagas` - Criar vaga
- `GET /vagas/{id}` - Buscar vaga
- `PUT /vagas/{id}` - Atualizar vaga
- `DELETE /vagas/{id}` - Deletar vaga

#### **Matching (Principal funcionalidade):**
- `GET /matching/vagas/{vagaId}/melhores-candidatos?limite=N` - **Lista os melhores candidatos para uma vaga**
- `GET /matching/candidatos/{candidatoId}/vagas-compativeis?limite=N` - Lista vagas compatíveis para um candidato

### 2. **Exemplo de Uso Completo:**

#### **Passo 1: Criar um candidato**
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

#### **Passo 2: Criar mais candidatos**
```bash
curl -X POST "http://localhost:8080/candidatos" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "candidato-2",
    "nome": "Maria Santos",
    "email": "maria.santos@email.com",
    "localizacao": "Rio de Janeiro",
    "habilidades": ["Java", "Spring Boot", "PostgreSQL", "Kubernetes"],
    "anosDeExperiencia": 7
  }'
```

#### **Passo 3: Criar uma vaga**
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

#### **Passo 4: BUSCAR OS MELHORES CANDIDATOS PARA A VAGA** 🎯
```bash
curl -X GET "http://localhost:8080/matching/vagas/vaga-1/melhores-candidatos?limite=5"
```

#### **Passo 5: Buscar vagas compatíveis para um candidato**
```bash
curl -X GET "http://localhost:8080/matching/candidatos/candidato-1/vagas-compativeis?limite=5"
```

### 3. **Como o algoritmo funciona:**

O sistema calcula um **score de compatibilidade** baseado em:
- ✅ **Habilidades em comum** (maior peso)
- ✅ **Anos de experiência** (se atende o mínimo)
- ✅ **Localização** (preferência por mesma cidade)

### 4. **Problema atual:**
Há um problema de configuração com SQLite que está impedindo os testes.

### 5. **Soluções para testar:**

#### **Opção A: Resolver a configuração SQLite**
- Ajustar configurações de pool de conexão
- Ou migrar para H2 (banco em memória)

#### **Opção B: Usar Postman/Insomnia**
- Importar a collection de endpoints
- Testar visualmente

#### **Opção C: Testes automatizados**
- Criar testes unitários com `@MicronautTest`
- Testar a lógica sem depender do banco

## 🎯 **Resposta direta:**

**SIM, seu sistema consegue listar os melhores candidatos para uma vaga específica através do endpoint:**

```
GET /matching/vagas/{vagaId}/melhores-candidatos?limite=10
```

O sistema:
1. ✅ Recebe o ID da vaga
2. ✅ Busca todos os candidatos
3. ✅ Calcula score de compatibilidade para cada um
4. ✅ Ordena por score (melhor primeiro)
5. ✅ Retorna os N melhores candidatos

## 🔧 **Próximos passos:**
1. Resolver a configuração do banco
2. Testar com dados reais
3. Ajustar algoritmo de score conforme necessário
