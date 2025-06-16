-- Script de inicialização do banco de dados
-- Executado automaticamente na inicialização

CREATE TABLE IF NOT EXISTS candidato (
    id VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    localizacao VARCHAR(255),
    habilidades_csv TEXT,
    anos_de_experiencia INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS vaga (
    id VARCHAR(255) PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    localizacao VARCHAR(255),
    habilidades_requeridas_csv TEXT,
    anos_de_experiencia_minimos INTEGER DEFAULT 0
);

-- Inserir dados de exemplo usando MERGE para evitar duplicatas
MERGE INTO candidato (id, nome, email, localizacao, habilidades_csv, anos_de_experiencia) VALUES
('1', 'João Silva', 'joao@exemplo.com', 'São Paulo, SP', 'Java, Spring, MySQL, REST API', 5),
('2', 'Maria Santos', 'maria@exemplo.com', 'Rio de Janeiro, RJ', 'JavaScript, React, Node.js, MongoDB', 3),
('3', 'Pedro Oliveira', 'pedro@exemplo.com', 'Belo Horizonte, MG', 'Python, Django, PostgreSQL, AWS', 4),
('4', 'Ana Costa', 'ana.costa@email.com', 'São Paulo, SP', 'Java, Spring Boot, Docker, Kubernetes, Microservices', 7),
('5', 'Carlos Ferreira', 'carlos.ferreira@email.com', 'Rio de Janeiro, RJ', 'React, TypeScript, Next.js, GraphQL, AWS', 4),
('6', 'Lucia Rodrigues', 'lucia.rodrigues@email.com', 'Porto Alegre, RS', 'Python, FastAPI, Docker, PostgreSQL, Redis', 6),
('7', 'Rafael Mendes', 'rafael.mendes@email.com', 'Brasília, DF', 'Angular, TypeScript, Node.js, MongoDB, Azure', 3),
('8', 'Fernanda Lima', 'fernanda.lima@email.com', 'Recife, PE', 'Vue.js, JavaScript, PHP, Laravel, MySQL', 5),
('9', 'Bruno Santos', 'bruno.santos@email.com', 'Salvador, BA', 'C#, .NET Core, SQL Server, Azure, Docker', 8),
('10', 'Camila Alves', 'camila.alves@email.com', 'Curitiba, PR', 'React Native, Flutter, Firebase, Node.js, MongoDB', 2);

MERGE INTO vaga (id, titulo, localizacao, habilidades_requeridas_csv, anos_de_experiencia_minimos) VALUES
('1', 'Desenvolvedor Java Sr', 'São Paulo, SP', 'Java, Spring, MySQL, REST API', 3),
('2', 'Desenvolvedor Full Stack', 'Rio de Janeiro, RJ', 'JavaScript, React, Node.js, MongoDB', 2),
('3', 'Desenvolvedor Python Jr', 'Belo Horizonte, MG', 'Python, Django, PostgreSQL', 1),
('4', 'Arquiteto de Software', 'São Paulo, SP', 'Java, Spring Boot, Microservices, Docker, Kubernetes', 5),
('5', 'Desenvolvedor Mobile', 'Curitiba, PR', 'React Native, Flutter, Firebase, JavaScript, TypeScript', 3);
